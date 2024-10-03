package com.ravan.foodie.autoreserve.ui.component.selection

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.material.Icon
import androidx.compose.runtime.Composable
import androidx.compose.runtime.CompositionLocalProvider
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalLayoutDirection
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.LayoutDirection
import com.ravan.foodie.R
import com.ravan.foodie.autoreserve.ui.model.FoodPrioritySelectionStarsRowUIModel
import com.ravan.foodie.domain.ui.theme.RavanTheme

@Composable
fun FoodLikelySelectionStarsRow(
    data: FoodPrioritySelectionStarsRowUIModel,
    onSelectLikely: (Int) -> Unit,
    modifier: Modifier = Modifier
) {

    CompositionLocalProvider(LocalLayoutDirection provides LayoutDirection.Ltr) {
        Row(
            modifier = modifier
                .fillMaxWidth(),
            verticalAlignment = Alignment.CenterVertically,
            horizontalArrangement = Arrangement.SpaceEvenly,
        ) {
            (1..5).forEach { index ->
                Box(
                    modifier = Modifier
                        .clickable { onSelectLikely(index) }
                ) {
                    if (index <= data.selected) {
                        Icon(
                            painter = painterResource(id = R.drawable.ic_star_filled),
                            contentDescription = "rating_star",
                            tint = RavanTheme.colors.icon.onSecondary,
                        )
                    } else {
                        Icon(
                            painter = painterResource(id = R.drawable.ic_star),
                            contentDescription = "rating_star",
                            tint = RavanTheme.colors.icon.onSecondary,
                        )
                    }
                }
            }
        }
    }

}

@Preview
@Composable
private fun FoodPrioritySelectionStarsRowPreview() {
    RavanTheme {
        FoodLikelySelectionStarsRow(
            data = FoodPrioritySelectionStarsRowUIModel(
                selected = 3
            ),
            onSelectLikely = {}
        )
    }

}