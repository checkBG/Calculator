package com.example.calculator.ui

import android.content.Context
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
import androidx.compose.ui.draw.drawWithContent
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.ColorFilter
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.TextUnit
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.calculator.utils.drawNeonStroke
import com.example.calculator.utils.vibration

@Composable
fun CalculatorButton(
    onClick: () -> Unit,
    modifier: Modifier = Modifier,
    symbol: Char,
    contentColor: Color?,
    containerColor: Color?,
    fontSize: TextUnit = 23.sp,
    context: Context,
) {
    ElevatedButton(
        onClick = { onClick.vibration(context = context) },
        elevation = ButtonDefaults.elevatedButtonElevation(defaultElevation = 5.dp),
        colors = ButtonDefaults.elevatedButtonColors(
//            containerColor = containerColor ?: MaterialTheme.colorScheme.primary,
//            contentColor = contentColor ?: MaterialTheme.colorScheme.scrim
            contentColor = contentColor ?: Color(0xFF2a50ea)
//            contentColor = Color(0xFF94FFCD)
        ),
        modifier = modifier
            .size(72.dp)
            .drawWithContent {
                drawContent()
                drawNeonStroke(
                    radius = 90.dp,
                    color = Color(0xFF2A50EA)
//                    color = Color(0xFF94FFCD)
                )
            }
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
    @StringRes description: Int,
    context: Context,
) {
    ElevatedButton(
        onClick = { onClick.vibration(context) },
        elevation = ButtonDefaults.elevatedButtonElevation(defaultElevation = 5.dp),
        colors = ButtonDefaults.elevatedButtonColors(
//            containerColor = containerColor ?: MaterialTheme.colorScheme.primary,
            contentColor = contentColor ?: MaterialTheme.colorScheme.scrim
        ),
        modifier = modifier
            .size(72.dp)
            .drawWithContent {
                drawContent()
                drawNeonStroke(
                    radius = 72.dp,
                    color = Color(0xFF2A50EA),
//                    color = Color(0xFF94FFCD)
                )
            }
    ) {
        Image(
            painter = painterResource(id = image),
            contentDescription = stringResource(id = description),
            contentScale = ContentScale.FillWidth,
            colorFilter = ColorFilter.tint(color = Color(0xFF94FFCD))
        )
    }
}