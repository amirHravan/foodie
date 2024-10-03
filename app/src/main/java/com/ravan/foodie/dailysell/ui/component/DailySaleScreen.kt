package com.ravan.foodie.dailysell.ui.component

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import com.ravan.foodie.R
import com.ravan.foodie.dailysell.ui.model.DailySellScreenUIModel
import com.ravan.foodie.domain.ui.theme.RavanTheme

@Composable
fun DailySaleScreen(
    uiModel: DailySellScreenUIModel,
    onOrderDailySaleClick: (Int, () -> Unit) -> Unit,
    onGetForgetCodeClick: (Int, () -> Unit) -> Unit,
    modifier: Modifier = Modifier
) {
    Box(
        modifier = modifier
            .background(RavanTheme.colors.background.primary),
        contentAlignment = Alignment.Center,
    ) {
        if (uiModel.dailySaleCardUIModelList.isEmpty()) {
            Box(
                modifier = Modifier
                    .fillMaxSize(),
                contentAlignment = Alignment.Center
            ) {
                Text(
                    text = stringResource(id = R.string.daily_sell_no_program_found),
                    color = RavanTheme.colors.text.onPrimary,
                    style = RavanTheme.typography.h6,
                )
            }
        } else {
            LazyColumn(
                modifier = Modifier.fillMaxSize(),
                horizontalAlignment = Alignment.CenterHorizontally,
                verticalArrangement = Arrangement.spacedBy(8.dp)
            ) {
                item {}
                items(count = uiModel.dailySaleCardUIModelList.size, key = { it }) {
                    DailySaleCard(
                        uiModel = uiModel.dailySaleCardUIModelList[it],
                        onOrderDailySaleClick = onOrderDailySaleClick,
                        onGetForgetCodeClick = onGetForgetCodeClick
                    )
                }
                item {}
            }
        }
    }
}