package com.example.calculator.ui

import android.icu.text.DecimalFormat
import android.icu.text.NumberFormat
import android.util.Log
import androidx.compose.animation.AnimatedVisibility
import androidx.compose.foundation.ScrollState
import androidx.compose.foundation.horizontalScroll
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.ExperimentalLayoutApi
import androidx.compose.foundation.layout.FlowRow
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.HorizontalDivider
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.calculator.data.CalculatorButtonData
import com.example.calculator.data.CalculatorButtonWithImageData
import com.example.calculator.data.CalculatorButtonsData
import com.example.calculator.ui.theme.CalculatorTheme
import java.util.Locale


@Composable
fun MainScreen(mainViewModel: MainViewModel, modifier: Modifier = Modifier) {
    val result by mainViewModel.calculator.collectAsState(initial = null)
    Log.d("result", result.toString())
    Log.d("expression", mainViewModel.expression)


    Surface(
        modifier = modifier
            .fillMaxSize(),
    ) {
        Column(
            verticalArrangement = Arrangement.Bottom,
            horizontalAlignment = Alignment.End
        ) {
            TextsCurrentAndResult(mainViewModel = mainViewModel, result = result)

            Spacer(modifier = Modifier.height(45.dp))
            HorizontalDividers()
            Spacer(modifier = Modifier.height(7.dp))

            FlowRowOfButtons(mainViewModel = mainViewModel)
        }
    }
}

@Composable
fun TextsCurrentAndResult(mainViewModel: MainViewModel, result: Double?) {
    Column {
        val numberFormat = NumberFormat.getInstance(Locale.getDefault()) as DecimalFormat
        numberFormat.minimumFractionDigits = 0
        numberFormat.maximumFractionDigits = 10
        Text(
            text = if (
                Regex("^-?\\d+([.]\\d+)?$").matches(mainViewModel.expression) &&
                ((Regex("-?[.]\\d+$").find(mainViewModel.expression)?.value?.length
                    ?: 0) < 10)
            ) {
                numberFormat.format(mainViewModel.expression.toDouble())
            } else {
                mainViewModel.expression
            }
                .replace('/', '÷')
                .replace('*', '×'),
            textAlign = TextAlign.Right,
            modifier = Modifier
                .horizontalScroll(ScrollState(Int.MAX_VALUE))
                .align(alignment = Alignment.End),
            fontSize = 50.sp,
            overflow = TextOverflow.Visible,
            softWrap = false
        )
        AnimatedVisibility(
            visible = result != null,
            modifier = Modifier.align(alignment = Alignment.End)
        ) {
            Text(
                text = if (result != null) {
                    numberFormat.format(result)
                        .replace('/', '÷')
                        .replace('*', '×')
                } else {
                    ""
                },
                textAlign = TextAlign.Right,
                color = Color.LightGray,
                modifier = Modifier
                    .horizontalScroll(ScrollState(Int.MAX_VALUE))
                    .align(alignment = Alignment.End),
                fontSize = 35.sp,
                overflow = TextOverflow.Visible,
                softWrap = false
            )
        }
    }
}

@OptIn(ExperimentalLayoutApi::class)
@Composable
fun FlowRowOfButtons(mainViewModel: MainViewModel) {
    FlowRow(
        horizontalArrangement = Arrangement.SpaceEvenly,
        verticalArrangement = Arrangement.Bottom,
        maxItemsInEachRow = 4,
        modifier = Modifier
            .fillMaxWidth()
            .padding(bottom = 25.dp),
    ) {
        val listOfButtons =
            CalculatorButtonsData(mainViewModel = mainViewModel).listOfButtons
        listOfButtons.forEach { currentButton ->
            if (currentButton is CalculatorButtonData) {
                Column {
                    CalculatorButton(
                        onClick = currentButton.onClick,
                        symbol = currentButton.symbol,
                        fontSize = currentButton.fontSize,
                        contentColor = currentButton.contentColor,
                        containerColor = currentButton.containerColor
                    )
                    Spacer(modifier = Modifier.height(4.dp))
                }
            } else if (currentButton is CalculatorButtonWithImageData) {
                Column {
                    CalculatorButtonWithImage(
                        onClick = currentButton.onClick,
                        image = currentButton.image,
                        description = currentButton.description,
                        contentColor = currentButton.contentColor,
                        containerColor = currentButton.containerColor
                    )
                    Spacer(modifier = Modifier.height(4.dp))
                }
            }
        }
    }
}

@Composable
fun HorizontalDividers(modifier: Modifier = Modifier) {
    Column(modifier = modifier.fillMaxWidth()) {
        HorizontalDivider(
            thickness = 1.2.dp,
            modifier = Modifier
                .fillMaxWidth(0.7f)
                .align(alignment = Alignment.CenterHorizontally)
        )
        HorizontalDivider(
            thickness = 1.1.dp,
            modifier = Modifier
                .fillMaxWidth(0.75f)
                .align(alignment = Alignment.CenterHorizontally)
        )
        HorizontalDivider(
            thickness = 1.dp,
            modifier = Modifier
                .fillMaxWidth(0.8f)
                .align(alignment = Alignment.CenterHorizontally)
        )
        HorizontalDivider(
            thickness = 1.1.dp,
            modifier = Modifier
                .fillMaxWidth(0.75f)
                .align(alignment = Alignment.CenterHorizontally)
        )
        HorizontalDivider(
            thickness = 1.2.dp,
            modifier = Modifier
                .fillMaxWidth(0.7f)
                .align(alignment = Alignment.CenterHorizontally)
        )
    }
}

@Preview(showBackground = true)
@Composable
fun MainScreenPreview() {
    CalculatorTheme(darkTheme = false) {
        MainScreen(mainViewModel = MainViewModel())
    }
}

@Preview(showBackground = true)
@Composable
fun ButtonPreview() {
    CalculatorTheme(darkTheme = false) {
        Surface {
            CalculatorButton(
                onClick = { /*TODO*/ },
                symbol = '2',
                fontSize = 25.sp,
                containerColor = null,
                contentColor = null
            )
        }
    }
}