package com.example.ripplematerial2

import androidx.compose.material.LocalContentColor
import androidx.compose.material.ripple.RippleAlpha
import androidx.compose.material.ripple.RippleTheme
import androidx.compose.runtime.Composable
import androidx.compose.runtime.Immutable
import androidx.compose.runtime.compositionLocalOf

val LocalRippleAlpha = compositionLocalOf { 0f }

@Immutable
object TestRippleTheme : RippleTheme {

    @Composable
    override fun defaultColor() = LocalContentColor.current

    @Composable
    override fun rippleAlpha(): RippleAlpha {
        val alpha = LocalRippleAlpha.current
        return RippleAlpha(0f, 0f, 0f, alpha)
    }
}