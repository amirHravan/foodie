package com.ravan.foodie.domain.ui.component

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.material3.Icon
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.ravan.foodie.R
import com.ravan.foodie.domain.ui.model.FoodieTitleBarUIModel
import com.ravan.foodie.domain.ui.theme.RavanTheme

@Composable
fun FoodieTitleBar(
    data: FoodieTitleBarUIModel,
    onBackClick: () -> Unit,
    modifier: Modifier = Modifier,
    tint: Color = RavanTheme.colors.icon.onPrimary,
) {
    Column(
        modifier = modifier
    ) {
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .padding(16.dp),
            horizontalArrangement = Arrangement.Start,
            verticalAlignment = Alignment.CenterVertically,
        ) {
            Icon(
                painter = painterResource(id = R.drawable.ic_back),
                contentDescription = null,
                tint = tint,
                modifier = Modifier
                    .clip(RavanTheme.shapes.pill)
                    .clickable { onBackClick() }
            )
            Spacer(modifier = Modifier.size(16.dp))
            Text(text = data.title, color = tint, style = RavanTheme.typography.h5)
        }
        FoodieDivider(color = tint, thickness = 1)
    }

}

@Preview
@Composable
fun FoodieTitleBarPreview() {
    RavanTheme {
        FoodieTitleBar(
            data = FoodieTitleBarUIModel(title = "عنوان"),
            onBackClick = { },
        )
    }
}
