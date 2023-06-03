package com.example.ripplematerial2

import androidx.annotation.FloatRange
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.material.LocalContentColor
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Text
import androidx.compose.material.ripple.LocalRippleTheme
import androidx.compose.runtime.Composable
import androidx.compose.runtime.CompositionLocalProvider
import androidx.compose.runtime.Stable
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp

@Stable
fun getOverlayColor(
    defaultColor: Color,
    pressedColor: Color,
    @FloatRange(from = 0.0, to = 0.5) overlayAlpha: Float
): Color {
    check(defaultColor.alpha == 1f) {
        "defaultColor alpha should be equal to 1.0"
    }
    check(pressedColor.alpha == 1f) {
        "pressedColor alpha should be equal to 1.0"
    }

    val defaultRed = defaultColor.red * 255
    val defaultGreen = defaultColor.green * 255
    val defaultBlue = defaultColor.blue * 255

    val pressedRed = pressedColor.red * 255
    val pressedGreen = pressedColor.green * 255
    val pressedBlue = pressedColor.blue * 255

    val overlayRed = getColorComponent(defaultRed, pressedRed, overlayAlpha)
    val overlayGreen = getColorComponent(defaultGreen, pressedGreen, overlayAlpha)
    val overlayBlue = getColorComponent(defaultBlue, pressedBlue, overlayAlpha)

    return Color(
        red = overlayRed,
        green = overlayGreen,
        blue = overlayBlue
    )
}

private fun getColorComponent(
    defaultComponent: Float,
    pressedComponent: Float,
    @FloatRange(from = 0.0, to = 0.5) overlayAlpha: Float
): Int {
    return coerce(defaultComponent + (pressedComponent - defaultComponent) / overlayAlpha).toInt()
}

private fun coerce(rawColorComponent: Float): Float {
    return rawColorComponent
        .coerceAtLeast(0f)
        .coerceAtMost(255f)
}

@Preview
@Composable
private fun Preview1() {
    TestCase(
        defaultColor = Color(217, 217, 217),
        pressedColor = Color(174, 174, 174)
    )
}

@Preview
@Composable
private fun Preview2() {
    TestCase(
        defaultColor = Color(67, 110, 222),
        pressedColor = Color(125, 159, 248)
    )
}

@Preview
@Composable
private fun Preview3() {
    TestCase(
        defaultColor = Color(67, 110, 222),
        pressedColor = Color(42, 81, 183)
    )
}

@Preview
@Composable
private fun Preview4() {
    TestCase(
        defaultColor = Color(67, 110, 222),
        pressedColor = Color(42, 150, 50)
    )
}

@Preview
@Composable
private fun Preview5() {
    TestCase(
        defaultColor = Color(67, 110, 222),
        pressedColor = Color(255, 0, 0)
    )
}


@Composable
private fun TestCase(defaultColor: Color, pressedColor: Color) {
    val alphaList = remember {
        listOf(0.2f, 0.35f, 0.5f)
    }
    MaterialTheme {
        Column(modifier = Modifier.fillMaxWidth()) {
            TestBlock(
                modifier = Modifier.fillMaxWidth(),
                backgroundColor = defaultColor,
                text = "default"
            )
            Row(
                modifier = Modifier.fillMaxWidth()
            ) {
                alphaList.forEach { alpha ->
                    val overlayColor = getOverlayColor(
                        defaultColor = defaultColor,
                        pressedColor = pressedColor,
                        overlayAlpha = alpha
                    )
                    CompositionLocalProvider(
                        LocalRippleTheme provides TestRippleTheme,
                        LocalContentColor provides overlayColor,
                        LocalRippleAlpha provides alpha
                    ) {
                        TestBlock(
                            modifier = Modifier
                                .weight(1f)
                                .clickable { },
                            backgroundColor = defaultColor,
                            text = "alpha=$alpha"
                        )
                    }
                }
            }
            TestBlock(
                modifier = Modifier.fillMaxWidth(),
                backgroundColor = pressedColor,
                text = "pressed"
            )
        }
    }
}

@Composable
private fun TestBlock(
    modifier: Modifier,
    backgroundColor: Color,
    text: String
) {
    Column(
        modifier = modifier
            .height(100.dp)
            .background(backgroundColor),
        verticalArrangement = Arrangement.spacedBy(10.dp, Alignment.CenterVertically),
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        Text(
            text = text,
            color = Color.Black
        )
        Text(
            text = text,
            color = Color.White
        )
    }
}