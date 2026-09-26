package com.ravan.foodie.order.ui.viewmodel

import com.ravan.foodie.account.domain.model.Account
import com.ravan.foodie.account.domain.repository.AccountStore
import com.ravan.foodie.account.domain.repository.ReserveInFlightTracker
import com.ravan.foodie.credit.domain.model.RedirectLoginAsToken
import com.ravan.foodie.credit.domain.repository.CreditRepository
import com.ravan.foodie.credit.domain.usecase.GetRedirectLoginAsTokenUseCase
import com.ravan.foodie.domain.model.LoadableData
import com.ravan.foodie.domain.ui.model.FoodieInformationBoxState
import com.ravan.foodie.order.api.dto.reserve.ReserveProgramDto
import com.ravan.foodie.order.api.dto.reserve.SelfWeekProgramDto
import com.ravan.foodie.order.api.dto.reserve.UserWeekReserveDto
import com.ravan.foodie.order.api.dto.self.SelfDto
import com.ravan.foodie.order.domain.model.ReserveRequestBodyData
import com.ravan.foodie.order.domain.repository.OrderFoodRepository
import com.ravan.foodie.order.domain.usecase.GetAvailableSelfsUseCase
import com.ravan.foodie.order.domain.usecase.GetReservableProgramUseCase
import com.ravan.foodie.order.domain.usecase.ReserveFoodUseCase
import com.ravan.foodie.order.ui.model.OrderFoodDetailUIModel
import com.ravan.foodie.order.ui.model.OrderFoodKey
import com.ravan.foodie.order.ui.model.OrderScreenUIModel
import com.ravan.foodie.order.ui.model.SelfDialogRowUIModel
import com.ravan.foodie.order.ui.model.key
import com.ravan.foodie.testing.FakeKeyValueStore
import kotlinx.coroutines.CancellationException
import kotlinx.coroutines.CompletableDeferred
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.test.StandardTestDispatcher
import kotlinx.coroutines.test.TestScope
import kotlinx.coroutines.test.advanceUntilIdle
import kotlinx.coroutines.test.resetMain
import kotlinx.coroutines.test.runCurrent
import kotlinx.coroutines.test.runTest
import kotlinx.coroutines.test.setMain
import kotlinx.serialization.json.Json
import org.junit.After
import org.junit.Assert.assertEquals
import org.junit.Assert.assertFalse
import org.junit.Assert.assertTrue
import org.junit.Before
import org.junit.Test

@OptIn(ExperimentalCoroutinesApi::class)
class OrderScreenViewModelTest {

    private val dispatcher = StandardTestDispatcher()
    private val repository = FakeOrderFoodRepository()
    private val accountStore = AccountStore(FakeKeyValueStore(), Json)
    private lateinit var viewModel: OrderScreenViewModel

    @Before
    fun setUp() {
        Dispatchers.setMain(dispatcher)
        viewModel = OrderScreenViewModel(
            getReservableProgramUseCase = GetReservableProgramUseCase(repository),
            getAvailableSelfsUseCase = GetAvailableSelfsUseCase(repository),
            reserveFoodUseCase = ReserveFoodUseCase(repository, ReserveInFlightTracker()),
            getRedirectLoginAsTokenUseCase = GetRedirectLoginAsTokenUseCase(FakeCreditRepository()),
            accountStore = accountStore,
        )
        accountStore.upsert(Account(username = "me", password = "pw", displayName = "Me"))
        accountStore.setActive("me")
    }

    @After
    fun tearDown() {
        Dispatchers.resetMain()
    }

    private fun TestScope.loadSelf() {
        viewModel.onSelfClick(SelfDialogRowUIModel(name = "self", id = SELF_ID))
        advanceUntilIdle()
    }

    private fun loadedModel(): OrderScreenUIModel =
        (viewModel.orderScreenUIModel.value as LoadableData.Loaded).data

    private fun food(key: OrderFoodKey): OrderFoodDetailUIModel =
        loadedModel().orderCardUIModelList
            .flatMap { it.reserveInfoList.values.flatten() }
            .single { it.key == key }

    @Test
    fun `food is pending while its request is in flight`() = runTest(dispatcher) {
        loadSelf()
        val gate = CompletableDeferred<Result<String>>()
        repository.onReserve = { gate.await() }

        viewModel.onOrderFoodClick(food(KABAB))
        runCurrent()

        assertEquals(setOf(KABAB), viewModel.pendingFoods.value)

        gate.complete(Result.success("ok"))
        advanceUntilIdle()

        assertTrue(viewModel.pendingFoods.value.isEmpty())
    }

