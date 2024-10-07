package com.ravan.foodie.autoreserve.ui.viewmodel

import android.net.Uri
import androidx.compose.runtime.mutableStateOf
import androidx.core.graphics.component1
import androidx.core.graphics.component2
import androidx.core.graphics.component3
import androidx.lifecycle.viewModelScope
import com.ravan.foodie.autoreserve.domain.model.AutoReserveDays
import com.ravan.foodie.autoreserve.domain.model.AutoReserveFoodPriority
import com.ravan.foodie.autoreserve.domain.model.AutoReserveRequestData
import com.ravan.foodie.autoreserve.domain.usecase.GetAllFoodsUseCase
import com.ravan.foodie.autoreserve.domain.usecase.GetAllSelectedDaysUseCase
import com.ravan.foodie.autoreserve.domain.usecase.UpdateAutoReserveDaysUseCase
import com.ravan.foodie.autoreserve.ui.model.AutoReserveScreenUIModel
import com.ravan.foodie.autoreserve.ui.model.ReserveResultInfoRowUIModel
import com.ravan.foodie.autoreserve.ui.model.toReserveResultInfoRowUIModel
import com.ravan.foodie.credit.domain.usecase.GetRedirectLoginAsTokenUseCase
import com.ravan.foodie.domain.model.LoadableData
import com.ravan.foodie.domain.model.NavigationEvent
import com.ravan.foodie.domain.ui.model.FoodieInformationBoxState
import com.ravan.foodie.domain.ui.model.FoodieInformationBoxUIModel
import com.ravan.foodie.domain.ui.viewmodel.FoodieViewModel
import com.ravan.foodie.domain.util.DaysOfWeek
import com.ravan.foodie.domain.util.getNextSaturday
import com.ravan.foodie.domain.util.getPreviousSaturday
import com.ravan.foodie.order.domain.model.MealType
import com.ravan.foodie.order.domain.model.ReservableFoodDetail
import com.ravan.foodie.order.domain.model.SelfDayReservableProgram
import com.ravan.foodie.order.domain.model.merge
import com.ravan.foodie.order.domain.usecase.GetAvailableSelfsUseCase
import com.ravan.foodie.order.domain.usecase.GetReservableProgramUseCase
import com.ravan.foodie.order.domain.usecase.ReserveFoodUseCase
import com.ravan.foodie.order.ui.model.SelectSelfRowUIModel
import com.ravan.foodie.order.ui.model.SelfDialogRowUIModel
import com.ravan.foodie.order.ui.model.SelfDialogUIModel
import com.ravan.foodie.order.ui.model.toSelfDialogUIModel
import kotlinx.collections.immutable.toImmutableList
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch
import java.util.Locale

