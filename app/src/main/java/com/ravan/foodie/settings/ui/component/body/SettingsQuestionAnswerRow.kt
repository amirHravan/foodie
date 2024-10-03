package com.ravan.foodie.settings.ui.component.body

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
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
import com.ravan.foodie.domain.ui.theme.RavanTheme
import com.ravan.foodie.settings.ui.model.SettingsQuestionAnswerRowUIModel

@Composable
fun SettingsQuestionAnswerRow(
    data: SettingsQuestionAnswerRowUIModel,
    modifier: Modifier = Modifier,
    color: Color = RavanTheme.colors.text.onSecondary
) {
    val isExpanded = remember {
        mutableStateOf(false)
    }

    Column(
        modifier = modifier
            .fillMaxWidth(),
        verticalArrangement = Arrangement.Top,
        horizontalAlignment = Alignment.CenterHorizontally
    ) {

        Row(
            modifier = Modifier
                .fillMaxWidth()
                .clickable {
                    isExpanded.value = !isExpanded.value
                }
                .padding(8.dp),
            verticalAlignment = Alignment.CenterVertically,
            horizontalArrangement = Arrangement.SpaceBetween
        ) {
            Text(
                text = data.question,
                style = RavanTheme.typography.h6,
                color = color,
                modifier = Modifier
                    .fillMaxWidth(0.8f)
            )
            Icon(
                painter = painterResource(id = R.drawable.ic_chevron_right),
                contentDescription = "Expand",
                tint = color,
                modifier = Modifier
                    .rotate(if (isExpanded.value) 90f else 0f)
            )
        }

        if (isExpanded.value) {
            Text(
                text = data.answer,
                style = RavanTheme.typography.body2,
                color = color,
                modifier = Modifier.padding(horizontal = 16.dp, vertical = 8.dp),
            )
        }

    }

}

@Preview
@Composable
private fun SettingsQuestionAnswerRowPreview() {
    RavanTheme {
        SettingsQuestionAnswerRow(
            data = SettingsQuestionAnswerRowUIModel(
                question = "کد این نرم افزار اوپن سورسه؟",
                answer = "در حال حاضر نه، چون بعضی از بخش\u200Cها با متود چابک (تف\u200Cمال) آماده شده؛ لذا به زودی درستش می\u200Cکنم و اوپن سورسش می\u200Cکنم."
            ),
            color = RavanTheme.colors.text.onPrimary
        )
    }
}