    @Test
    fun `success flips the food before the program is fetched again`() = runTest(dispatcher) {
        loadSelf()
        val programGate = CompletableDeferred<Unit>()
        repository.onProgram = { programGate.await(); programWith() }

        viewModel.onOrderFoodClick(food(KABAB))
        runCurrent()

        assertTrue(food(KABAB).isSelected)
        assertTrue(viewModel.pendingFoods.value.isEmpty())
        assertEquals(FoodieInformationBoxState.SUCCESS, viewModel.informationBoxUIModel.value?.state)
        programGate.complete(Unit)
    }

    @Test
    fun `background refresh replaces the optimistic state with server data`() =
        runTest(dispatcher) {
            loadSelf()
            // Server says the other food ended up reserved (e.g. it swapped the meal).
            repository.onProgram = { programWith(GHEYME_FOOD_ID) }

            viewModel.onOrderFoodClick(food(KABAB))
            advanceUntilIdle()

            assertFalse(food(KABAB).isSelected)
            assertTrue(food(GHEYME).isSelected)
        }

    @Test
    fun `failure keeps the food unchanged and shows the error`() = runTest(dispatcher) {
        loadSelf()
        repository.onReserve = { Result.failure(Throwable("اعتبار کافی نیست")) }

        viewModel.onOrderFoodClick(food(KABAB))
        advanceUntilIdle()

        assertFalse(food(KABAB).isSelected)
        assertTrue(viewModel.pendingFoods.value.isEmpty())
        assertEquals("اعتبار کافی نیست", viewModel.informationBoxUIModel.value?.message)
        assertEquals(FoodieInformationBoxState.FAILED, viewModel.informationBoxUIModel.value?.state)
    }

    @Test
    fun `tapping a pending food again does not send a second request`() = runTest(dispatcher) {
        loadSelf()
        val gate = CompletableDeferred<Result<String>>()
        repository.onReserve = { gate.await() }

        viewModel.onOrderFoodClick(food(KABAB))
        runCurrent()
        viewModel.onOrderFoodClick(food(KABAB))
        runCurrent()
        gate.complete(Result.success("ok"))
        advanceUntilIdle()

        assertEquals(1, repository.reserveCalls.size)
    }

    @Test
    fun `different foods can be reserved at the same time`() = runTest(dispatcher) {
        loadSelf()
        val gate = CompletableDeferred<Result<String>>()
        repository.onReserve = { gate.await() }

        viewModel.onOrderFoodClick(food(KABAB))
        viewModel.onOrderFoodClick(food(DINNER))
        runCurrent()

        assertEquals(setOf(KABAB, DINNER), viewModel.pendingFoods.value)
        gate.complete(Result.success("ok"))
        advanceUntilIdle()
        assertEquals(2, repository.reserveCalls.size)
    }

    @Test
    fun `an older refresh never overwrites a newer one`() = runTest(dispatcher) {
        loadSelf()
        val slowRefresh = CompletableDeferred<Unit>()
        val newRefresh = CompletableDeferred<Unit>()
        var programCalls = 0
        repository.onProgram = {
            programCalls++
            if (programCalls == 1) {
                // A response that arrives even though its refresh was cancelled, like the real
                // repository, which catches every exception.
                try {
                    slowRefresh.await()
                } catch (_: CancellationException) {
                }
                programWith()
            } else {
                newRefresh.await()
                programWith(KABAB_FOOD_ID, DINNER_FOOD_ID)
            }
        }

        viewModel.onOrderFoodClick(food(KABAB))
        advancePast(REFRESH_DEBOUNCE_MS)
        // Second success restarts the refresh while the first one is still waiting.
        viewModel.onOrderFoodClick(food(DINNER))
        advanceUntilIdle()

        // The stale response (nothing reserved) must not have replaced the optimistic state.
        assertTrue(food(KABAB).isSelected)
        assertTrue(food(DINNER).isSelected)

        newRefresh.complete(Unit)
        advanceUntilIdle()
        assertTrue(food(KABAB).isSelected)
        assertTrue(food(DINNER).isSelected)
        assertEquals(2, programCalls)
    }

