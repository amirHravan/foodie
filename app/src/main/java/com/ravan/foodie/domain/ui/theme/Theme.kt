package com.ravan.foodie.domain.ui.theme

import androidx.compose.foundation.isSystemInDarkTheme
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.Colors
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Typography
import androidx.compose.runtime.Composable
import androidx.compose.runtime.CompositionLocalProvider
import androidx.compose.runtime.ReadOnlyComposable
import androidx.compose.runtime.Stable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.runtime.staticCompositionLocalOf
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.Shape
import androidx.compose.ui.platform.LocalLayoutDirection
import androidx.compose.ui.unit.LayoutDirection
import androidx.compose.ui.unit.dp
import com.google.accompanist.systemuicontroller.rememberSystemUiController

private val DarkColorsDefault = RavanColors(
    background = SurfaceColors(
        primary = RavanBlue,
        secondary = RavanWhite,
        tertiary = RavanBlack,
        success = FunctionalGreen.copy(alpha = 0.2f),
        fail = FunctionalRedDark.copy(alpha = 0.2f),
        disable = RavanBlack.copy(alpha = 0.2f),
    ),
    border = ContentColors(
        onPrimary = RavanWhite,
        onSecondary = RavanBlue,
        onTertiary = RavanWhite,
        onSuccess = FunctionalGreen,
        onFail = FunctionalRedDark,
        onDisable = RavanWhite,
    ),
    text = ContentColors(
        onPrimary = RavanWhite,
        onSecondary = RavanBlue,
        onTertiary = RavanWhite,
        onSuccess = RavanWhite,
        onFail = RavanWhite,
        onDisable = RavanWhite,
    ),
    link = ContentColors(
        onPrimary = RavanWhite,
        onSecondary = RavanBlue,
        onTertiary = RavanWhite,
        onSuccess = RavanWhite,
        onFail = RavanWhite,
        onDisable = RavanWhite,
    ),
    icon = ContentColors(
        onPrimary = RavanWhite,
        onSecondary = RavanBlue,
        onTertiary = RavanWhite,
        onSuccess = FunctionalGreen,
        onFail = FunctionalRed,
        onDisable = RavanWhite,
    ),
    notificationBadge = FunctionalRedDark,
    isDark = true,
)

private val ShapesDefault: RavanShapes = RavanShapes(
    pill = RoundedCornerShape(50),
    r16 = RoundedCornerShape(16.dp),
    r12 = RoundedCornerShape(12.dp),
    r8 = RoundedCornerShape(8.dp),
    r4 = RoundedCornerShape(4.dp),
    r = RoundedCornerShape(0.dp),
)

@Composable
fun RavanTheme(
    darkTheme: Boolean = isSystemInDarkTheme(),
    content: @Composable () -> Unit
) {
    val colors = DarkColorsDefault
    val systemUiController = rememberSystemUiController()
    systemUiController.setSystemBarsColor(
        color = colors.background.primary,
        darkIcons = true
    )

    ProvideRavanTheme(colors, ShapesDefault) {
        CompositionLocalProvider(LocalLayoutDirection provides LayoutDirection.Rtl) {
            MaterialTheme(
                colors = debugColors(darkTheme),
                typography = RavanTypography,
                shapes = Shapes,
                content = content
            )
        }
    }
}

object RavanTheme {
    val colors: RavanColors
        @Composable
        @ReadOnlyComposable
        get() = LocalRavanColors.current

    val shapes: RavanShapes
        @Composable
        @ReadOnlyComposable
        get() = LocalRavanShapes.current

    val typography: Typography = RavanTypography
}

data class ContentColors(
    val onPrimary: Color,
    val onSecondary: Color,
    val onTertiary: Color,
    val onSuccess: Color,
    val onFail: Color,
    val onDisable: Color,
)

data class SurfaceColors(
    val primary: Color,
    val secondary: Color,
    val tertiary: Color,
    val success: Color,
    val fail: Color,
    val disable: Color,
)


private val LocalRavanColors = staticCompositionLocalOf<RavanColors> {
    error("No RavanColor provided")
}

@Stable
class RavanColors(
    border: ContentColors,
    text: ContentColors,
    link: ContentColors,
    icon: ContentColors,
    background: SurfaceColors,
    notificationBadge: Color,
    isDark: Boolean
) {
    var text by mutableStateOf(text)
        private set
    var border by mutableStateOf(border)
        private set
    var link by mutableStateOf(link)
        private set
    var icon by mutableStateOf(icon)
        private set
    var background by mutableStateOf(background)
        private set
    var notificationBadge by mutableStateOf(notificationBadge)
        private set
    var isDark by mutableStateOf(isDark)
        private set

    fun update(other: RavanColors) {
        text = other.text
        border = other.border
        link = other.link
        icon = other.icon
        background = other.background
        notificationBadge = other.notificationBadge
        isDark = other.isDark
    }

    fun copy(): RavanColors = RavanColors(
        text = text,
        border = border,
        link = link,
        icon = icon,
        background = background,
        notificationBadge = notificationBadge,
        isDark = isDark,
    )
}

private val LocalRavanShapes = staticCompositionLocalOf<RavanShapes> {
    error("No RavanShape provided")
}

@Stable
class RavanShapes(
    pill: Shape,
    r16: Shape,
    r12: Shape,
    r8: Shape,
    r4: Shape,
    r: Shape
) {
    var pill by mutableStateOf(pill)
        private set
    var r16 by mutableStateOf(r16)
        private set
    var r12 by mutableStateOf(r12)
        private set
    var r8 by mutableStateOf(r8)
        private set
    var r4 by mutableStateOf(r4)
        private set
    var r by mutableStateOf(r)
        private set

    fun update(other: RavanShapes) {
        pill = other.pill
        r16 = other.r16
        r12 = other.r12
        r8 = other.r8
        r4 = other.r4
        r = other.r
    }

    fun copy(): RavanShapes = RavanShapes(
        pill = pill,
        r16 = r16,
        r12 = r12,
        r8 = r8,
        r4 = r4,
        r = r
    )
}

@Composable
fun ProvideRavanTheme(
    colors: RavanColors,
    shapes: RavanShapes,
    content: @Composable () -> Unit
) {
    val colorPalette = remember {
        colors.copy()
    }
    colorPalette.update(colors)

    val shapePalette = remember {
        shapes.copy()
    }
    shapePalette.update(shapes)

    CompositionLocalProvider(LocalRavanColors provides colorPalette) {
        CompositionLocalProvider(LocalRavanShapes provides shapePalette, content = content)
    }
}


fun debugColors(
    darkTheme: Boolean,
    debugColor: Color = Color.Magenta
) = Colors(
    primary = debugColor,
    primaryVariant = debugColor,
    secondary = debugColor,
    secondaryVariant = debugColor,
    background = debugColor,
    surface = debugColor,
    error = debugColor,
    onPrimary = debugColor,
    onSecondary = debugColor,
    onBackground = debugColor,
    onSurface = debugColor,
    onError = debugColor,
    isLight = !darkTheme
)