class AutoReserveViewModel(
    private val getAvailableSelfsUseCase: GetAvailableSelfsUseCase,
    private val getRedirectLoginAsTokenUseCase: GetRedirectLoginAsTokenUseCase,
    private val getAllSelectedDaysUseCase: GetAllSelectedDaysUseCase,
    private val updateAutoReserveDaysUseCase: UpdateAutoReserveDaysUseCase,
    private val getReservableProgramUseCase: GetReservableProgramUseCase,
    private val getAllFoodsUseCase: GetAllFoodsUseCase,
    private val reserveFoodUseCase: ReserveFoodUseCase,

    ) : FoodieViewModel() {

    private val reserveLogs: MutableList<ReserveResultInfoRowUIModel> = mutableListOf()
    val reserveResponseLog =
        mutableStateOf<LoadableData<List<ReserveResultInfoRowUIModel>>>(LoadableData.NotLoaded)

    private var selectedDays: AutoReserveDays? = null
    val autoReserveScreenUIModel =
        mutableStateOf<LoadableData<AutoReserveScreenUIModel>>(LoadableData.NotLoaded)


    // selected self row
    private var selectedSelfId = -1
    private var selectedSelfName: String = ""
    private var selfDialogUIModel: SelfDialogUIModel? = null
    val selectSelfRowUIModel = mutableStateOf(SelectSelfRowUIModel())

    // information box
    val showMessage = mutableStateOf(false)
    val informationBoxUIModel = mutableStateOf<FoodieInformationBoxUIModel?>(null)

    // foods
    private var foodPriorityList: List<AutoReserveFoodPriority>? = null

    // navigation
    val navBack: NavigationEvent = NavigationEvent()
    val navReserve: NavigationEvent = NavigationEvent()

    fun onLaunch() {
        autoReserveScreenUIModel.value = LoadableData.Loading
        onSelectSelfClick()
        getReservePriorities()

    }

    fun onBackClick() {
        navBack.navigate()
    }

    private fun getReservePriorities() {
        CoroutineScope(Dispatchers.IO).launch {
            getAllFoodsUseCase().fold(
                onSuccess = {
                    foodPriorityList = it
                },
                onFailure = {}
            )
        }
    }

    private fun getReserveDays() {
        CoroutineScope(Dispatchers.IO).launch {
            getAllSelectedDaysUseCase().fold(
                onSuccess = {
                    selectedDays = it
                    autoReserveScreenUIModel.value = LoadableData.Loaded(
                        AutoReserveScreenUIModel(
                            selectedDaysList = it.days.toImmutableList()
                        )
                    )
                },
                onFailure = {
                    autoReserveScreenUIModel.value = LoadableData.Failed(
                        it.message ?: "در گرفتن روزهای انتخاب شده خطایی پیش آمده"
                    )
                }
            )
        }
    }

    fun onSelectSelfClick() {
        CoroutineScope(Dispatchers.IO).launch {
            getAvailableSelfsUseCase().fold(
                onSuccess = {
                    selfDialogUIModel = it.toSelfDialogUIModel()
                    selectSelfRowUIModel.value = SelectSelfRowUIModel(
                        selectedSelfName,
                        selfDialogUIModel = it.toSelfDialogUIModel()
                    )
                    getReserveDays()
                },
                onFailure = {
                    autoReserveScreenUIModel.value =
                        LoadableData.Failed("در گرفتن سلف‌های مجاز خطایی پیش آمده")
                    informationBoxUIModel.value = FoodieInformationBoxUIModel(
                        state = FoodieInformationBoxState.FAILED,
                        message = it.message ?: "در گرفتن سلف‌های مجاز خطایی پیش آمده"
                    )
                    showMessage()
                }
            )
        }
    }

    fun onSelfClick(
        data: SelfDialogRowUIModel
    ) {
        selectedSelfName = data.name

        selectSelfRowUIModel.value = SelectSelfRowUIModel(
            selectedSelfName = selectedSelfName,
            selfDialogUIModel = selfDialogUIModel
        )

        if (data.id != selectedSelfId) {
            selectedSelfId = data.id
        }
    }

    fun onReserveClick() {
        startAutomaticReserve()
    }

    fun onRefresh() {
        onLaunch()
    }

    fun onPrioritySelectionClick() {
        navReserve.navigate()
    }

    fun onIncreaseCreditClick(
        invokeIntent: (String) -> Unit,
    ) {
        viewModelScope.launch {
            getRedirectLoginAsTokenUseCase().fold(
                onSuccess = {
                    informationBoxUIModel.value = FoodieInformationBoxUIModel(
                        message = "در حال انتقال به صفحه افزایش اعتبار...",
                        state = FoodieInformationBoxState.SUCCESS
                    )

                    val url = Uri.Builder()
                        .scheme("https")  // Protocol (https, http, etc.)
                        .authority("setad.dining.sharif.edu")  // Base URL
                        .appendPath("j_security_check")  // Path segment (if any)
                        .appendQueryParameter("loginAsToken", it.loginAsToken)  // Add query params
                        .appendQueryParameter("redirect", "/nurture/user/credit/charge/view.rose")
                        .build()
                        .toString()

                    invokeIntent(url)
                },
                onFailure = {
                    informationBoxUIModel.value = FoodieInformationBoxUIModel(
                        message = it.message ?: "در افزایش اعتبار خطایی پیش آمده",
                        state = FoodieInformationBoxState.FAILED
                    )
                }
            )
            showMessage()
        }

    }

    fun onSelectDayClick(
        day: DaysOfWeek,
        isSelected: Boolean
    ) {
        val days = selectedDays?.days?.toMutableList() ?: mutableListOf()
        if (isSelected) {
            days.add(day)
        } else {
            days.remove(day)
        }

        selectedDays = AutoReserveDays(days)
        CoroutineScope(Dispatchers.IO).launch {
            updateAutoReserveDaysUseCase(selectedDays!!)
            getReserveDays()
        }

    }

    private fun startAutomaticReserve() {
        reserveResponseLog.value = LoadableData.Loading
        viewModelScope.launch {
            getReservableProgram { program ->
                viewModelScope.launch {
                    program.forEach { dayProgram ->
                        if (dayProgram.dayName.toLowerCase(Locale.ROOT) !in (selectedDays?.days?.map {
                                it.dayName.toLowerCase(
                                    Locale.ROOT
                                )
                            } ?: emptyList())) {
                            return@forEach
                        }

                        dayProgram.reserveInfoList.forEach { (mealType, reservableFoodDetails) ->
                            val reserveData = selectFoodBasedOnPriority(reservableFoodDetails)
                            reserveFood(
                                requestData = reserveData,
                                mealType = mealType,
                                dayName = dayProgram.farsiDayName
                            )
                        }
                        delay(500)
                    }
                }
            }

            reserveLogs.clear()
        }
    }

    private suspend fun reserveFood(
        requestData: AutoReserveRequestData,
        mealType: MealType,
        dayName: String,
    ) {
        reserveFoodUseCase(
            requestData.foodTypeId,
            requestData.mealTypeId,
            requestData.programId,
            selected = true
        ).let {
            reserveLogs.add(
                it.toReserveResultInfoRowUIModel(
                    mealType,
                    requestData.foodName,
                    dayName
                )
            )
        }
        reserveResponseLog.value =
            LoadableData.Loaded(reserveLogs.filter { it.foodName.isNotBlank() })

    }

    private fun selectFoodBasedOnPriority(reservableFoodDetails: List<ReservableFoodDetail>): AutoReserveRequestData {
        var (foodTypeId, mealTypeId, programId) = 0
        var foodName = ""
        var highestPriority = -1

        foodPriorityList?.let {
            reservableFoodDetails.forEach { foodDetail ->
                // TODO Fix IDs
                val priority = it.find { it.name == foodDetail.foodName }?.priority ?: 0
                if (priority > highestPriority) {
                    highestPriority = priority
                    foodTypeId = foodDetail.foodTypeId
                    mealTypeId = foodDetail.mealTypeId
                    programId = foodDetail.programId
                    foodName = foodDetail.foodName
                }
            }
        }
        return AutoReserveRequestData(
            foodTypeId = foodTypeId,
            mealTypeId = mealTypeId,
            programId = programId,
            foodName = foodName,
        )
    }

    private suspend fun getReservableProgram(
        onSuccess: (List<SelfDayReservableProgram>) -> Unit = {},
    ) {
        if (selectedSelfId == -1) {
            reserveResponseLog.value = LoadableData.Failed("سلفی انتخاب نشده است.")
            return
        }
        reserveResponseLog.value = LoadableData.Loading

        val previousProgram = getReservableProgramUseCase(
            selfId = selectedSelfId,
            weekStartDate = getPreviousSaturday()
        )
        val nextProgram = getReservableProgramUseCase(
            selfId = selectedSelfId,
            weekStartDate = getNextSaturday()
        )

        if (previousProgram.isFailure && nextProgram.isFailure) {
            informationBoxUIModel.value = FoodieInformationBoxUIModel(
                message = "هیچ برنامه غذایی‌ای برای این سلف تعریف نشده است.",
                state = FoodieInformationBoxState.FAILED
            )
            showMessage()
        } else {
            return onSuccess(
                previousProgram.getOrNull().merge(nextProgram.getOrNull()).selfWeekProgram
            )
        }
    }

    private fun showMessage() {
        showMessage.value = true
        viewModelScope.launch {
            delay(3000)
            showMessage.value = false
        }
    }
}