package com.ravan.foodie.order.ui.component

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.ravan.foodie.domain.ui.component.FoodieDivider
import com.ravan.foodie.domain.ui.theme.RavanTheme
import com.ravan.foodie.order.ui.fixture.selfDialogUIModelFixture
import com.ravan.foodie.order.ui.model.SelfDialogRowUIModel
import com.ravan.foodie.order.ui.model.SelfDialogUIModel

@Composable
fun SelfDialog(
    data: SelfDialogUIModel,
    onSelectSelf: (SelfDialogRowUIModel) -> Unit,
) {
    data.selfs.forEach { selfDialogItemUIModel ->
        Text(
            text = selfDialogItemUIModel.name,
            color = RavanTheme.colors.text.onSecondary,
            style = RavanTheme.typography.h6.copy(textAlign = TextAlign.Center),
            modifier = Modifier
                .padding(4.dp)
                .fillMaxWidth()
                .clickable { onSelectSelf(selfDialogItemUIModel) }
        )
        if (selfDialogItemUIModel != data.selfs.last()) {
            FoodieDivider(
                color = RavanTheme.colors.border.onSecondary,
                thickness = 1.dp,
            )
        }
    }
}

@Preview
@Composable
private fun SelectSelfDialogPreview() {
    RavanTheme {
        Column {
            SelfDialog(
                data = selfDialogUIModelFixture,
                onSelectSelf = {}
            )
        }
    }
}
