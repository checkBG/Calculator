package com.example.calculator.ui

import androidx.annotation.DrawableRes
import androidx.annotation.StringRes
import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.size
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.ElevatedButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.TextUnit
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp

@Composable
fun CalculatorButton(
    onClick: () -> Unit,
    modifier: Modifier = Modifier,
    symbol: Char,
    contentColor: Color?,
    containerColor: Color?,
    fontSize: TextUnit = 23.sp
) {
    ElevatedButton(
        onClick = onClick,
        elevation = ButtonDefaults.elevatedButtonElevation(defaultElevation = 2.dp),
        colors = ButtonDefaults.elevatedButtonColors(
            containerColor = containerColor ?: MaterialTheme.colorScheme.primary,
            contentColor = contentColor ?: MaterialTheme.colorScheme.scrim
        ),
        modifier = modifier
            .size(72.dp)
    ) {
        Text(
            text = symbol.toString(),
            fontSize = fontSize,
            modifier = Modifier
        )
    }
}

@Composable
fun CalculatorButtonWithImage(
    onClick: () -> Unit,
    modifier: Modifier = Modifier,
    contentColor: Color?,
    containerColor: Color?,
    @DrawableRes image: Int,
    @StringRes description: Int
) {
    ElevatedButton(
        onClick = onClick,
        elevation = ButtonDefaults.elevatedButtonElevation(defaultElevation = 2.dp),
        colors = ButtonDefaults.elevatedButtonColors(
            containerColor = containerColor ?: MaterialTheme.colorScheme.primary,
            contentColor = contentColor ?: MaterialTheme.colorScheme.scrim
        ),
        modifier = modifier
            .size(72.dp)
    ) {
        Image(
            painter = painterResource(id = image),
            contentDescription = stringResource(id = description),
            contentScale = ContentScale.FillWidth
        )
    }
}