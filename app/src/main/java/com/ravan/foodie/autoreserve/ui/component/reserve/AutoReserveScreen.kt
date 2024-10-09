package com.ravan.foodie.autoreserve.ui.component.reserve

import android.content.Intent
import android.net.Uri
import androidx.compose.animation.AnimatedVisibility
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.ravan.foodie.R
import com.ravan.foodie.autoreserve.ui.model.AutoReserveDaySelectionItemUIModel
import com.ravan.foodie.autoreserve.ui.model.AutoReserveScreenUIModel
import com.ravan.foodie.autoreserve.ui.model.ReserveResultInfoRowUIModel
import com.ravan.foodie.autoreserve.ui.model.ReserveStatus
import com.ravan.foodie.domain.model.LoadableData
import com.ravan.foodie.domain.ui.component.FoodieButton
import com.ravan.foodie.domain.ui.component.FoodieDivider
import com.ravan.foodie.domain.ui.component.FoodieProgressIndicator
import com.ravan.foodie.domain.ui.component.FoodieTitleBar
import com.ravan.foodie.domain.ui.model.FoodieButtonUIModel
import com.ravan.foodie.domain.ui.model.FoodieTitleBarUIModel
import com.ravan.foodie.domain.ui.theme.RavanTheme
import com.ravan.foodie.domain.util.DaysOfWeek
import com.ravan.foodie.order.domain.model.MealType
import com.ravan.foodie.order.ui.component.SelectSelfRow
import com.ravan.foodie.order.ui.model.SelectSelfRowUIModel
import com.ravan.foodie.order.ui.model.SelfDialogRowUIModel
import kotlinx.collections.immutable.toImmutableList

@Composable
fun AutoReserveScreen(
    data: AutoReserveScreenUIModel,
    reserveResultInfoRowUIModelList: LoadableData<List<ReserveResultInfoRowUIModel>>,
    selectSelfRowUIModel: SelectSelfRowUIModel,
    onExpandSelfDialogClick: () -> Unit,
    onSelectSelfClick: (SelfDialogRowUIModel) -> Unit,
    onPrioritySelectionClick: () -> Unit,
    onSelectDayClick: (DaysOfWeek, Boolean) -> Unit,
    onAutoReserveClick: () -> Unit,
    onIncreaseCreditClick: ((String) -> Unit) -> Unit,
    onBackClick: () -> Unit,
) {
    val context = LocalContext.current

    Column(
        modifier = Modifier
            .fillMaxSize()
            .background(RavanTheme.colors.background.primary)
    ) {
        FoodieTitleBar(
            data = FoodieTitleBarUIModel(
                title = stringResource(id = R.string.auto_reserve_screen_title)
            ),
            onBackClick = onBackClick
        ) {
            FoodieButton(
                data = FoodieButtonUIModel.General(
                    iconRes = R.drawable.ic_payment,
                    title = stringResource(id = R.string.increase_credit_button_label)
                ), onClick = {
                    onIncreaseCreditClick { url ->
                        val intent = Intent(Intent.ACTION_VIEW, Uri.parse(url))
                        context.startActivity(intent)
                    }
                },
                modifier = Modifier
            )
        }
        LazyColumn(
            modifier = Modifier
                .weight(1f)
        ) {
            item {
                SelectSelfRow(
                    selectSelfRowUIModel = selectSelfRowUIModel,
                    onExpandClick = onExpandSelfDialogClick,
                    onSelectSelfClick = onSelectSelfClick,
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(vertical = 16.dp, horizontal = 8.dp),
                )
            }

            item {
                DaysOfWeekSelectionColumn(
                    selectedDays = data.selectedDaysList,
                    onSelectDayClick = onSelectDayClick,
                )
            }

            item {
                AnimatedVisibility(visible = reserveResultInfoRowUIModelList !is LoadableData.NotLoaded) {
                    Column(
                        modifier = Modifier
                            .fillMaxWidth()
                            .padding(vertical = 16.dp, horizontal = 8.dp)
                            .clip(RavanTheme.shapes.r16)
                            .background(RavanTheme.colors.background.secondary),
                        horizontalAlignment = Alignment.CenterHorizontally,
                        verticalArrangement = Arrangement.Center
                    ) {
                        Text(
                            text = stringResource(id = R.string.reserve_result_info_title),
                            color = RavanTheme.colors.text.onSecondary,
                            style = RavanTheme.typography.h6,
                            modifier = Modifier.padding(12.dp)
                        )
                        FoodieDivider(
                            color = RavanTheme.colors.border.onSecondary,
                            thickness = 1
                        )
                        when (reserveResultInfoRowUIModelList) {
                            is LoadableData.NotLoaded -> Unit
                            is LoadableData.Loading -> {
                                FoodieProgressIndicator(
                                    modifier = Modifier
                                        .fillMaxWidth()
                                        .padding(16.dp),
                                    color = RavanTheme.colors.text.onSecondary,
                                )
                            }

                            is LoadableData.Loaded -> {
                                if (reserveResultInfoRowUIModelList.data.isEmpty()) {
                                    FoodieProgressIndicator(
                                        modifier = Modifier
                                            .fillMaxWidth()
                                            .padding(16.dp),
                                        color = RavanTheme.colors.text.onSecondary,
                                    )
                                } else {
                                    reserveResultInfoRowUIModelList.data.forEach { reserveResultInfoRowUIModel ->
                                        ReserveResultInfoRow(
                                            data = reserveResultInfoRowUIModel,
                                            modifier = Modifier
                                                .fillMaxWidth(),
                                            color = RavanTheme.colors.text.onSecondary
                                        )
                                    }
                                }

                            }

                            is LoadableData.Failed -> {
                                Text(
                                    text = reserveResultInfoRowUIModelList.message,
                                    color = RavanTheme.colors.text.onSecondary,
                                    style = RavanTheme.typography.body1,
                                    modifier = Modifier.padding(12.dp)
                                )
                            }
                        }
                    }

                }

            }
        }
        FoodieDivider()
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .padding(horizontal = 16.dp, vertical = 16.dp),
            verticalAlignment = Alignment.CenterVertically,
            horizontalArrangement = Arrangement.spacedBy(8.dp)
        ) {
            FoodieButton(
                data = FoodieButtonUIModel.General(
                    iconRes = R.drawable.ic_robot,
                    title = stringResource(id = R.string.auto_reserve_button_label)
                ), onClick = onAutoReserveClick,
                modifier = Modifier
                    .weight(1f)
                    .height(50.dp)
            )
            FoodieButton(
                data = FoodieButtonUIModel.General(
                    iconRes = R.drawable.ic_star,
                    title = stringResource(id = R.string.priority_selection_screen_title)
                ), onClick = onPrioritySelectionClick,
                modifier = Modifier
                    .weight(1f)
                    .height(50.dp)
            )

        }
    }

}

