package com.ravan.foodie.domain.ui.component

import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.offset
import androidx.compose.foundation.layout.size
import androidx.compose.material3.Icon
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.ravan.foodie.R
import com.ravan.foodie.domain.ui.model.FoodieTextIconRowUIModel
import com.ravan.foodie.domain.ui.theme.RavanTheme

@Composable
fun FoodieTextIconRow(
    data: FoodieTextIconRowUIModel,
    modifier: Modifier = Modifier,
    color: Color = RavanTheme.colors.text.onPrimary,
    textStyle: TextStyle = RavanTheme.typography.body1
) {
    Row(
        verticalAlignment = Alignment.CenterVertically,
        modifier = modifier
    ) {
        Icon(
            painter = painterResource(id = data.iconRes),
            contentDescription = null,
            tint = color,
            modifier = Modifier.size(18.dp)
        )
        Spacer(modifier = Modifier.size(6.dp))
        Text(
            text = data.text,
            color = color,
            modifier = Modifier.offset(y = (-4).dp),
            style = textStyle
        )

    }
}

@Preview
@Composable
fun FoodieTextIconRowPreview() {
    RavanTheme {
        FoodieTextIconRow(
            data = FoodieTextIconRowUIModel(
                iconRes = R.drawable.ic_home,
                text = "خانه"
            )
        )
    }
}
