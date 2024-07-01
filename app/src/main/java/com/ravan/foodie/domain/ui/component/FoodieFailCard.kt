package com.ravan.foodie.domain.ui.component

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.size
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.ravan.foodie.R
import com.ravan.foodie.domain.ui.model.FoodieButtonUIModel
import com.ravan.foodie.domain.ui.model.FoodieFailCardUIModel
import com.ravan.foodie.domain.ui.theme.RavanTheme

@Composable
fun FoodieFailCard(
    data: FoodieFailCardUIModel,
    onReloadClick: () -> Unit,
    modifier: Modifier = Modifier,
) {
    Column(
        modifier = modifier,
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.Center,
    ) {
        Text(
            text = data.title,
            style = RavanTheme.typography.h6,
            color = RavanTheme.colors.text.onPrimary
        )
        Spacer(modifier = Modifier.size(32.dp))
        FoodieButton(
            data = FoodieButtonUIModel.General(
                title = stringResource(id = R.string.retry),
                iconRes = R.drawable.ic_refresh
            ),
            onClick = onReloadClick
        )
    }
}

@Preview
@Composable
private fun FoodieFailCardPreview() {
    RavanTheme {
        FoodieFailCard(
            data = FoodieFailCardUIModel(
                title = "زارت",
            ),
            onReloadClick = {}
        )
    }
}
