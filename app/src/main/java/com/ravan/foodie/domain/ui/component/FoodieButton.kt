package com.ravan.foodie.domain.ui.component

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.aspectRatio
import androidx.compose.foundation.layout.offset
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.material3.Icon
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Shape
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.ravan.foodie.R
import com.ravan.foodie.domain.ui.model.FoodieButtonUIModel
import com.ravan.foodie.domain.ui.theme.RavanShapes
import com.ravan.foodie.domain.ui.theme.RavanTheme

@Composable
fun FoodieButton(
    data: FoodieButtonUIModel,
    onClick: () -> Unit,
    modifier: Modifier = Modifier,
    isEnable: Boolean = true,
    shape: Shape = RavanTheme.shapes.r16
) {

    when (data) {
        is FoodieButtonUIModel.General -> {
            Box(
                modifier = modifier
                    .clip(shape)
                    .background(
                        getBackgroundColor(isEnable)
                    )
                    .clickable {
                        if (isEnable) {
                            onClick()
                        }
                    }
                    .padding(horizontal = 16.dp, vertical = 2.dp),
                contentAlignment = Alignment.Center
            ) {
                Row(
                    modifier = Modifier.offset(y = 2.dp),
                ) {
                    data.iconRes?.let {
                        Icon(
                            painter = painterResource(id = it),
                            contentDescription = null,
                            tint = getTintColor(isEnable),
                            modifier = Modifier
                                .size(24.dp)
                                .aspectRatio(1f),
                        )
                    }
                    data.title?.let {
                        Text(
                            text = it,
                            style = RavanTheme.typography.body1,
                            color = getTintColor(isEnable),
                            modifier = Modifier
                                .padding(start = 8.dp)
                                .offset(y = (-7).dp)
                        )
                    }
                }
            }
        }

        is FoodieButtonUIModel.Icon -> {
            Box(
                modifier = modifier
                    .size(72.dp)
                    .clip(shape)
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
                    painter = painterResource(id = data.iconRes),
                    contentDescription = null,
                    tint = getTintColor(isEnable),
                    modifier = Modifier
                        .size(32.dp)
                        .aspectRatio(1f),
                )
            }
        }
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
private fun FoodieButtonPreview() {
    RavanTheme {
        Column {
            FoodieButton(
                data = FoodieButtonUIModel.Icon(R.drawable.ic_code),
                onClick = {},
            )

            FoodieButton(
                data = FoodieButtonUIModel.Icon(R.drawable.ic_code),
                isEnable = false,
                onClick = {},
            )

            FoodieButton(
                data = FoodieButtonUIModel.General(
                    iconRes = R.drawable.ic_code,
                    title = "asdfa"
                ),
                isEnable = false,
                onClick = {},
            )
        }

    }
}
