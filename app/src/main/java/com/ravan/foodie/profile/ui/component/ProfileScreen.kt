package com.ravan.foodie.profile.ui.component

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.ravan.foodie.R
import com.ravan.foodie.domain.ui.component.FoodieDivider
import com.ravan.foodie.domain.ui.theme.RavanTheme
import com.ravan.foodie.domain.util.toLocalNumber
import com.ravan.foodie.profile.api.dto.nurture.toSamadNurtureProfile
import com.ravan.foodie.profile.ui.fixture.profileNurtureProfileDto
import com.ravan.foodie.profile.ui.model.ProfileItemRowUIModel
import com.ravan.foodie.profile.ui.model.ProfileScreenUIModel
import com.ravan.foodie.profile.ui.model.toProfileScreenUIModel

@Composable
fun ProfileScreen(
    data: ProfileScreenUIModel,
) {
    Column(
        modifier = Modifier
            .fillMaxSize()
            .background(RavanTheme.colors.background.primary)
            .padding(top = 16.dp),
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.Top,
    ) {
        ProfileItemRow(
            data = ProfileItemRowUIModel(
                title = stringResource(R.string.nurture_profile_row_item_name),
                value = data.fullName
            ),
            modifier = Modifier.padding(horizontal = 16.dp, vertical = 8.dp)
        )
        ProfileItemRow(
            data = ProfileItemRowUIModel(
                title = stringResource(R.string.nurture_profile_row_item_usrname),
                value = data.userName.toLocalNumber()
            ), modifier = Modifier.padding(horizontal = 16.dp, vertical = 8.dp)
        )
        ProfileItemRow(
            data = ProfileItemRowUIModel(
                title = stringResource(R.string.nurture_profile_row_item_credit),
                value = data.credit.toLocalNumber()
            ), modifier = Modifier.padding(horizontal = 16.dp, vertical = 8.dp)
        )
        if (data.creditTransfers.isNotEmpty()) {
            LazyColumn(
                modifier = Modifier
                    .padding(top = 16.dp)
                    .clip(RavanTheme.shapes.r12)
                    .weight(1f)
                    .background(RavanTheme.colors.background.secondary)
                    .padding(horizontal = 16.dp, vertical = 8.dp),
                verticalArrangement = Arrangement.spacedBy(8.dp),
                horizontalAlignment = Alignment.CenterHorizontally,
            ) {
                item {
                    Text(
                        text = stringResource(R.string.nurture_profile_row_item_transactions),
                        style = RavanTheme.typography.h6,
                        modifier = Modifier.padding(8.dp)
                    )
                    FoodieDivider(color = RavanTheme.colors.border.onSecondary)
                }
                items(data.creditTransfers.size, key = { it }) { index ->
                    CreditTransferRow(
                        data = data.creditTransfers[index],
                        modifier = Modifier
                            .fillMaxWidth()
                            .padding(4.dp)
                    )
                    FoodieDivider(
                        thickness = 1,
                        color = RavanTheme.colors.border.onSecondary,
                        alpha = 0.2f
                    )
                }
            }
        }
    }
}

@Preview
@Composable
private fun ProfileScreenPreview() {
    RavanTheme {
        ProfileScreen(
            data = profileNurtureProfileDto.toSamadNurtureProfile().toProfileScreenUIModel()
        )
    }
}
