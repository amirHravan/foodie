package com.ravan.foodie.domain.ui.theme

import androidx.compose.material.Typography
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.Font
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.sp
import com.ravan.foodie.R

private val Roya = FontFamily(
    Font(R.font.roya, FontWeight.Normal),
    Font(R.font.roya_bold, FontWeight.Bold)
)

private val Kalameh = FontFamily(
    Font(R.font.kalameh_thin, FontWeight.Thin),
    Font(R.font.kalameh_regular, FontWeight.Normal),
    Font(R.font.kalameh_bold, FontWeight.Bold),
    Font(R.font.kalameh_black, FontWeight.Black)
)


val RavanTypography = typographyFromDefaults(
    h1 = TextStyle(
        fontFamily = Kalameh,
        fontWeight = FontWeight.Black
    ),
    h2 = TextStyle(
        fontFamily = Kalameh,
        fontWeight = FontWeight.Black
    ),
    h3 = TextStyle(
        fontFamily = Kalameh,
        fontWeight = FontWeight.Bold
    ),
    h4 = TextStyle(
        fontFamily = Kalameh,
        fontWeight = FontWeight.Bold,
    ),
    h5 = TextStyle(
        fontFamily = Kalameh,
        fontWeight = FontWeight.Bold
    ),
    h6 = TextStyle(
        fontFamily = Kalameh,
        fontWeight = FontWeight.Bold,
    ),
    subtitle1 = TextStyle(
        fontFamily = Kalameh,
        fontWeight = FontWeight.Thin,
    ),
    subtitle2 = TextStyle(
        fontFamily = Kalameh,
        fontWeight = FontWeight.Thin
    ),
    body1 = TextStyle(
        fontFamily = Kalameh,
        fontWeight = FontWeight.Normal,
    ),
    body2 = TextStyle(
        fontFamily = Kalameh,
        fontWeight = FontWeight.Normal,
    ),
    button = TextStyle(
        fontFamily = Kalameh,
        fontWeight = FontWeight.Bold,
        fontSize = 24.sp,
    ),
    caption = TextStyle(
        fontFamily = Kalameh,
        fontWeight = FontWeight.Thin
    ),
    overline = TextStyle(
        fontFamily = Kalameh,
        fontWeight = FontWeight.Thin
    )
)

fun typographyFromDefaults(
    h1: TextStyle?,
    h2: TextStyle?,
    h3: TextStyle?,
    h4: TextStyle?,
    h5: TextStyle?,
    h6: TextStyle?,
    subtitle1: TextStyle?,
    subtitle2: TextStyle?,
    body1: TextStyle?,
    body2: TextStyle?,
    button: TextStyle?,
    caption: TextStyle?,
    overline: TextStyle?
): Typography {
    val defaults = Typography()
    return Typography(
        h1 = defaults.h1.merge(h1),
        h2 = defaults.h2.merge(h2),
        h3 = defaults.h3.merge(h3),
        h4 = defaults.h4.merge(h4),
        h5 = defaults.h5.merge(h5),
        h6 = defaults.h6.merge(h6),
        subtitle1 = defaults.subtitle1.merge(subtitle1),
        subtitle2 = defaults.subtitle2.merge(subtitle2),
        body1 = defaults.body1.merge(body1),
        body2 = defaults.body2.merge(body2),
        button = defaults.button.merge(button),
        caption = defaults.caption.merge(caption),
        overline = defaults.overline.merge(overline)
    )
}