package com.example.calculator.ui

import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.ViewModel
import com.example.calculator.data.Calculator
import com.example.calculator.utils.endsWith
import com.example.calculator.utils.redundantDoubleFormat
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update

class MainViewModel : ViewModel() {
    var expression by mutableStateOf("")
        private set

    private val _calculator = MutableStateFlow(Calculator(string = expression))
    val calculator: StateFlow<Calculator>
        get() = _calculator.asStateFlow()

    private fun onClick() {
        _calculator.update {
            Calculator(string = expression)
        }
    }

    private fun requireIsNotResult() {
        if (_calculator.value.isResult) {
            _calculator.value.isResult = false
            expression = ""
        }
    }

    fun onClearClick() {
        requireIsNotResult()
        expression = ""
        onClick()
    }

    fun onDeleteLastCharClick() {
        expression = expression.dropLast(1)
        onClick()
    }

    fun onEqualsClick() {
        expression = if (_calculator.value.result != null) {
            _calculator.value.result.redundantDoubleFormat()
        } else {
            "Ошибка"
        }
        onClick()
        _calculator.value.isResult = true
    }

    fun onNumeralClick(numeral: Char) {
        requireIsNotResult()
        if (
            Regex(".*[-*/+]0$").matches(expression)
        ) {
            expression = expression.dropLast(1) + numeral
            onClick()
            return
        } else if (!expression.endsWith(')')) {
            expression += numeral // 0123456789
            onClick()
        }
    }

    fun onActionClick(action: Char) {
        if (expression == "Ошибка" || expression == "Infinity") {
            requireIsNotResult()
        }

        if (action == '.') {
            if (Regex("\\d+([.]\\d*)(E\\d+)?$").find(expression)?.value == null) {
                expression += '.'
                onClick()
                return
            }
            return
        }

        if (expression == "-" || expression.endsWith(regex = Regex(".*[(]-$"))) {
            if (action == '+') {
                expression = expression.dropLast(1)
                onClick()
                return
            }
            return
        }
        if (expression != "-" && expression.endsWith(regex = Regex(".*[-*+/.]$"))) {
            expression = expression.dropLast(1) + action
            onClick()
            return
        }
        if ((expression.isNotEmpty() && !expression.endsWith('(')) || (action == '-')) {
            expression += action // /*-+.
            onClick()
            return
        }
    }

    fun onBracesClick(brace: Char) {
        requireIsNotResult()
        val isLastSymbolIsActionOrExpressionIsEmpty =
            expression.endsWith(Regex(".*[-*+/(]$")) || expression.isEmpty()
        if (isLastSymbolIsActionOrExpressionIsEmpty) {
            if (brace == ')' && expression.isNotEmpty() && !expression.endsWith(regex = Regex(".*([(]|-)$"))) {
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
            } else {
                expression += "*$brace"
                onClick()
            }
        }
    }
}