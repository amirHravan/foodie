package com.ravan.foodie.account.ui.component

import androidx.compose.material3.AlertDialog
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.runtime.Composable
import androidx.compose.ui.res.stringResource
import com.ravan.foodie.R
import com.ravan.foodie.account.ui.model.AccountRowUIModel
import com.ravan.foodie.domain.ui.theme.RavanTheme

@Composable
fun RemoveAccountDialog(
    account: AccountRowUIModel,
    onConfirm: () -> Unit,
    onDismiss: () -> Unit,
) {
    AlertDialog(
        onDismissRequest = onDismiss,
        containerColor = RavanTheme.colors.background.secondary,
        title = {
            Text(
                text = stringResource(R.string.accounts_remove_title),
                style = RavanTheme.typography.h5,
                color = RavanTheme.colors.text.onSecondary,
            )
        },
        text = {
            Text(
                text = stringResource(R.string.accounts_remove_message, account.title),
                style = RavanTheme.typography.body1,
                color = RavanTheme.colors.text.onSecondary,
            )
        },
        confirmButton = {
            TextButton(onClick = onConfirm) {
                Text(
                    text = stringResource(R.string.accounts_remove_confirm),
                    style = RavanTheme.typography.button,
                    color = RavanTheme.colors.border.onFail,
                )
            }
        },
        dismissButton = {
            TextButton(onClick = onDismiss) {
                Text(
                    text = stringResource(R.string.accounts_cancel),
                    style = RavanTheme.typography.button,
                    color = RavanTheme.colors.text.onSecondary,
                )
            }
        },
    )
}
