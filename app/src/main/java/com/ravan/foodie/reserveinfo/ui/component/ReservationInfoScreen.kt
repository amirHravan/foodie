package com.ravan.foodie.reserveinfo.ui.component

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.ravan.foodie.R
import com.ravan.foodie.domain.ui.theme.RavanTheme
import com.ravan.foodie.reserveinfo.ui.fixture.reserveInfoScreenUIModelFixture
import com.ravan.foodie.reserveinfo.ui.model.ReservationInfoScreenUIModel

@Composable
fun ReservationInfoScreen(
    data: ReservationInfoScreenUIModel,
    onGetForgetCodeClick: (Int, () -> Unit) -> Unit,
) {
    Column(
        modifier = Modifier
            .background(RavanTheme.colors.background.primary)
    ) {
        if (data.reservationInfoCardUIModelList.isEmpty()) {
            Box(
                modifier = Modifier
                    .weight(1f)
                    .fillMaxWidth(),
                contentAlignment = Alignment.Center
            ) {
                Text(
                    text = stringResource(id = R.string.reservation_info_screen_no_data),
                    color = RavanTheme.colors.text.onPrimary,
                    style = RavanTheme.typography.h6,
                )
            }
        } else {
            LazyColumn(
                modifier = Modifier
                    .weight(1f)
                    .fillMaxWidth()
                    .padding(top = 8.dp),
                horizontalAlignment = Alignment.CenterHorizontally,
                verticalArrangement = Arrangement.spacedBy(16.dp)
            ) {
                item {
                    Spacer(modifier = Modifier)
                }
                items(
                    data.reservationInfoCardUIModelList,
                    key = { it.farsiDate }
                ) { reservationInfoCardUIModel ->
                    ReservationInfoCard(
                        data = reservationInfoCardUIModel,
                        onGetForgetCodeClick = onGetForgetCodeClick,
                        modifier = Modifier
                            .padding(horizontal = 16.dp)
                            .clip(RavanTheme.shapes.r8)

                    )
                }
                item {
                    Spacer(modifier = Modifier)
                }
            }
        }
    }


}

@Preview
@Composable
fun ReservationInfoScreenPreview() {
    RavanTheme {
        ReservationInfoScreen(
            data = reserveInfoScreenUIModelFixture,
            onGetForgetCodeClick = { _, _ -> }
        )
    }
}
