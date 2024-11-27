package com.example.calculator.utils

import android.content.Context
import android.content.Context.VIBRATOR_SERVICE
import android.os.Build
import android.os.VibrationEffect
import android.os.Vibrator
import android.util.Log
import androidx.compose.ui.Modifier
import androidx.compose.ui.geometry.CornerRadius
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.Paint
import androidx.compose.ui.graphics.PaintingStyle
import androidx.compose.ui.graphics.drawscope.ContentDrawScope
import androidx.compose.ui.graphics.drawscope.Stroke
import androidx.compose.ui.graphics.drawscope.drawIntoCanvas
import androidx.compose.ui.graphics.toArgb
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp
import java.math.BigDecimal

fun (() -> Unit).vibration(context: Context) {
    this.invoke()
    val vibrator = context.getSystemService(VIBRATOR_SERVICE) as Vibrator
    val vibrationEffect: VibrationEffect = if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
        VibrationEffect.createOneShot(1, VibrationEffect.DEFAULT_AMPLITUDE)
    } else {
        Log.e("TAG", "Cannot vibrate device...")
        TODO("VERSION.SDK_INT < O")
    }
    vibrator.cancel()
    vibrator.vibrate(vibrationEffect)
}

fun String.removeExponentialNotation(minPow: Int): String {
    val exponent = Regex("(?<=E)\\d+").find(this)?.value?.toInt() ?: return this
    if (exponent < minPow) {
        return BigDecimal(this).toPlainString()
    }
    return this
}

fun Double?.redundantDoubleFormat(): String {
    if (this == null) {
        return ""
    }
    return this.toString()
        .replace(Regex("[.]0+$"), "")
        .removeExponentialNotation(minPow = 15)
}

fun String.toNumberFormat(separator: String = " "): String {
    if (this.isEmpty()) {
        return this
    }
    val isNegativeNumber = if (this.first() == '-') '-' else ""
    var changedStringInt = Regex("[^-][^.]*").find(this)?.value ?: return this
    val changedStringDouble = Regex("[.]\\d*(E\\d+)?").find(this)?.value?.replace('.', ',') ?: ""
    val length = changedStringInt.length
    val regex = Regex("\\d{3}")
    val replacement =
        regex.findAll(changedStringInt.substring(length % 3, length))
            .map { it.value }
            .toList()
            .joinToString(
                prefix = if (length % 3 != 0 && length > 2) separator else "",
                separator = if (this.isNotEmpty()) separator else ""
            )
    changedStringInt = changedStringInt.take(length % 3)
    return "$isNegativeNumber${changedStringInt}${replacement}$changedStringDouble"
}

fun String.allNumbersToNumberFormat(separator: String = " "): String {
    var formatedExpression = this
    Regex("\\d+([.]\\d*)?(E\\d+)?").findAll(this).map { it.value }.forEach {
        formatedExpression =
            formatedExpression.replaceFirst(it, it.toNumberFormat(separator = separator))
    }
    return formatedExpression
        .replace('/', '÷')
        .replace('*', '×')
}

fun ContentDrawScope.drawNeonStroke(radius: Dp, color: Color, width: Float = 25f) {
    this.drawIntoCanvas {
        val paint = Paint().apply {
            style = PaintingStyle.Stroke
            strokeWidth = width
        }

        val frameworkPaint = paint.asFrameworkPaint()
        this.drawIntoCanvas {
            frameworkPaint.color = color.copy(alpha = 0f).toArgb()
            frameworkPaint.setShadowLayer(radius.toPx(), 0f, 0f, color.copy(alpha = 0.65f).toArgb())
            it.drawRoundRect(
                left = 0f,
                right = size.width,
                bottom = size.height,
                top = 0f,
                radiusX = radius.toPx(),
                radiusY = radius.toPx(),
                paint = paint
            )
            drawRoundRect(
                color = color,
                size = size,
                cornerRadius = CornerRadius(radius.toPx(), radius.toPx()),
                style = Stroke(width = 1.dp.toPx())
            )
        }
    }
}

inline fun Modifier.thenIf(condition: Boolean, other: Modifier.() -> Modifier): Modifier {
    return if (condition) other() else this
}

fun CharSequence.endsWith(regex: Regex): Boolean =
    this.isNotEmpty() && regex.matches(this)





















