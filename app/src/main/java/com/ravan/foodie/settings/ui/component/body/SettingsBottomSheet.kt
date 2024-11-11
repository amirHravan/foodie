package com.ravan.foodie.settings.ui.component.body

import android.widget.Toast
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.ravan.foodie.R
import com.ravan.foodie.domain.ui.component.FoodieButton
import com.ravan.foodie.domain.ui.model.FoodieButtonUIModel
import com.ravan.foodie.domain.ui.theme.RavanTheme
import com.ravan.foodie.domain.util.sendMail
import com.ravan.foodie.settings.ui.model.SettingsQuestionAnswerRowUIModel
import okhttp3.internal.toImmutableList

@Composable
fun SettingsBottomSheet(
    onSendFeedbackClick: (() -> Unit) -> Unit,
    onGithubRedirectClick: () -> Unit,
) {
    val questionAnswerList = remember(true) {
        listOf(
            (R.string.about_us_question_2 to R.string.about_us_answer_2),
            (R.string.about_us_question_3 to R.string.about_us_answer_3),
            (R.string.about_us_question_5 to R.string.about_us_answer_5),
        ).toImmutableList()
    }

    val context = LocalContext.current

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
                    .padding(vertical = 8.dp)
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

        item {
            SettingsQuestionAnswerRow(
                data = SettingsQuestionAnswerRowUIModel(
                    question = stringResource(id = R.string.about_us_question_1),
                    answer = stringResource(id = R.string.about_us_answer_1)
                ),
            ) {
                FoodieButton(data = FoodieButtonUIModel.General(
                    iconRes = R.drawable.ic_github,
                    title = stringResource(id = R.string.code_github_address)
                ), onClick = { onGithubRedirectClick() })
            }
        }

        items(count = questionAnswerList.size, key = { it }) {
            SettingsQuestionAnswerRow(
                data = SettingsQuestionAnswerRowUIModel(
                    question = stringResource(id = questionAnswerList[it].first),
                    answer = stringResource(id = questionAnswerList[it].second),
                )
            )
        }

        item {}

        item {
            FoodieButton(
                data = FoodieButtonUIModel.General(
                    title = stringResource(id = R.string.about_us_send_feedback),
                    iconRes = R.drawable.ic_mail,
                ), onClick = {
                    onSendFeedbackClick {
                        context.sendMail(
                            to = "amir.h.rnn@gmail.com",
                            subject = "انتقاد/پیشنهاد برنامه رزرو غذا شریف"
                        )

                        Toast.makeText(
                            context,
                            "تشکر بابت ارسال بازخورد",
                            Toast.LENGTH_SHORT
                        ).show()
                    }
                }
            )
        }

        item {}
    }
}

@Preview
@Composable
private fun SettingsBottomSheetPreview() {
    RavanTheme {
        Column {
            SettingsBottomSheet(
                onSendFeedbackClick = {},
                onGithubRedirectClick = {},
            )
        }
    }

}