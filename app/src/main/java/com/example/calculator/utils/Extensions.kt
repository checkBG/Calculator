package com.example.calculator.utils

import android.content.Context
import android.os.Build
import android.os.VibrationEffect
import android.os.Vibrator
import android.util.Log
import java.text.DecimalFormat

fun (() -> Unit).vibration(context: Context) {
    this.invoke()
    val vibrator = context.getSystemService(Context.VIBRATOR_SERVICE) as Vibrator
    val vibrationEffect1: VibrationEffect =
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            VibrationEffect.createOneShot(1, VibrationEffect.DEFAULT_AMPLITUDE)
        } else {
            Log.e("TAG", "Cannot vibrate device...")
            TODO("VERSION.SDK_INT < O")
        }
    vibrator.cancel()
    vibrator.vibrate(vibrationEffect1)
}

fun String.toNumberFormat(): String {
    return this
        .replace('/', '÷')
        .replace('*', '×')
        .replace(Regex("[.]0+\\D*$"), "")
}

fun Double?.toNumberFormat(): String {
    if (this == null) {
        return ""
    }
    return this.toString()
        .replace('/', '÷')
        .replace('*', '×')
        .replace(Regex("[.]0+\\D*$"), "")
}
