package com.ravan.foodie.home.ui.component

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.aspectRatio
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.material3.Icon
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.ravan.foodie.R
import com.ravan.foodie.domain.ui.theme.RavanTheme
import com.ravan.foodie.home.ui.model.HomeButtonUIModel

@Composable
fun HomeButton(
    data: HomeButtonUIModel,
    onClick: () -> Unit,
    modifier: Modifier = Modifier,
    isEnable: Boolean = true,
) {
    Box(
        modifier = modifier
            .size(72.dp)
            .clip(RavanTheme.shapes.r16)
            .background(
                getBackgroundColor(isEnable)
            )
            .clickable {
                if (isEnable) {
                    onClick()
                }
            }
            .padding(8.dp),
        contentAlignment = Alignment.Center
    ) {
        Icon(
            painter = painterResource(id = data.icon),
            contentDescription = null,
            tint = getTintColor(isEnable),
            modifier = Modifier
                .size(32.dp)
                .aspectRatio(1f),
        )
    }
}

@Composable
private fun getTintColor(isEnable: Boolean) =
    if (isEnable) RavanTheme.colors.icon.onTertiary else RavanTheme.colors.icon.onTertiary.copy(
        alpha = 0.4f
    )

@Composable
private fun getBackgroundColor(isEnable: Boolean) =
    if (isEnable) RavanTheme.colors.background.tertiary else RavanTheme.colors.background.tertiary.copy(
        alpha = 0.4f
    )

@Preview
@Composable
fun HomeButtonPreview() {
    RavanTheme {
        Column {
            HomeButton(
                data = HomeButtonUIModel(R.drawable.ic_code),
                onClick = {},
            )

            HomeButton(
                data = HomeButtonUIModel(R.drawable.ic_code),
                isEnable = false,
                onClick = {},
            )
        }

    }
}
