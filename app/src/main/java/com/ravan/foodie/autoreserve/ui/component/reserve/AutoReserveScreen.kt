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
import com.ravan.foodie.domain.ui.component.FoodieButton
import com.ravan.foodie.domain.ui.component.FoodieDivider
import com.ravan.foodie.domain.ui.component.FoodieTitleBar
import com.ravan.foodie.domain.ui.model.FoodieButtonUIModel
import com.ravan.foodie.domain.ui.model.FoodieTitleBarUIModel
import com.ravan.foodie.domain.ui.theme.RavanTheme
import com.ravan.foodie.domain.util.DaysOfWeek
import com.ravan.foodie.order.ui.component.SelectSelfRow
import com.ravan.foodie.order.ui.model.SelectSelfRowUIModel
import com.ravan.foodie.order.ui.model.SelfDialogRowUIModel

@Composable
fun AutoReserveScreen(
    data: AutoReserveScreenUIModel,
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
                    iconRes = null,
                    title = stringResource(id = R.string.priority_selection_screen_title)
                ), onClick = onPrioritySelectionClick
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
                DaysOfWeekSelectionColumn(onSelectDayClick)
            }

            item {
                AnimatedVisibility(visible = data.reserveResultInfoRowUIModelList != null) {
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
                        data.reserveResultInfoRowUIModelList?.forEach { reserveResultInfoRowUIModel ->
                            ReserveResultInfoRow(
                                data = reserveResultInfoRowUIModel,
                                modifier = Modifier
                                    .fillMaxWidth(),
                                color = RavanTheme.colors.text.onSecondary
                            )
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
                    iconRes = R.drawable.ic_smart_toy,
                    title = stringResource(id = R.string.auto_reserve_button_label)
                ), onClick = onAutoReserveClick,
                modifier = Modifier
                    .weight(1f)
                    .height(50.dp)
            )

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
                    .weight(1f)
                    .height(50.dp)
            )
        }
    }

}

@Composable
private fun DaysOfWeekSelectionColumn(onSelectDayClick: (DaysOfWeek, Boolean) -> Unit) {
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
                onSelectClick = { onSelectDayClick(DaysOfWeek.SATURDAY, true) }
            )
            Spacer(modifier = Modifier.size(12.dp))
            AutoReserveDaySelectionItem(
                data = AutoReserveDaySelectionItemUIModel(stringResource(id = R.string.persian_day_of_week_sunday)),
                onSelectClick = { onSelectDayClick(DaysOfWeek.SUNDAY, true) }
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
                onSelectClick = { onSelectDayClick(DaysOfWeek.MONDAY, true) }
            )
            Spacer(modifier = Modifier.size(12.dp))
            AutoReserveDaySelectionItem(
                data = AutoReserveDaySelectionItemUIModel(stringResource(id = R.string.persian_day_of_week_tuesday)),
                onSelectClick = { onSelectDayClick(DaysOfWeek.TUESDAY, true) }
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
                onSelectClick = { onSelectDayClick(DaysOfWeek.WEDNESDAY, true) }
            )
            Spacer(modifier = Modifier.size(12.dp))
            AutoReserveDaySelectionItem(
                data = AutoReserveDaySelectionItemUIModel(stringResource(id = R.string.persian_day_of_week_thursday)),
                onSelectClick = { onSelectDayClick(DaysOfWeek.THURSDAY, true) }
            )
        }
        Row(
            modifier = Modifier
                .fillMaxWidth(),
            verticalAlignment = Alignment.CenterVertically,
            horizontalArrangement = Arrangement.Center
        ) {

            AutoReserveDaySelectionItem(
                data = AutoReserveDaySelectionItemUIModel(stringResource(id = R.string.persian_day_of_week_friday)),
                onSelectClick = { onSelectDayClick(DaysOfWeek.FRIDAY, true) }
            )
        }
    }
}


@Preview
@Composable
private fun AutoReserveScreenPreview() {
    RavanTheme {
        AutoReserveScreen(
            data = AutoReserveScreenUIModel(
                reserveResultInfoRowUIModelList = listOf(
                    ReserveResultInfoRowUIModel(
                        foodName = "چلو جوجه کباب",
                        status = ReserveStatus.SUCCESS,
                        message = "غذاتون با موفقیت رزرو شد جناب / سرکار"
                    ),
                    ReserveResultInfoRowUIModel(
                        foodName = "چلو ماهی",
                        status = ReserveStatus.SUCCESS,
                        message = "غذاتون با موفقیت رزرو شد جناب / سرکار"
                    ),
                    ReserveResultInfoRowUIModel(
                        foodName = "چلو قیمه",
                        status = ReserveStatus.SUCCESS,
                        message = "غذاتون با موفقیت رزرو شد جناب / سرکار"
                    ),
                    ReserveResultInfoRowUIModel(
                        foodName = "چلو برگر",
                        status = ReserveStatus.SUCCESS,
                        message = "غذاتون با موفقیت رزرو شد جناب / سرکار"
                    ),
                    ReserveResultInfoRowUIModel(
                        foodName = "چلو کباب",
                        status = ReserveStatus.FAILURE,
                        message = "غذاتون با موفقیت رزرو شد جناب / سرکار"
                    ),
                    ReserveResultInfoRowUIModel(
                        foodName = "چلو قرمه سبزی",
                        status = ReserveStatus.FAILURE,
                        message = "غذاتون با موفقیت رزرو شد جناب / سرکار"
                    ),
                )
            ),
            selectSelfRowUIModel = SelectSelfRowUIModel(
                selfDialogUIModel = null,
                selectedSelfName = "خوان کرم پدر"
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