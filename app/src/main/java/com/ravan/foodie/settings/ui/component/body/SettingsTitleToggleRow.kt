package com.ravan.foodie.settings.ui.component.body

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.material.Text
import androidx.compose.material3.Switch
import androidx.compose.material3.SwitchDefaults
import androidx.compose.runtime.Composable
import androidx.compose.runtime.CompositionLocalProvider
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalLayoutDirection
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.LayoutDirection
import com.ravan.foodie.domain.ui.theme.RavanTheme
import com.ravan.foodie.settings.ui.model.SettingsTitleToggleRowUIModel

@Composable
fun SettingsTitleToggleRow(
    data: SettingsTitleToggleRowUIModel,
    onToggleChange: (Boolean) -> Unit,
    modifier: Modifier = Modifier,
    initState: Boolean = false
) {
    val checked = remember {
        mutableStateOf(initState)
    }

    Row(
        modifier = modifier.fillMaxWidth(),
        horizontalArrangement = Arrangement.Center,
        verticalAlignment = Alignment.CenterVertically,
    ) {
        Text(
            text = data.title,
            style = RavanTheme.typography.body1,
            color = RavanTheme.colors.text.onPrimary,
        )
        Spacer(modifier = Modifier.weight(1f))
        CompositionLocalProvider(LocalLayoutDirection provides LayoutDirection.Ltr) {
            Switch(
                checked = checked.value,
                onCheckedChange = {
                    checked.value = it
                    onToggleChange(it)
                },
                colors = SwitchDefaults.colors(
                    checkedThumbColor = RavanTheme.colors.text.onTertiary,
                    checkedTrackColor = RavanTheme.colors.background.tertiary,
                    checkedBorderColor = RavanTheme.colors.background.tertiary,
                    checkedIconColor = RavanTheme.colors.icon.onPrimary,
                    uncheckedThumbColor = RavanTheme.colors.text.onTertiary.copy(alpha = 0.5f),
                    uncheckedTrackColor = RavanTheme.colors.background.tertiary,
                    uncheckedBorderColor = RavanTheme.colors.background.tertiary,
                    uncheckedIconColor = RavanTheme.colors.icon.onSecondary,
                ),

                )
        }
    }

}

@Preview
@Composable
private fun SettingsTitleToggleRowPreview() {
    RavanTheme {
        SettingsTitleToggleRow(
            data = SettingsTitleToggleRowUIModel("اعلان\u200Cها"),
            onToggleChange = {})
    }
}