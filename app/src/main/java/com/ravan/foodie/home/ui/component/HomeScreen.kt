package com.ravan.foodie.home.ui.component

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.ravan.foodie.domain.ui.theme.RavanTheme


@Composable
fun HomeScreen(
    onLunchClick: () -> Unit,
    onCalenderClick: () -> Unit,
    onProfileClick: () -> Unit,
    onSettingsClick: () -> Unit,
    onAutomaticReserveClick: () -> Unit,
    onAboutUsClick: () -> Unit,
    onDailySaleClick: () -> Unit,

    ) {
    Column(
        modifier = Modifier
            .fillMaxSize()
            .background(RavanTheme.colors.background.primary),
        horizontalAlignment = Alignment.Start,
    ) {
        Box(modifier = Modifier.weight(1f))
        HomeButtonList(
            onLunchClick = onLunchClick,
            onCalenderClick = onCalenderClick,
            onProfileClick = onProfileClick,
            onSettingsClick = onSettingsClick,
            onAutomaticReserveClick = onAutomaticReserveClick,
            onAboutUsClick = onAboutUsClick,
            onDailySaleClick = onDailySaleClick,
            modifier = Modifier.padding(bottom = 16.dp)
        )

    }
}

@Preview
@Composable
fun HomeScreenPreview() {
    RavanTheme {
        HomeScreen(
            onLunchClick = {},
            onProfileClick = {},
            onCalenderClick = {},
            onSettingsClick = {},
            onAutomaticReserveClick = {},
            onAboutUsClick = {},
            onDailySaleClick = {},
        )
    }

}