package com.ravan.foodie.autoreserve.ui.component.reserve

import androidx.compose.animation.AnimatedVisibility
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Icon
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.rotate
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.ravan.foodie.R
import com.ravan.foodie.autoreserve.ui.model.ReserveResultInfoRowUIModel
import com.ravan.foodie.autoreserve.ui.model.ReserveStatus
import com.ravan.foodie.domain.ui.component.FoodieTextIconRow
import com.ravan.foodie.domain.ui.model.FoodieTextIconRowUIModel
import com.ravan.foodie.domain.ui.theme.RavanTheme
import com.ravan.foodie.order.domain.model.MealType
import kotlinx.collections.immutable.toImmutableList

@Composable
fun ReserveResultInfoRow(
    data: ReserveResultInfoRowUIModel,
    modifier: Modifier = Modifier,
    color: Color = RavanTheme.colors.text.onPrimary
) {
    val backgroundColor = when (data.status) {
        ReserveStatus.SUCCESS -> RavanTheme.colors.background.success
        ReserveStatus.FAILURE -> RavanTheme.colors.background.fail
    }

    val isExpanded = remember {
        mutableStateOf(data.status == ReserveStatus.FAILURE)
    }

    Column(
        modifier = modifier
            .clickable { isExpanded.value = !isExpanded.value }
            .background(backgroundColor)
            .padding(16.dp)
    ) {
        Row(
            modifier = Modifier
                .fillMaxWidth(),
            verticalAlignment = Alignment.CenterVertically,
            horizontalArrangement = Arrangement.spacedBy(16.dp),
        ) {
            Icon(
                painter = painterResource(id = data.mealType.getIcon()),
                contentDescription = "MealType Icon",
                tint = color
            )
            Text(
                text = "${data.dayName}: ${data.foodName}",
                color = color,
                style = RavanTheme.typography.body1,
            )
            Spacer(modifier = Modifier.weight(1f))
            Icon(
                painter = painterResource(id = R.drawable.ic_chevron_right),
                contentDescription = "Expand Icon",
                modifier = Modifier
                    .rotate(if (isExpanded.value) 90f else 0f),
                tint = color
            )
        }
        AnimatedVisibility(visible = isExpanded.value) {
            Column {
                Text(
                    text = data.message,
                    color = color,
                    style = RavanTheme.typography.body2,
                    modifier = Modifier.padding(
                        start = 16.dp,
                        top = 16.dp,
                        end = 8.dp,
                        bottom = 8.dp
                    ),
                )

                if (data.status == ReserveStatus.SUCCESS) {
                    data.choices.forEach {
                        FoodieTextIconRow(
                            data = FoodieTextIconRowUIModel(
                                iconRes = if (it == data.foodName) R.drawable.ic_check else R.drawable.ic_close,
                                text = it,
                            ),
                            modifier = Modifier.padding(start = 24.dp),
                            color = color
                        )

                    }
                }
            }

        }
    }

}

@Preview
@Composable
private fun ReserveResultInfoRowPreview() {
    RavanTheme {
        ReserveResultInfoRow(
            data = ReserveResultInfoRowUIModel(
                foodName = "چلو جوجه کباب",
                status = ReserveStatus.SUCCESS,
                message = "غذاتون با موفقیت رزرو شد جناب / سرکار",
                mealType = MealType.LUNCH,
                dayName = "شنبه",
                choices = listOf("چلو جوجه کباب", "چلو مرغ کباب", "چلو قرمه سبزی").toImmutableList()
            )
        )
    }
}