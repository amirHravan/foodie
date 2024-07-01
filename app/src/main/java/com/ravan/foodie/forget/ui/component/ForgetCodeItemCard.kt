package com.ravan.foodie.forget.ui.component

import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.ravan.foodie.domain.ui.theme.RavanTheme
import com.ravan.foodie.forget.ui.fixture.forgetCodeItemCardUIModel
import com.ravan.foodie.forget.ui.model.ForgetCodeItemUIModel

@Composable
fun ForgetCodeItemCard(
    data: ForgetCodeItemUIModel,
    onGetForgetCodeClick: (Int) -> Unit,
    modifier: Modifier = Modifier
) {
    Column(
        verticalArrangement = Arrangement.spacedBy(8.dp),
        horizontalAlignment = Alignment.CenterHorizontally,
        modifier = modifier
            .fillMaxWidth()
            .clip(RavanTheme.shapes.r12)
            .background(RavanTheme.colors.background.secondary)
            .padding(16.dp)

    ) {
        Text(
            text = data.mealType.getLocalName(),
            style = RavanTheme.typography.h5,
            color = RavanTheme.colors.text.onSecondary,
        )

        data.foodDetails.forEach {
            ForgetCodeFoodDetailRow(
                data = it,
                onGetForgetCodeClick = { onGetForgetCodeClick(it.reserveId) },
                modifier = Modifier
                    .fillMaxWidth()
                    .weight(1f)
                    .border(1.dp, RavanTheme.colors.border.onSecondary, RavanTheme.shapes.r8)
                    .padding(horizontal = 8.dp, vertical = 16.dp)
            )
        }

    }

}

@Preview
@Composable
private fun ForgetCodeItemCardPreview() {
    RavanTheme {
        ForgetCodeItemCard(
            data = forgetCodeItemCardUIModel,
            onGetForgetCodeClick = {}
        )
    }
}
