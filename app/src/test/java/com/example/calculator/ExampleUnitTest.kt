package com.example.calculator

import com.example.calculator.ui.MainViewModel
import org.junit.Test

import org.junit.Assert.*

/**
 * Example local unit test, which will execute on the development machine (host).
 *
 * See [testing documentation](http://d.android.com/tools/testing).
 */
class ExampleUnitTest {
    private val viewModel = MainViewModel()

    @Test
    fun onNumeralClick_addNumeral() {
        viewModel.onNumeralClick('8')
        val currentExpression = viewModel.expression
        assertEquals("8", currentExpression)
    }
}