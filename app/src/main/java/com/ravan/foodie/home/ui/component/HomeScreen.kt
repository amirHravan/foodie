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
    onCodeClick: () -> Unit,
    onLunchClick: () -> Unit,
    onCalenderClick: () -> Unit,
    onProfileClick: () -> Unit,
) {
    Column(
        modifier = Modifier
            .fillMaxSize()
            .background(RavanTheme.colors.background.primary),
        horizontalAlignment = Alignment.Start,
    ) {
        Box(modifier = Modifier.weight(1f))
        HomeButtonList(
            onCodeClick = onCodeClick,
            onLunchClick = onLunchClick,
            onCalenderClick = onCalenderClick,
            onProfileClick = onProfileClick,
            modifier = Modifier.padding(start = 16.dp, top = 16.dp, bottom = 16.dp)
        )

    }
}

@Preview
@Composable
fun HomeScreenPreview() {
    RavanTheme {
        HomeScreen(
            onCodeClick = {},
            onLunchClick = {},
            onProfileClick = {},
            onCalenderClick = {},
        )
    }

}