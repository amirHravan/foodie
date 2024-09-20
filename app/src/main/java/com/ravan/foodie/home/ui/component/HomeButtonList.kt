package com.ravan.foodie.home.ui.component

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.ravan.foodie.R
import com.ravan.foodie.domain.ui.theme.RavanTheme
import com.ravan.foodie.home.ui.model.HomeButtonUIModel

@Composable
fun HomeButtonList(
    onCodeClick: () -> Unit,
    onLunchClick: () -> Unit,
    onCalenderClick: () -> Unit,
    onProfileClick: () -> Unit,
    modifier: Modifier = Modifier,
) {
    LazyRow(
        modifier = modifier,
        verticalAlignment = Alignment.CenterVertically,
        horizontalArrangement = Arrangement.spacedBy(16.dp),
    ) {
        item {
            HomeButton(
                data = HomeButtonUIModel(R.drawable.ic_calender),
                onClick = onCalenderClick,
            )
        }

        item {
            HomeButton(
                data = HomeButtonUIModel(R.drawable.ic_lunch),
                onClick = onLunchClick,
            )
        }
        item {
            HomeButton(
                data = HomeButtonUIModel(R.drawable.ic_code),
                onClick = onCodeClick,
            )
        }

        item {
            HomeButton(
                data = HomeButtonUIModel(R.drawable.ic_profile_circle),
                onClick = onProfileClick,
            )
        }
    }
}

@Preview
@Composable
fun HomeButtonListPreview() {
    RavanTheme {
        HomeButtonList(
            onCodeClick = {},
            onLunchClick = {},
            onCalenderClick = {},
            onProfileClick = {},
            modifier = Modifier
        )
    }
}
