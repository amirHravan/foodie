package com.ravan.foodie.autoreserve.ui.component.selection

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.ravan.foodie.R
import com.ravan.foodie.autoreserve.ui.model.FoodPriorityScreenUIModel
import com.ravan.foodie.autoreserve.ui.model.FoodPriorityUIModel
import com.ravan.foodie.domain.ui.component.FoodieTitleBar
import com.ravan.foodie.domain.ui.model.FoodieTitleBarUIModel
import com.ravan.foodie.domain.ui.theme.RavanTheme

@Composable
fun FoodPriorityScreen(
    data: FoodPriorityScreenUIModel,
    onPriorityChange: (FoodPriorityUIModel, Int) -> Unit,
    onBackClick: () -> Unit,
) {

    Column(
        modifier = Modifier
            .fillMaxSize()
            .background(RavanTheme.colors.background.primary)
    ) {
        FoodieTitleBar(
            data = FoodieTitleBarUIModel(
                title = stringResource(id = R.string.priority_selection_screen_title)
            ),
            onBackClick = onBackClick
        )
        LazyColumn(
            modifier = Modifier
                .background(RavanTheme.colors.background.primary)
                .padding(16.dp),
            verticalArrangement = Arrangement.spacedBy(8.dp),
        ) {
            items(count = data.foodPriorityUIModelList.count(), key = { it }) { index ->
                FoodRateRow(
                    data = data.foodPriorityUIModelList[index],
                    onChangePriority = onPriorityChange
                )
            }

        }
    }
}

@Preview
@Composable
private fun FoodPriorityScreenPreview() {
    val data = FoodPriorityScreenUIModel(
        foodPriorityUIModelList = listOf(
            FoodPriorityUIModel(
                name = "قورمه سبزی",
                priority = 3,
                id = 0,
            ),
            FoodPriorityUIModel(
                name = "پیتزا",
                priority = 4,
                id = 0,
            ),
            FoodPriorityUIModel(
                name = "کباب کوبیده",
                priority = 2,
                id = 0,
            ),
            FoodPriorityUIModel(
                name = "جوجه کباب",
                priority = 5,
                id = 0,
            ),
            FoodPriorityUIModel(
                name = "شیرینی قایقی",
                priority = 5,
                id = 0,
            )
        )
    )
    RavanTheme {
        FoodPriorityScreen(
            data = data,
            onPriorityChange = { _, _ -> },
            onBackClick = {}
        )
    }

}
