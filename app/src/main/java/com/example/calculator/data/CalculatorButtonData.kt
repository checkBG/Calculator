package com.example.calculator.data

import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.TextUnit
import androidx.compose.ui.unit.sp

data class CalculatorButtonData(
    val onClick: () -> Unit,
    val symbol: Char,
    val fontSize: TextUnit = 20.sp,
    val contentColor: Color? = null,
    val containerColor: Color? = null
)