@Composable
private fun DaysOfWeekSelectionColumn(
    selectedDays: List<DaysOfWeek>,
    onSelectDayClick: (DaysOfWeek, Boolean) -> Unit
) {
    Column(
        modifier = Modifier
            .fillMaxWidth()
            .padding(8.dp),
        verticalArrangement = Arrangement.spacedBy(12.dp),
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        Row(
            modifier = Modifier
                .fillMaxWidth(),
            verticalAlignment = Alignment.CenterVertically,
            horizontalArrangement = Arrangement.Center
        ) {
            AutoReserveDaySelectionItem(
                data = AutoReserveDaySelectionItemUIModel(stringResource(id = R.string.persian_day_of_week_saturday)),
                onSelectClick = { onSelectDayClick(DaysOfWeek.SATURDAY, it) },
                isSelected = selectedDays.contains(DaysOfWeek.SATURDAY)
            )
            Spacer(modifier = Modifier.size(12.dp))
            AutoReserveDaySelectionItem(
                data = AutoReserveDaySelectionItemUIModel(stringResource(id = R.string.persian_day_of_week_sunday)),
                onSelectClick = { onSelectDayClick(DaysOfWeek.SUNDAY, it) },
                isSelected = selectedDays.contains(DaysOfWeek.SUNDAY)
            )

        }
        Row(
            modifier = Modifier
                .fillMaxWidth(),
            verticalAlignment = Alignment.CenterVertically,
            horizontalArrangement = Arrangement.Center
        ) {
            AutoReserveDaySelectionItem(
                data = AutoReserveDaySelectionItemUIModel(stringResource(id = R.string.persian_day_of_week_monday)),
                onSelectClick = { onSelectDayClick(DaysOfWeek.MONDAY, it) },
                isSelected = selectedDays.contains(DaysOfWeek.MONDAY)
            )
            Spacer(modifier = Modifier.size(12.dp))
            AutoReserveDaySelectionItem(
                data = AutoReserveDaySelectionItemUIModel(stringResource(id = R.string.persian_day_of_week_tuesday)),
                onSelectClick = { onSelectDayClick(DaysOfWeek.TUESDAY, it) },
                isSelected = selectedDays.contains(DaysOfWeek.TUESDAY)
            )
        }
        Row(
            modifier = Modifier
                .fillMaxWidth(),
            verticalAlignment = Alignment.CenterVertically,
            horizontalArrangement = Arrangement.Center
        ) {
            AutoReserveDaySelectionItem(
                data = AutoReserveDaySelectionItemUIModel(stringResource(id = R.string.persian_day_of_week_wednesday)),
                onSelectClick = { onSelectDayClick(DaysOfWeek.WEDNESDAY, it) },
                isSelected = selectedDays.contains(DaysOfWeek.WEDNESDAY),
            )
            Spacer(modifier = Modifier.size(12.dp))
            AutoReserveDaySelectionItem(
                data = AutoReserveDaySelectionItemUIModel(stringResource(id = R.string.persian_day_of_week_thursday)),
                onSelectClick = { onSelectDayClick(DaysOfWeek.THURSDAY, it) },
                isSelected = selectedDays.contains(DaysOfWeek.THURSDAY)
            )
        }
    }
}


