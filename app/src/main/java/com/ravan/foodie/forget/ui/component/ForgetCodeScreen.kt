package com.ravan.foodie.forget.ui.component

import androidx.compose.animation.AnimatedVisibility
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.ravan.foodie.domain.ui.component.FoodieInformationBox
import com.ravan.foodie.domain.ui.model.FoodieInformationBoxUIModel
import com.ravan.foodie.domain.ui.theme.RavanTheme
import com.ravan.foodie.forget.ui.fixture.forgetCodeScreenUIModelFixture
import com.ravan.foodie.forget.ui.model.ForgetCodeScreenUIModel

@Composable
fun ForgetCodeScreen(
    data: ForgetCodeScreenUIModel,
    informationBoxUIModel: FoodieInformationBoxUIModel?,
    showNotification: Boolean = false,
    buttonEnable: Boolean = true,
    onGetForgetCode: (Int) -> Unit,
) {
    Box(modifier = Modifier.fillMaxSize()) {
        Column(
            modifier = Modifier
                .fillMaxSize()
                .background(RavanTheme.colors.background.primary)
                .padding(16.dp)
        ) {
            data.forgetCodeItemUIModelList.forEach { forgetCodeItem ->
                ForgetCodeItemCard(
                    data = forgetCodeItem,
                    onGetForgetCodeClick = onGetForgetCode,
                    modifier = Modifier.weight(1f),
                    buttonEnable = buttonEnable
                )
            }
        }
        AnimatedVisibility(visible = showNotification) {
            informationBoxUIModel?.let {
                FoodieInformationBox(
                    data = it,
                    modifier = Modifier.fillMaxWidth()
                )
            }


        }
    }


}

@Preview
@Composable
private fun ForgetCodeScreenPreview() {
    RavanTheme {
        ForgetCodeScreen(
            data = forgetCodeScreenUIModelFixture,
            onGetForgetCode = {},
            buttonEnable = true,
            informationBoxUIModel = null,

            )
    }
}
