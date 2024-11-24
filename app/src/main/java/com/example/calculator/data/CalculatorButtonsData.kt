package com.example.calculator.data

import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.sp
import com.example.calculator.R
import com.example.calculator.ui.MainViewModel

class CalculatorButtonsData(mainViewModel: MainViewModel) {
    private val colorForActions = Color(0xFF94FFCD)

    val listOfButtons = listOf(
        CalculatorButtonData(
            onClick = { mainViewModel.onClearClick() },
            symbol = 'C',
            fontSize = 27.sp,
            contentColor = Color(0xFF8F0002),
        ),
        CalculatorButtonData(
            onClick = { mainViewModel.onActionClick(action = '/') },
            symbol = '÷',
            fontSize = 35.sp,
            contentColor = colorForActions,
        ),
        CalculatorButtonData(
            onClick = { mainViewModel.onActionClick(action = '*') },
            symbol = '×',
            fontSize = 35.sp,
            contentColor =colorForActions,
        ),
        CalculatorButtonWithImageData(
            onClick = { mainViewModel.onDeleteLastCharClick() },
            image = R.drawable.delete,
            description = R.string.delete,
        ),
        CalculatorButtonData(
            onClick = { mainViewModel.onNumeralClick(numeral = '7') },
            symbol = '7',
        ),
        CalculatorButtonData(
            onClick = { mainViewModel.onNumeralClick(numeral = '8') },
            symbol = '8',
        ),
        CalculatorButtonData(
            onClick = { mainViewModel.onNumeralClick(numeral = '9') },
            symbol = '9',
        ),
        CalculatorButtonData(
            onClick = { mainViewModel.onActionClick(action = '-') },
            symbol = '-',
            fontSize = 40.sp,
            contentColor = colorForActions,
        ),
        CalculatorButtonData(
            onClick = { mainViewModel.onNumeralClick(numeral = '4') },
            symbol = '4',
        ),
        CalculatorButtonData(
            onClick = { mainViewModel.onNumeralClick(numeral = '5') },
            symbol = '5',
        ),
        CalculatorButtonData(
            onClick = { mainViewModel.onNumeralClick(numeral = '6') },
            symbol = '6',
        ),
        CalculatorButtonData(
            onClick = { mainViewModel.onActionClick(action = '+') },
            symbol = '+',
            fontSize = 25.sp,
            contentColor = colorForActions,
        ),
        CalculatorButtonData(
            onClick = { mainViewModel.onNumeralClick(numeral = '1') },
            symbol = '1',
        ),
        CalculatorButtonData(
            onClick = { mainViewModel.onNumeralClick(numeral = '2') },
            symbol = '2',
        ),
        CalculatorButtonData(
            onClick = { mainViewModel.onNumeralClick(numeral = '3') },
            symbol = '3',
        ),
        CalculatorButtonData(
            onClick = { mainViewModel.onEqualsClick() },
            symbol = '=',
            fontSize = 26.sp,
            contentColor = colorForActions,
        ),
        CalculatorButtonData(
            onClick = { mainViewModel.onActionClick(action = '.') },
            symbol = ',',
            fontSize = 30.sp,
            contentColor = colorForActions,
        ),
        CalculatorButtonData(
            onClick = { mainViewModel.onNumeralClick(numeral = '0') },
            symbol = '0',
        ),
        CalculatorButtonData(
            onClick = { mainViewModel.onBracesClick(brace = '(') },
            symbol = '(',
            fontSize = 25.sp,
            contentColor = colorForActions,
        ),
        CalculatorButtonData(
            onClick = { mainViewModel.onBracesClick(brace = ')') },
            symbol = ')',
            fontSize = 25.sp,
            contentColor = colorForActions,
        )
    )
}