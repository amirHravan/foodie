package com.ravan.foodie.settings.ui.component.body

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.ColumnScope
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import com.ravan.foodie.R
import com.ravan.foodie.domain.ui.theme.RavanTheme
import com.ravan.foodie.settings.ui.model.SettingsQuestionAnswerRowUIModel

@Composable
fun ColumnScope.SettingsBottomSheet() {
    val questionAnswerList = remember(true) {
        listOf(
            (R.string.about_us_question_1 to R.string.about_us_answer_1),
            (R.string.about_us_question_2 to R.string.about_us_answer_2),
            (R.string.about_us_question_3 to R.string.about_us_answer_3),
        )
    }
    LazyColumn(
        modifier = Modifier
            .fillMaxSize()
            .background(RavanTheme.colors.background.secondary)
            .padding(horizontal = 16.dp),
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.spacedBy(8.dp)
    ) {
        item {
            Text(
                text = stringResource(id = R.string.about_us_title),
                style = RavanTheme.typography.h5,
                color = RavanTheme.colors.text.onSecondary,
                modifier = Modifier
            )
        }
        item {
            Text(
                text = stringResource(id = R.string.about_us_text),
                style = RavanTheme.typography.body2,
                color = RavanTheme.colors.text.onSecondary,
            )
        }

        item {}

        items(count = questionAnswerList.size, key = { it }) {
            SettingsQuestionAnswerRow(
                data = SettingsQuestionAnswerRowUIModel(
                    question = stringResource(id = questionAnswerList[it].first),
                    answer = stringResource(id = questionAnswerList[it].second),
                )
            )
        }
    }
}