package com.ravan.foodie.splash.ui.component

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.ravan.foodie.R
import com.ravan.foodie.domain.ui.component.FoodieFailCard
import com.ravan.foodie.domain.ui.model.FoodieFailCardUIModel
import com.ravan.foodie.domain.ui.theme.RavanTheme

@Composable
fun SplashScreen(
    shouldShowNetworkError: Boolean,
    onReload: () -> Unit,
) {

    Box(
        modifier = Modifier
            .fillMaxSize()
            .background(RavanTheme.colors.background.primary),
        contentAlignment = Alignment.Center,
    ) {
        if (shouldShowNetworkError) {
            FoodieFailCard(
                data = FoodieFailCardUIModel(
                    title = stringResource(id = R.string.network_connection_error),
                ),
                onReloadClick = onReload,
                modifier = Modifier.fillMaxWidth()
            )
        } else {
            Column(
                modifier = Modifier
                    .fillMaxSize(),
                horizontalAlignment = Alignment.CenterHorizontally,
                verticalArrangement = Arrangement.Top
            ) {
                Spacer(modifier = Modifier.weight(1f))
                Image(
                    painter = painterResource(id = R.mipmap.ic_launcher_foreground),
                    contentDescription = null,
                    contentScale = ContentScale.Fit,
                    modifier = Modifier,
                )
                Spacer(modifier = Modifier.weight(1f))
            }

        }

    }
}


@Preview
@Composable
fun SplashScreenPreview() {
    RavanTheme { SplashScreen(shouldShowNetworkError = false, onReload = {}) }
}
