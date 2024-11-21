package com.example.calculator.data

import androidx.annotation.DrawableRes
import androidx.annotation.StringRes
import androidx.compose.ui.graphics.Color

data class CalculatorButtonWithImageData(
    val onClick: () -> Unit,
    @DrawableRes val image: Int,
    @StringRes val description: Int,
    val contentColor: Color? = null,
    val containerColor: Color? = null
)
