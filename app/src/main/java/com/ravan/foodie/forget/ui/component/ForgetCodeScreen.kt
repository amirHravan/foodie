package com.ravan.foodie.forget.ui.component

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.ravan.foodie.domain.ui.theme.RavanTheme
import com.ravan.foodie.forget.ui.fixture.forgetCodeScreenUIModelFixture
import com.ravan.foodie.forget.ui.model.ForgetCodeScreenUIModel

@Composable
fun ForgetCodeScreen(
    data: ForgetCodeScreenUIModel,
    onGetForgetCode: (Int) -> Unit,
) {
    Column(
        modifier = Modifier
            .fillMaxSize()
            .background(RavanTheme.colors.background.primary)
            .padding(16.dp)
    ) {
        ForgetCodeItemCard(
            data = data.forgetCodeItemUIModel,
            onGetForgetCodeClick = onGetForgetCode,
            modifier = Modifier.fillMaxSize()

        )
    }


}

@Preview
@Composable
private fun ForgetCodeScreenPreview() {
    RavanTheme {
        ForgetCodeScreen(
            data = forgetCodeScreenUIModelFixture,
            onGetForgetCode = {}
        )
    }
}
