package com.ravan.foodie.domain.ui.component

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.aspectRatio
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
import com.ravan.foodie.domain.ui.theme.RavanTheme

enum class FoodieButtonState {
    Enabled,
    Disabled,
    Loading,
}

@Composable
fun FoodieButton(
    data: FoodieButtonUIModel,
    onClick: () -> Unit,
    modifier: Modifier = Modifier,
    state: FoodieButtonState = FoodieButtonState.Enabled,
    shape: Shape = RavanTheme.shapes.r16
) {

    when (data) {
        is FoodieButtonUIModel.General -> {
            Box(
                modifier = modifier
                    .clip(shape)
                    .background(
                        getBackgroundColor(state)
                    )
                    .clickable(enabled = state == FoodieButtonState.Enabled) {
                        onClick()
                    }
                    .padding(horizontal = 16.dp, vertical = 6.dp),
                contentAlignment = Alignment.Center
            ) {

                Row(
                    modifier = Modifier,
                    verticalAlignment = Alignment.CenterVertically,
                ) {
                    when (state) {
                        FoodieButtonState.Enabled,
                        FoodieButtonState.Disabled -> {
                            data.iconRes?.let {
                                Icon(
                                    painter = painterResource(id = it),
                                    contentDescription = null,
                                    tint = getTintColor(state),
                                    modifier = Modifier
                                        .size(24.dp)
                                        .aspectRatio(1f),
                                )
                            }
                        }

                        FoodieButtonState.Loading -> {
                            FoodieProgressIndicator(
                                color = getTintColor(state),
                                modifier = Modifier
                                    .size(24.dp)
                                    .aspectRatio(1f)
                                    .padding(4.dp),
                                strokeWidth = 2.dp,
                            )
                        }
                    }
                    data.title?.let {
                        Text(
                            text = it,
                            style = RavanTheme.typography.button,
                            color = getTintColor(state),
                            modifier = Modifier
                                .padding(start = 8.dp)
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
                        getBackgroundColor(state)
                    )
                    .clickable(enabled = state == FoodieButtonState.Enabled) {
                        onClick()
                    }
                    .padding(8.dp),
                contentAlignment = Alignment.Center
            ) {
                when (state) {
                    FoodieButtonState.Enabled,
                    FoodieButtonState.Disabled -> {
                        Icon(
                            painter = painterResource(id = data.iconRes),
                            contentDescription = null,
                            tint = getTintColor(state),
                            modifier = Modifier
                                .size(32.dp)
                                .aspectRatio(1f),
                        )
                    }

                    FoodieButtonState.Loading -> {
                        FoodieProgressIndicator(
                            color = getTintColor(state),
                        )
                    }
                }
            }
        }
    }
}

@Composable
private fun getTintColor(state: FoodieButtonState) =
    when (state) {
        FoodieButtonState.Enabled,
        FoodieButtonState.Loading -> RavanTheme.colors.icon.onTertiary

        FoodieButtonState.Disabled -> RavanTheme.colors.icon.onTertiary.copy(
            alpha = 0.4f
        )
    }

@Composable
private fun getBackgroundColor(state: FoodieButtonState) =
    when (state) {
        FoodieButtonState.Enabled,
        FoodieButtonState.Loading -> RavanTheme.colors.background.tertiary

        FoodieButtonState.Disabled -> RavanTheme.colors.background.tertiary.copy(
            alpha = 0.4f
        )
    }

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
                state = FoodieButtonState.Disabled,
                onClick = {},
            )

            FoodieButton(
                data = FoodieButtonUIModel.Icon(R.drawable.ic_code),
                state = FoodieButtonState.Loading,
                onClick = {},
            )

            FoodieButton(
                data = FoodieButtonUIModel.General(
                    iconRes = R.drawable.ic_code,
                    title = "asdfa"
                ),
                onClick = {},
            )


            FoodieButton(
                data = FoodieButtonUIModel.General(
                    iconRes = R.drawable.ic_code,
                    title = "asdfa"
                ),
                state = FoodieButtonState.Disabled,
                onClick = {},
            )


            FoodieButton(
                data = FoodieButtonUIModel.General(
                    iconRes = R.drawable.ic_code,
                    title = "asdfa"
                ),
                state = FoodieButtonState.Loading,
                onClick = {},
            )
        }

    }
}
