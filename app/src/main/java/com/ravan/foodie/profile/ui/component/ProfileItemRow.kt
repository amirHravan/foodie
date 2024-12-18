package com.ravan.foodie.profile.ui.component

import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import com.ravan.foodie.R
import com.ravan.foodie.domain.ui.theme.RavanTheme
import com.ravan.foodie.profile.ui.model.ProfileItemRowUIModel

@Composable
fun ProfileItemRow(
    data: ProfileItemRowUIModel,
    modifier: Modifier = Modifier,
    color: Color = RavanTheme.colors.text.onPrimary
) {
    Row(
        modifier = modifier.fillMaxWidth(),
        verticalAlignment = Alignment.CenterVertically,
    ) {
        Text(
            text = data.title,
            style = RavanTheme.typography.body1,
            color = color,
            modifier = Modifier.weight(1f)
        )

        Text(
            text = data.value,
            style = RavanTheme.typography.body1.copy(textAlign = TextAlign.End),
            color = color,
            modifier = Modifier.weight(1f)
        )
    }

}

@Preview
@Composable
private fun ProfileItemRowPreview() {
    RavanTheme {
        ProfileItemRow(
            data = ProfileItemRowUIModel(
                title = stringResource(id = R.string.nurture_profile_row_item_name),
                value = "۸۶۰۰-"
            )
        )
    }
}
