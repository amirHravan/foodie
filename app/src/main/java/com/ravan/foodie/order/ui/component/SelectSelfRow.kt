package com.ravan.foodie.order.ui.component

import androidx.compose.animation.AnimatedVisibility
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Icon
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.draw.rotate
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.ravan.foodie.R
import com.ravan.foodie.domain.ui.component.FoodieDivider
import com.ravan.foodie.domain.ui.theme.RavanTheme
import com.ravan.foodie.order.ui.model.SelectSelfRowUIModel
import com.ravan.foodie.order.ui.model.SelfDialogRowUIModel
import com.ravan.foodie.order.ui.model.SelfDialogUIModel
import kotlinx.collections.immutable.toImmutableList

@Composable
fun SelectSelfRow(
    selectSelfRowUIModel: SelectSelfRowUIModel,
    modifier: Modifier = Modifier,
    onExpandClick: () -> Unit,
    onSelectSelfClick: (SelfDialogRowUIModel) -> Unit,
) {
    val isExpanded = remember(selectSelfRowUIModel.selfDialogUIModel) {
        mutableStateOf(selectSelfRowUIModel.selectedSelfName.isBlank() && selectSelfRowUIModel.selfDialogUIModel != null)
    }
    val (backgroundColor, borderColor) = getColors(isExpanded.value)

    Box(
        modifier = modifier
            .clip(RavanTheme.shapes.r8)
            .background(backgroundColor)
            .clickable {
                isExpanded.value = !isExpanded.value
                onExpandClick()
            }
            .border(2.dp, borderColor, RavanTheme.shapes.r8)
            .padding(8.dp),
        contentAlignment = Alignment.TopStart
    ) {
        Icon(
            painter = painterResource(id = R.drawable.ic_chevron_left),
            contentDescription = null,
            tint = borderColor,
            modifier = Modifier.rotate(if (isExpanded.value) -90f else 0f)
        )
        Column(
            modifier = Modifier
                .fillMaxWidth(),
            horizontalAlignment = Alignment.CenterHorizontally,
            verticalArrangement = Arrangement.spacedBy(8.dp)
        ) {
            if (!isExpanded.value) {
                Text(
                    text = selectSelfRowUIModel.selectedSelfName,
                    color = RavanTheme.colors.text.onPrimary,
                    style = RavanTheme.typography.h6,
                )
            }
            AnimatedVisibility(
                visible = isExpanded.value,
            ) {
                Column(
                    horizontalAlignment = Alignment.CenterHorizontally,
                    verticalArrangement = Arrangement.spacedBy(8.dp)
                ) {
                    Text(
                        text = stringResource(id = R.string.order_select_self_dialog_title),
                        color = RavanTheme.colors.text.onSecondary,
                        style = RavanTheme.typography.h6,
                    )
                    FoodieDivider(color = RavanTheme.colors.border.onSecondary)

                    selectSelfRowUIModel.selfDialogUIModel?.let { selfDialogUIModel ->
                        SelfDialog(
                            data = selfDialogUIModel,
                            onSelectSelf = {
                                onSelectSelfClick(it)
                                isExpanded.value = !isExpanded.value
                            },
                        )
                    }


                }
            }
        }
    }
}

@Composable
fun getColors(isExpanded: Boolean): Pair<Color, Color> {
    return if (isExpanded) {
        RavanTheme.colors.background.secondary to RavanTheme.colors.border.onSecondary
    } else {
        Color.Transparent to RavanTheme.colors.border.onPrimary
    }

}

@Preview
@Composable
private fun SelectSelfRowPreview() {
    RavanTheme {
        SelectSelfRow(
            selectSelfRowUIModel = SelectSelfRowUIModel(
                selectedSelfName = "",
                selfDialogUIModel = SelfDialogUIModel(
                    selfs = emptyList<SelfDialogRowUIModel>().toImmutableList(),
                )
            ),
            onExpandClick = {},
            onSelectSelfClick = {},
        )
    }
}