@Preview
@Composable
private fun AutoReserveScreenPreview() {
    RavanTheme {
        AutoReserveScreen(
            data = AutoReserveScreenUIModel(),
            selectSelfRowUIModel = SelectSelfRowUIModel(
                selfDialogUIModel = null,
                selectedSelfName = "خوان کرم پدر"
            ),
            reserveResultInfoRowUIModelList = LoadableData.Loaded(
                listOf(
                    ReserveResultInfoRowUIModel(
                        foodName = "چلو جوجه کباب",
                        status = ReserveStatus.SUCCESS,
                        message = "غذاتون با موفقیت رزرو شد جناب / سرکار",
                        mealType = MealType.DINNER,
                        dayName = "شنبه",
                        choices = listOf(
                            "kooft"
                        ).toImmutableList()
                    ),
                    ReserveResultInfoRowUIModel(
                        foodName = "چلو ماهی",
                        status = ReserveStatus.SUCCESS,
                        message = "غذاتون با موفقیت رزرو شد جناب / سرکار",
                        mealType = MealType.DINNER,
                        dayName = "شنبه",
                        choices = listOf(
                            "kooft"
                        ).toImmutableList()
                    ),
                    ReserveResultInfoRowUIModel(
                        foodName = "چلو قیمه",
                        status = ReserveStatus.SUCCESS,
                        message = "غذاتون با موفقیت رزرو شد جناب / سرکار",
                        mealType = MealType.DINNER,
                        dayName = "شنبه",
                        choices = listOf(
                            "kooft"
                        ).toImmutableList()
                    ),
                    ReserveResultInfoRowUIModel(
                        foodName = "چلو برگر",
                        status = ReserveStatus.SUCCESS,
                        message = "غذاتون با موفقیت رزرو شد جناب / سرکار",
                        mealType = MealType.DINNER,
                        dayName = "شنبه",
                        choices = listOf(
                            "kooft"
                        ).toImmutableList()
                    ),
                    ReserveResultInfoRowUIModel(
                        foodName = "چلو کباب",
                        status = ReserveStatus.FAILURE,
                        message = "غذاتون با موفقیت رزرو شد جناب / سرکار",
                        mealType = MealType.DINNER,
                        dayName = "شنبه",
                        choices = listOf(
                            "kooft"
                        ).toImmutableList()
                    ),
                    ReserveResultInfoRowUIModel(
                        foodName = "چلو قرمه سبزی",
                        status = ReserveStatus.FAILURE,
                        message = "غذاتون با موفقیت رزرو شد جناب / سرکار",
                        mealType = MealType.DINNER,
                        dayName = "شنبه",
                        choices = listOf(
                            "kooft"
                        ).toImmutableList()
                    ),
                )
            ),
            onPrioritySelectionClick = {},
            onBackClick = {},
            onSelectSelfClick = {},
            onExpandSelfDialogClick = {},
            onSelectDayClick = { _, _ -> },
            onAutoReserveClick = {},
            onIncreaseCreditClick = {},
        )
    }
}