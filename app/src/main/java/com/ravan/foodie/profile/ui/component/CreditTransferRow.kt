package com.ravan.foodie.profile.ui.component

import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.offset
import androidx.compose.material3.Icon
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.ravan.foodie.R
import com.ravan.foodie.domain.ui.theme.RavanTheme
import com.ravan.foodie.domain.util.toLocalNumber
import com.ravan.foodie.profile.api.dto.nurture.toCreditTransfer
import com.ravan.foodie.profile.ui.fixture.profileCreditTransferDto1
import com.ravan.foodie.profile.ui.model.CreditTransferUIModel
import com.ravan.foodie.profile.ui.model.toCreditTransferUIModel

@Composable
fun CreditTransferRow(
    data: CreditTransferUIModel,
    modifier: Modifier = Modifier,
) {
    Row(
        modifier = modifier.fillMaxWidth(),
        verticalAlignment = Alignment.CenterVertically,
    ) {
        Icon(
            painter = painterResource(id = getIconResource(data.amount > 0)),
            contentDescription = null,
            tint = RavanTheme.colors.icon.onSuccess
        )
        Spacer(modifier = Modifier.weight(1f))
        Text(
            text = data.amount.toString().toLocalNumber(),
            style = RavanTheme.typography.body1,
            color = RavanTheme.colors.text.onSecondary,
            modifier = Modifier.offset(y = (-6).dp)
        )
        Spacer(modifier = Modifier.weight(1f))
        Text(
            text = data.date.toLocalNumber(),
            style = RavanTheme.typography.body1,
            color = RavanTheme.colors.text.onSecondary,
            modifier = Modifier.offset(y = (-6).dp)
        )
    }

}

@Composable
private fun getIconResource(isPositive: Boolean): Int {
    return if (isPositive) {
        R.drawable.ic_add
    } else {
        R.drawable.ic_remove
    }
}

@Preview
@Composable
private fun CreditTransferRowPreview() {
    RavanTheme {
        CreditTransferRow(
            data = profileCreditTransferDto1.toCreditTransfer().toCreditTransferUIModel()
        )
    }
}
