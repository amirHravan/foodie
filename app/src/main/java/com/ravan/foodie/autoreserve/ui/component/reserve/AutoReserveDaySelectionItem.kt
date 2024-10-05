package com.ravan.foodie.autoreserve.ui.component.reserve

import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.material3.Icon
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.ravan.foodie.R
import com.ravan.foodie.autoreserve.ui.model.AutoReserveDaySelectionItemUIModel
import com.ravan.foodie.domain.ui.theme.RavanTheme

@Composable
fun AutoReserveDaySelectionItem(
    data: AutoReserveDaySelectionItemUIModel,
    onSelectClick: (Boolean) -> Unit,
    modifier: Modifier = Modifier,
    isSelected: Boolean = false,
) {
    val _isSelected = remember {
        mutableStateOf(isSelected)
    }
    val backgroundColor = getBackgroundColor(_isSelected.value)
    val textColor = getTextColor(isSelected = _isSelected.value)

    Row(
        modifier = modifier
            .clip(RavanTheme.shapes.r16)
            .width(150.dp)
            .clickable {
                _isSelected.value = !_isSelected.value
                onSelectClick(_isSelected.value)
            }
            .background(backgroundColor)
            .border(1.dp, RavanTheme.colors.border.onPrimary, RavanTheme.shapes.r16)
            .padding(vertical = 8.dp, horizontal = 16.dp),
        horizontalArrangement = Arrangement.Start,
        verticalAlignment = Alignment.CenterVertically
    ) {
        Icon(
            painter = painterResource(id = getIconResource(_isSelected.value)),
            contentDescription = "selection_help_icon",
            tint = textColor,
            modifier = Modifier.size(18.dp)
        )
        Spacer(modifier = Modifier.weight(1f))
        Text(text = data.title, color = textColor, style = RavanTheme.typography.body1)
        Spacer(modifier = Modifier.weight(1f))

    }

}

@Composable
private fun getIconResource(isSelected: Boolean): Int {
    return if (isSelected) {
        R.drawable.ic_check
    } else {
        R.drawable.ic_close
    }
}

@Composable
private fun getBackgroundColor(isSelected: Boolean): Color {
    return if (isSelected) {
        RavanTheme.colors.background.secondary
    } else {
        RavanTheme.colors.background.primary
    }
}

@Composable
private fun getTextColor(isSelected: Boolean): Color {
    return if (isSelected) {
        RavanTheme.colors.text.onSecondary
    } else {
        RavanTheme.colors.text.onPrimary
    }
}

@Preview
@Composable
private fun AutoReserveDaySelectionItemPreview() {
    RavanTheme {
        AutoReserveDaySelectionItem(
            data = AutoReserveDaySelectionItemUIModel(
                title = stringResource(id = R.string.persian_day_of_week_monday)
            ),
            onSelectClick = {}
        )
    }
}