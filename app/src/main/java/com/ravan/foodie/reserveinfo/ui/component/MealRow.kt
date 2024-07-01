package com.ravan.foodie.reserveinfo.ui.component

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.ravan.foodie.R
import com.ravan.foodie.domain.ui.component.FoodieTextIconRow
import com.ravan.foodie.domain.ui.model.FoodieTextIconRowUIModel
import com.ravan.foodie.domain.ui.theme.RavanTheme
import com.ravan.foodie.reserveinfo.ui.fixture.reserveInfoMealRowUIModelFixture1
import com.ravan.foodie.reserveinfo.ui.model.MealRowUIModel

@Composable
fun MealRow(
    data: MealRowUIModel,
    modifier: Modifier = Modifier,
) {
    Column(
        modifier = modifier
            .fillMaxWidth()
            .padding(horizontal = 16.dp),
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.Center
    ) {
        Text(
            text = data.foodName,
            color = RavanTheme.colors.text.onSecondary,
            style = RavanTheme.typography.h6,
            modifier = Modifier
                .padding(vertical = 8.dp)
                .padding(start = 4.dp)
        )
        FoodieTextIconRow(
            data = FoodieTextIconRowUIModel(
                iconRes = R.drawable.ic_clock,
                text = data.mealName,
            ),
            color = RavanTheme.colors.text.onSecondary,
            textStyle = RavanTheme.typography.body2,
        )
        FoodieTextIconRow(
            data = FoodieTextIconRowUIModel(
                iconRes = R.drawable.ic_location,
                text = data.selfName,
            ),
            color = RavanTheme.colors.text.onSecondary,
            textStyle = RavanTheme.typography.body2,
        )
    }


}

@Preview
@Composable
fun MealRowPreview() {
    RavanTheme {
        MealRow(
            data = reserveInfoMealRowUIModelFixture1,
        )
    }
}
