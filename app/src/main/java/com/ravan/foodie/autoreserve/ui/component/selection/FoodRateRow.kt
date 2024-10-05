package com.ravan.foodie.autoreserve.ui.component.selection

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.mutableIntStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.ravan.foodie.autoreserve.ui.model.FoodPrioritySelectionStarsRowUIModel
import com.ravan.foodie.autoreserve.ui.model.FoodPriorityUIModel
import com.ravan.foodie.domain.ui.theme.RavanTheme

@Composable
fun FoodRateRow(
    data: FoodPriorityUIModel,
    onChangePriority: (Int) -> Unit,
    modifier: Modifier = Modifier
) {

    Column(
        modifier = modifier
            .fillMaxWidth()
            .clip(RavanTheme.shapes.r8)
            .background(RavanTheme.colors.background.secondary)
            .padding(8.dp),
        verticalArrangement = Arrangement.spacedBy(8.dp),
        horizontalAlignment = Alignment.CenterHorizontally,
    ) {
        Text(
            text = data.name,
            style = RavanTheme.typography.h6,
            modifier = Modifier.padding(8.dp),
            color = RavanTheme.colors.text.onSecondary
        )
        FoodLikelySelectionStarsRow(
            data = FoodPrioritySelectionStarsRowUIModel(selected = data.priority),
            onSelectLikely = {
                if (it != data.priority) {
                    onChangePriority(it)
                }
            }
        )
    }
}

@Preview
@Composable
private fun FoodRateRowPreview() {
    RavanTheme {
        FoodRateRow(
            data = FoodPriorityUIModel(
                name = "خورشت قورمه سبزی",
                priority = 3,
                id = 0,
            ),
            onChangePriority = { _-> }
        )
    }

}