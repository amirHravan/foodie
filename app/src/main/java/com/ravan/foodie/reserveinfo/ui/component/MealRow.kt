package com.ravan.foodie.reserveinfo.ui.component

import android.widget.Toast
import androidx.compose.animation.AnimatedVisibility
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalClipboardManager
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.AnnotatedString
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.ravan.foodie.R
import com.ravan.foodie.domain.ui.component.FoodieButton
import com.ravan.foodie.domain.ui.component.FoodieButtonState
import com.ravan.foodie.domain.ui.component.FoodieTextIconRow
import com.ravan.foodie.domain.ui.model.FoodieButtonUIModel
import com.ravan.foodie.domain.ui.model.FoodieTextIconRowUIModel
import com.ravan.foodie.domain.ui.theme.RavanTheme
import com.ravan.foodie.domain.util.toLocalNumber
import com.ravan.foodie.reserveinfo.ui.fixture.reserveInfoMealRowUIModelFixture1
import com.ravan.foodie.reserveinfo.ui.model.MealRowUIModel

@Composable
fun MealRow(
    data: MealRowUIModel,
    onGetForgetCodeClick: (Int, () -> Unit) -> Unit,
    modifier: Modifier = Modifier,
) {
    val buttonState = remember(data) {
        mutableStateOf(FoodieButtonState.Enabled)
    }

    val clipBoardManager = LocalClipboardManager.current
    val context = LocalContext.current

    Column(
        modifier = modifier
            .fillMaxWidth()
            .padding(horizontal = 16.dp),
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.Center
    ) {
        Text(
            text = data.foodName,
            color = RavanTheme.colors.text.onSecondary,
            style = RavanTheme.typography.h6,
            modifier = Modifier
                .padding(vertical = 8.dp)
                .padding(start = 4.dp)
        )
        FoodieTextIconRow(
            data = FoodieTextIconRowUIModel(
                iconRes = R.drawable.ic_clock,
                text = data.mealName,
            ),
            color = RavanTheme.colors.text.onSecondary,
            textStyle = RavanTheme.typography.body2,
        )
        FoodieTextIconRow(
            data = FoodieTextIconRowUIModel(
                iconRes = R.drawable.ic_location,
                text = data.selfName,
            ),
            color = RavanTheme.colors.text.onSecondary,
            textStyle = RavanTheme.typography.body2,
        )
        AnimatedVisibility(visible = data.forgetCode != null) {
            FoodieButton(
                data = FoodieButtonUIModel.General(
                    title = data.forgetCode?.toLocalNumber() ?: "کد فراموشی نداریم!",
                    iconRes = R.drawable.ic_content_copy,
                ),
                onClick = {
                    clipBoardManager.setText(
                        AnnotatedString(
                            text = data.forgetCode ?: ""
                        )
                    )
                    Toast.makeText(context, "کد فراموشی کپی شد", Toast.LENGTH_SHORT).show()
                }
            )
        }
        AnimatedVisibility(visible = data.forgetCode == null) {
            FoodieButton(
                data = FoodieButtonUIModel.General(
                    title = stringResource(R.string.get_forget_code_button_label),
                    iconRes = null
                ),
                onClick = {
                    buttonState.value = FoodieButtonState.Loading
                    onGetForgetCodeClick(data.reserveId) {
                        buttonState.value = FoodieButtonState.Enabled
                    }
                },
                modifier = Modifier
                    .padding(top = 8.dp),
                shape = RavanTheme.shapes.r8,
                state = buttonState.value,

                )

        }
    }


}

@Preview
@Composable
fun MealRowPreview() {
    RavanTheme {
        MealRow(
            data = reserveInfoMealRowUIModelFixture1,
            onGetForgetCodeClick = { _, _ -> }
        )
    }
}
