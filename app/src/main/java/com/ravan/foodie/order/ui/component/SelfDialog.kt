package com.ravan.foodie.order.ui.component

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.ravan.foodie.R
import com.ravan.foodie.domain.ui.component.FoodieDivider
import com.ravan.foodie.domain.ui.theme.RavanTheme
import com.ravan.foodie.order.ui.fixture.selfDialogUIModelFixture
import com.ravan.foodie.order.ui.model.SelfDialogUIModel
import com.ravan.foodie.order.ui.model.SelfRowUIModel

@Composable
fun SelfDialog(
    data: SelfDialogUIModel,
    onSelectSelf: (SelfRowUIModel) -> Unit,
    onBackClick: () -> Unit,
    modifier: Modifier = Modifier
) {
    Box(
        modifier = modifier
            .fillMaxSize()
            .clickable { onBackClick() },
        contentAlignment = Alignment.BottomCenter
    ) {
        LazyColumn(
            modifier = Modifier
                .fillMaxHeight(0.6f)
                .clip(RoundedCornerShape(topEnd = 16.dp, topStart = 16.dp))
                .clickable { }
                .background(RavanTheme.colors.background.secondary)
        ) {
            item {
                Text(
                    text = stringResource(R.string.order_select_self_dialog_title),
                    color = RavanTheme.colors.text.onSecondary,
                    style = RavanTheme.typography.h4.copy(textAlign = TextAlign.Center),
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(vertical = 12.dp)
                )
                FoodieDivider(color = RavanTheme.colors.border.onSecondary)
            }
            items(data.selfs, key = { it.id }) {
                SelfDialogItem(data = it, onSelectSelf = { onSelectSelf(it) })
                FoodieDivider(thickness = 1, color = RavanTheme.colors.border.onSecondary)
            }
            item {
                Spacer(modifier = Modifier.size(64.dp))
            }
        }

    }
}

@Composable
private fun SelfDialogItem(
    data: SelfRowUIModel,
    onSelectSelf: () -> Unit,
    modifier: Modifier = Modifier,
) {
    Box(
        contentAlignment = Alignment.TopStart,
        modifier = modifier
            .fillMaxWidth()
            .clickable { onSelectSelf() }
            .padding(horizontal = 16.dp, vertical = 12.dp)
    ) {
        Text(
            text = data.name,
            color = RavanTheme.colors.text.onSecondary,
            style = RavanTheme.typography.h6
        )
    }

}

@Preview
@Composable
private fun SelectSelfDialogPreview() {
    RavanTheme {
        SelfDialog(
            data = selfDialogUIModelFixture,
            onBackClick = {},
            onSelectSelf = {}
        )
    }
}