    @Test
    fun `messages name the account once there is more than one`() = runTest(dispatcher) {
        loadSelf()

        viewModel.onOrderFoodClick(food(KABAB))
        advanceUntilIdle()
        assertEquals("ok", viewModel.informationBoxUIModel.value?.message)

        accountStore.upsert(Account(username = "ali", password = "pw", displayName = "Ali"))
        viewModel.onOrderFoodClick(food(DINNER))
        advanceUntilIdle()
        assertEquals("برای Me: ok", viewModel.informationBoxUIModel.value?.message)
    }

    @Test
    fun `a failed visible load shows the retry state instead of loading forever`() =
        runTest(dispatcher) {
            repository.onProgram = { Result.failure(Throwable("خطا")) }

            loadSelf()

            val state = viewModel.orderScreenUIModel.value
            assertTrue(state is LoadableData.Failed)
            assertEquals("خطا", (state as LoadableData.Failed).message)
        }

    private fun TestScope.advancePast(ms: Long) {
        testScheduler.advanceTimeBy(ms + 1)
        runCurrent()
    }

    private class FakeOrderFoodRepository : OrderFoodRepository {
        val reserveCalls = mutableListOf<Pair<ReserveRequestBodyData, Int>>()
        var onReserve: suspend () -> Result<String> = { Result.success("ok") }
        var onProgram: suspend () -> Result<ReserveProgramDto> = { programWith() }
        private var programRequests = 0

        override suspend fun reserveFood(
            reserveRequestBodyData: ReserveRequestBodyData,
            programId: Int,
        ): Result<String> {
            reserveCalls += reserveRequestBodyData to programId
            return onReserve()
        }

        /**
         * The view model asks for the previous and then the next week. The previous week always
         * fails here so each load yields exactly one day, answered by [onProgram].
         */
        override suspend fun getReserveProgram(
            selfId: Int,
            weekStartDate: String,
        ): Result<ReserveProgramDto> {
            programRequests++
            return if (programRequests % 2 == 1) {
                Result.failure(Throwable("no program"))
            } else {
                onProgram()
            }
        }

        override suspend fun getAvailableSelfs(): Result<List<SelfDto>> =
            Result.success(listOf(SelfDto(id = SELF_ID, name = "self")))
    }

    private class FakeCreditRepository : CreditRepository {
        override suspend fun getLoginAsToken(): Result<RedirectLoginAsToken> =
            Result.success(RedirectLoginAsToken(loginAsToken = "token"))
    }

    private companion object {
        const val SELF_ID = 7
        const val DATE = "2026-09-27"
        const val REFRESH_DEBOUNCE_MS = 300L

        const val KABAB_FOOD_ID = 100
        const val GHEYME_FOOD_ID = 101
        const val DINNER_FOOD_ID = 102

        val KABAB = OrderFoodKey(programId = 10, mealTypeId = 1, foodTypeId = 1)
        val GHEYME = OrderFoodKey(programId = 10, mealTypeId = 1, foodTypeId = 2)
        val DINNER = OrderFoodKey(programId = 11, mealTypeId = 5, foodTypeId = 1)

        fun dto(key: OrderFoodKey, foodId: Int) = SelfWeekProgramDto(
            cancelRuleViolated = false,
            date = DATE,
            dayTranslated = "Sunday",
            foodId = foodId,
            foodName = "food $foodId",
            foodTypeId = key.foodTypeId,
            mealTypeId = key.mealTypeId,
            price = 1000,
            programId = key.programId,
            reserveRuleViolated = false,
            selfId = SELF_ID,
            daysDifferenceWithToday = 1,
        )

        /** One day with lunch (kabab, gheyme) and dinner; [reserved] are the reserved foodIds. */
        fun programWith(vararg reserved: Int): Result<ReserveProgramDto> = Result.success(
            ReserveProgramDto(
                selfWeekProgramDtoList = listOf(
                    listOf(
                        dto(KABAB, KABAB_FOOD_ID),
                        dto(GHEYME, GHEYME_FOOD_ID),
                        dto(DINNER, DINNER_FOOD_ID),
                    )
                ),
                userId = 1,
                userWeekReserveDtos = reserved.map {
                    UserWeekReserveDto(foodId = it, programDate = DATE, selfId = SELF_ID)
                },
            )
        )
    }
}
