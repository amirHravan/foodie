package com.ravan.foodie.account.ui.component

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.gestures.detectDragGesturesAfterLongPress
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.material3.Icon
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.key
import androidx.compose.runtime.mutableFloatStateOf
import androidx.compose.runtime.mutableIntStateOf
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberUpdatedState
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.alpha
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.graphicsLayer
import androidx.compose.ui.hapticfeedback.HapticFeedbackType
import androidx.compose.ui.input.pointer.pointerInput
import androidx.compose.ui.layout.onSizeChanged
import androidx.compose.ui.platform.LocalDensity
import androidx.compose.ui.platform.LocalHapticFeedback
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import androidx.compose.ui.zIndex
import com.ravan.foodie.R
import com.ravan.foodie.account.ui.model.AccountRowUIModel
import com.ravan.foodie.domain.ui.theme.RavanTheme
import kotlinx.collections.immutable.ImmutableList
import kotlin.math.roundToInt

private val ROW_SPACING = 8.dp

/**
 * Saved accounts plus an "add account" row. With [onMove] set, rows can be reordered by
 * long-pressing and dragging them.
 */
@Composable
fun AccountList(
    accounts: ImmutableList<AccountRowUIModel>,
    canAddAccount: Boolean,
    contentColor: Color,
    rowBackground: Color,
    onAccountClick: (String) -> Unit,
    onLoginAgainClick: (String) -> Unit,
    onAddAccountClick: () -> Unit,
    modifier: Modifier = Modifier,
    enabled: Boolean = true,
    onRemoveClick: ((String) -> Unit)? = null,
    onMove: ((fromIndex: Int, toIndex: Int) -> Unit)? = null,
    addRowContentColor: Color = contentColor,
) {
    val haptic = LocalHapticFeedback.current
    val rowShape = RavanTheme.shapes.r8
    val spacingPx = with(LocalDensity.current) { ROW_SPACING.toPx() }
    // Read inside long-lived gesture handlers, which would otherwise keep the first values.
    val currentAccounts by rememberUpdatedState(accounts)
    val currentOnMove by rememberUpdatedState(onMove)

    var draggedUsername by remember { mutableStateOf<String?>(null) }
    var dragOffset by remember { mutableFloatStateOf(0f) }
    var rowHeight by remember { mutableIntStateOf(0) }

    Column(
        modifier = modifier.fillMaxWidth(),
        verticalArrangement = Arrangement.spacedBy(ROW_SPACING),
    ) {
        accounts.forEach { account ->
            key(account.username) {
                val isDragged = draggedUsername == account.username
                val rowModifier = Modifier
                    .fillMaxWidth()
                    .onSizeChanged { rowHeight = it.height }
                    .zIndex(if (isDragged) 1f else 0f)
                    .graphicsLayer {
                        translationY = if (isDragged) dragOffset else 0f
                        shadowElevation = if (isDragged) 8.dp.toPx() else 0f
                        shape = rowShape
                        clip = true
                    }
                    .background(rowBackground)
                    // The drag handler comes after (inside) clickable, so it sees the release
                    // first and consumes it: letting go after a long press never switches.
                    .clickable(enabled = enabled) { onAccountClick(account.username) }
                    .then(
                        if (onMove == null) {
                            Modifier
                        } else {
                            Modifier.pointerInput(account.username) {
                                detectDragGesturesAfterLongPress(
                                    onDragStart = {
                                        haptic.performHapticFeedback(HapticFeedbackType.LongPress)
                                        draggedUsername = account.username
                                        dragOffset = 0f
                                    },
                                    onDrag = { change, amount ->
                                        change.consume()
                                        dragOffset += amount.y
                                        val step = rowHeight + spacingPx
                                        val from = currentAccounts
                                            .indexOfFirst { it.username == account.username }
                                        if (step <= 0f || from == -1) return@detectDragGesturesAfterLongPress
                                        val to = (from + (dragOffset / step).roundToInt())
                                            .coerceIn(0, currentAccounts.lastIndex)
                                        if (to != from) {
                                            currentOnMove?.invoke(from, to)
                                            dragOffset -= (to - from) * step
                                        }
                                    },
                                    onDragEnd = {
                                        draggedUsername = null
                                        dragOffset = 0f
                                    },
                                    onDragCancel = {
                                        draggedUsername = null
                                        dragOffset = 0f
                                    },
                                )
                            }
                        }
                    )

                AccountRow(
                    data = account,
                    contentColor = contentColor,
                    onLoginAgainClick = { onLoginAgainClick(account.username) },
                    onRemoveClick = onRemoveClick?.let { { it(account.username) } },
                    modifier = rowModifier,
                )
            }
        }

        AddAccountRow(
            enabled = enabled && canAddAccount,
            contentColor = addRowContentColor,
            onClick = onAddAccountClick,
        )
        if (!canAddAccount) {
            Text(
                text = stringResource(R.string.accounts_limit_reached),
                style = RavanTheme.typography.body2,
                color = addRowContentColor.copy(alpha = 0.7f),
                modifier = Modifier.padding(horizontal = 12.dp),
            )
        } else if (onMove != null && accounts.size > 1) {
            Text(
                text = stringResource(R.string.accounts_reorder_hint),
                style = RavanTheme.typography.body2,
                color = addRowContentColor.copy(alpha = 0.7f),
                modifier = Modifier.padding(horizontal = 12.dp),
            )
        }
    }
}

@Composable
private fun AddAccountRow(
    enabled: Boolean,
    contentColor: Color,
    onClick: () -> Unit,
) {
    Row(
        modifier = Modifier
            .fillMaxWidth()
            .clip(RavanTheme.shapes.r8)
            .clickable(enabled = enabled, onClick = onClick)
            .alpha(if (enabled) 1f else 0.4f)
            .padding(horizontal = 12.dp, vertical = 12.dp),
        verticalAlignment = Alignment.CenterVertically,
        horizontalArrangement = Arrangement.spacedBy(12.dp),
    ) {
        Icon(
            painter = painterResource(R.drawable.ic_add),
            contentDescription = null,
            tint = contentColor,
            modifier = Modifier.size(24.dp),
        )
        Text(
            text = stringResource(R.string.accounts_add),
            style = RavanTheme.typography.h6,
            color = contentColor,
        )
    }
}
