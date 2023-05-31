package com.example.ripplematerial2

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.material.LocalContentColor
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Text
import androidx.compose.material.ripple.LocalRippleTheme
import androidx.compose.runtime.Composable
import androidx.compose.runtime.CompositionLocalProvider
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.compositeOver
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp

private val testAlphaList = listOf(0f, 0.25f, 0.5f, 0.75f, 1f)
private val testColorList = listOf(Color.Black, Color.Red)
private val backgroundColor = Color.White

@Composable
fun Content() {
    MaterialTheme {
        CompositionLocalProvider(
            LocalRippleTheme provides TestRippleTheme,
            LocalRippleAlpha provides 0.5f,
            LocalContentColor provides Color.Black
        ) {
            Row {
                testColorList.forEach { color ->
                    Column(
                        modifier = Modifier
                            .background(
                                colorWithOverlay(
                                    contentColor = color,
                                    rippleAlpha = 0.5f
                                )
                            )
                            .weight(1f)
                            .padding(16.dp),
                        verticalArrangement = Arrangement.spacedBy(16.dp),
                        horizontalAlignment = Alignment.CenterHorizontally
                    ) {
                        testAlphaList.forEach { alpha ->
                            TestCase(
                                rippleAlpha = alpha,
                                contentColor = color
                            )
                        }
                    }
                }
            }
        }
    }
}

@Composable
private fun TestCase(contentColor: Color, rippleAlpha: Float) {
    CompositionLocalProvider(
        LocalContentColor provides contentColor,
        LocalRippleAlpha provides rippleAlpha
    ) {
        val expectedFinalBackgroundColor = colorWithOverlay(
            contentColor = contentColor,
            rippleAlpha = rippleAlpha
        )
        Row {
            TestCaseText(
                modifier = Modifier
                    .background(backgroundColor),
                text = "start"
            )
            TestCaseText(
                modifier = Modifier
                    .background(backgroundColor)
                    .clickable { },
                text = "alpha=$rippleAlpha"
            )
            TestCaseText(
                modifier = Modifier
                    .background(expectedFinalBackgroundColor),
                text = "final"
            )
        }
    }
}

private fun colorWithOverlay(
    contentColor: Color,
    rippleAlpha: Float
) = contentColor
    .copy(alpha = rippleAlpha)
    .compositeOver(backgroundColor)

@Composable
private fun RowScope.TestCaseText(
    modifier: Modifier = Modifier,
    text: String
) {
    Text(
        modifier = modifier
            .weight(1f)
            .padding(5.dp),
        textAlign = TextAlign.Center,
        text = text
    )
}

@Preview
@Composable
fun ContentPreview() {
    Content()
}