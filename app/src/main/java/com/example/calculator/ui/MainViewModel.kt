package com.example.calculator.ui

import android.util.Log
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.ViewModel
import com.example.calculator.data.Calculator
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update

class MainViewModel : ViewModel() {
    var expression by mutableStateOf("0")
        private set

    private val _calculator = MutableStateFlow(Calculator.calculator(expression))
    val calculator: Flow<Double?>
        get() = _calculator.asStateFlow()

    private fun onClick() {
        _calculator.update {
            Calculator.calculator(expression)
        }
    }

    fun onClearClick() {
        expression = "0"
        onClick()
    }

    fun onDeleteLastCharClick() {
        expression = if (expression.length > 1) expression.dropLast(1) else "0"
        onClick()
    }

    fun onEqualsClick() {
        expression = if (_calculator.value != null) {
            _calculator.value.toString().replace(Regex("[.]0+\\D*$"), "")
        } else {
            "Error"
        }
        onClick()
    }

    fun onNumeralClick(numeral: Char) {
        if (
            Regex(".*[-*/+]0$").matches(expression) ||
            expression == "0"
        ) {
            expression = expression.dropLast(1) + numeral
            onClick()
            return
        }
        expression += numeral // 0123456789
        onClick()
    }

    fun onActionClick(action: Char) {
//        Log.d("expressionF", expression)
        if (expression == "Error") {
            expression = "0"
        }

        if (action == '.') {
            if (expression.isNotEmpty() && expression.last() in "0123456789") {
                if (expression.takeLastWhile { it !in "*-+/(" }.count { it == '.' } == 0) {
                    expression += '.'
                    onClick()
                    return
                }
                onClick()
                return
            } else {
                onClick()
                return
            }
        }
        if (expression == "0" && action == '-') {
            expression = "-"
            onClick()
            return
        }

        if ((expression.length == 1 && expression == "-" && action == '+')) {
            expression = "0"
            onClick()
            return
        }
        if (expression.isNotEmpty() && expression != "-" && expression.last() in "*-+/") {
            expression = expression.dropLast(1) + action
            onClick()
            return
        }
        if ((expression.isNotEmpty() && expression != "-") || (action == '-' && expression != "-")) {
            expression += action // /*-+.
            onClick()
            return
        }
    }

    fun onBracesClick(brace: Char) {
        val isLastSymbolOfExpressionIsAction = expression.last() in "*-+/"
        if (isLastSymbolOfExpressionIsAction) {
            if (brace == ')') {
                expression = expression.dropLast(1) + brace
                onClick()
                return
            } else if (brace == '(') {
                expression += brace
                onClick()
                return
            }
        } else {
            if (brace == ')') {
                expression += brace
                onClick()
                return
            }

        }

        if (expression == "0" && brace == '(') {
            expression = brace.toString()
            onClick()
            return
        }

        expression += brace
        onClick()
    }
}