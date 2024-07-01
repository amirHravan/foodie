package com.ravan.foodie.order.ui.component

import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.ravan.foodie.domain.ui.theme.RavanTheme

@Composable
fun SelectSelfRow(
    name: String,
    onClick: () -> Unit,
    modifier: Modifier = Modifier
) {
    Box(
        modifier = modifier
    ) {
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .clip(RavanTheme.shapes.r8)
                .clickable { onClick() }
                .border(2.dp, RavanTheme.colors.border.onPrimary, RavanTheme.shapes.r8)
                .padding(8.dp),
            horizontalArrangement = Arrangement.Center
        ) {
            Text(
                text = name,
                color = RavanTheme.colors.text.onPrimary,
                style = RavanTheme.typography.h6,
            )
        }
    }
}

@Preview
@Composable
private fun SelectSelfRowPreview() {
    RavanTheme {
        SelectSelfRow(
            name = "مرکزی",
            onClick = {},
        )
    }
}
