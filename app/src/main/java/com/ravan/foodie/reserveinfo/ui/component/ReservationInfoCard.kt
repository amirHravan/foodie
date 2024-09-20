package com.ravan.foodie.reserveinfo.ui.component

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.ravan.foodie.domain.ui.component.FoodieDivider
import com.ravan.foodie.domain.ui.theme.RavanTheme
import com.ravan.foodie.reserveinfo.ui.fixture.reserveInfoCardUIModelFixture
import com.ravan.foodie.reserveinfo.ui.model.ReservationInfoCardUIModel

@Composable
fun ReservationInfoCard(
    data: ReservationInfoCardUIModel,
    modifier: Modifier = Modifier
) {
    Column(
        modifier = modifier
            .background(RavanTheme.colors.background.secondary)
            .fillMaxWidth(),
        horizontalAlignment = Alignment.CenterHorizontally,
    ) {
        ReservationInfoCardHeader(data)
        data.mealRowUIModelList.forEach { mealRowUIModel ->
            FoodieDivider(
                color = RavanTheme.colors.border.onSecondary,
                thickness = 1,
            )
            MealRow(
                data = mealRowUIModel,
                modifier = Modifier.weight(1f)
            )
        }
    }

}

@Composable
private fun ReservationInfoCardHeader(data: ReservationInfoCardUIModel) {
    Row(
        modifier = Modifier
            .fillMaxWidth()
            .padding(horizontal = 16.dp, vertical = 8.dp),
        horizontalArrangement = Arrangement.SpaceBetween,
        verticalAlignment = Alignment.CenterVertically,
    ) {
        Text(
            text = data.farsiDayName,
            style = RavanTheme.typography.h4,
            modifier = Modifier
        )
        Text(
            text = data.farsiDate,
            style = RavanTheme.typography.h6,
            modifier = Modifier
        )
    }
}

@Preview
@Composable
private fun ReservationInfoCardPreview() {
    RavanTheme {
        ReservationInfoCard(
            data = reserveInfoCardUIModelFixture
        )
    }
}
