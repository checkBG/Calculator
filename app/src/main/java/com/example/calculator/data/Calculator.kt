package com.example.calculator.data

import android.util.Log
import java.math.BigDecimal
import java.math.RoundingMode

class Calculator(string: String) {
    val result = calculator(string = string)
    var isResult = false

    private fun calculator(string: String): Double? {
        try {
            var stringExpressionWithoutBraces =
                string.trimEnd('+', '*', '-', '/', '.', '(').replace(" ", "")
            while (stringExpressionWithoutBraces.count { it == '(' } > stringExpressionWithoutBraces.count { it == ')' }) {
                stringExpressionWithoutBraces += ")"
            }
            while (stringExpressionWithoutBraces.count { it == ')' } > stringExpressionWithoutBraces.count { it == '(' }) {
                stringExpressionWithoutBraces = "($stringExpressionWithoutBraces"
            }
            Log.d("stringExpressionWithoutBraces", stringExpressionWithoutBraces)
            val bracesRegex = Regex("[(][^(]*?[)]")
            val bracesExpressions =
                bracesRegex.findAll(stringExpressionWithoutBraces).map { it.value }.toMutableList()

            while (bracesExpressions.isNotEmpty()) {
                stringExpressionWithoutBraces = stringExpressionWithoutBraces.replaceFirst(
                    bracesExpressions[0],
                    calculator(bracesExpressions[0].drop(1).dropLast(1)).toString()
                )
                bracesExpressions.removeAt(0)
                if (bracesExpressions.isEmpty()) {
                    bracesExpressions.addAll(
                        bracesRegex.findAll(stringExpressionWithoutBraces).map { it.value })
                }
            }

            val expressionRegex =
                Regex("(-?\\d+([.]\\d+)?([eE]\\d+)?.?)") // шаблон возможного числа и его знака, следующего за ним (этого знака может и не быть)
            // TODO:Реализовать обработку процентов
            val expressions =
                expressionRegex.findAll(stringExpressionWithoutBraces).map { it.value }
                    .toMutableList() // поиск всех подходящих значений и преобразование в MutableMap<String>

            var mulOrDivIndex: Int =
                expressions.indexOfFirst { it.endsWith('*') || it.endsWith('/') } // поиск индекса числа, после которого идёт операция (* или /), то есть учитывается математический порядок
            while (mulOrDivIndex != -1) { // если такого числа нет, то вёрнётся -1
                expressions[mulOrDivIndex] =
                    action(
                        firstOperand = expressions[mulOrDivIndex],
                        secondOperand = expressions[mulOrDivIndex + 1]
                    )
                        ?: throw Exception("cannot execute the function") // меняем по индексу число на результат (число1 операция число2)
                expressions.removeAt(mulOrDivIndex + 1) // удаляем число2, которым было умножено или поделено число1 (знак числа2 переносится в их результат для дальнейшей корректной работы алгоритма)
                mulOrDivIndex =
                    expressions.indexOfFirst { it.endsWith('*') || it.endsWith('/') } // проверяем на наличие ещё одного числа с подходящими знаками
            }

            var plusOrMinusIndex: Int =
                expressions.indexOfFirst { it.endsWith('-') || it.endsWith('+') } // поиск индекса числа, после которого идёт операция (+ или -), то есть учитывается математический порядок
            while (plusOrMinusIndex != -1) { // если такого числа нет, то вёрнётся -1
                expressions[plusOrMinusIndex] =
                    action(
                        firstOperand = expressions[plusOrMinusIndex],
                        secondOperand = expressions[plusOrMinusIndex + 1]
                    )
                        ?: throw Exception("cannot execute the function") // меняем по индексу число на результат (число1 операция число2)
                expressions.removeAt(plusOrMinusIndex + 1) // удаляем число2, которым было сложено или вычтено число1 (знак числа2 переносится в их результат для дальнейшей корректной работы алгоритма)
                plusOrMinusIndex =
                    expressions.indexOfFirst { it.endsWith('-') || it.endsWith('+') } // проверяем на наличие ещё одного числа с подходящими знаками
            }
            Log.d("resultF", expressions.first().toString())
            return expressions.first().toDouble() // возвращаем результат в типе Double
        } catch (exception: Exception) {
            Log.e("calculator function exception", exception.toString())
            return null
        }
    }

    private fun action(firstOperand: String, secondOperand: String): String? {
        try {
            val firstOperandWithoutLastChar =
                BigDecimal(firstOperand.dropLast(1)) // берём число, которое удовлетворяет шаблону
            val firstOperandLastChar = firstOperand.last() // берём знак числа1
            val secondOperandWithoutLastChar =
                BigDecimal(Regex("-?\\d+(.\\d+)?([eE]\\d+)?").find(secondOperand)!!.value) // берём число, удовлетворяющее шаблону
            val secondOperandLastChar = Regex("[*\\-+/]$").find(secondOperand)?.value
                ?: "" // берём знак числа2, иначе пустая строка, из-за которой алгоритм завершится
            val result = when (firstOperandLastChar) { // исходя из знака числа1
                '-' -> firstOperandWithoutLastChar - secondOperandWithoutLastChar
                '+' -> firstOperandWithoutLastChar + secondOperandWithoutLastChar
                '*' -> firstOperandWithoutLastChar * secondOperandWithoutLastChar
                '/' -> firstOperandWithoutLastChar.divide(
                    secondOperandWithoutLastChar,
                    maxOf(
                        secondOperandWithoutLastChar.toString().length - firstOperandWithoutLastChar.toString().length + 20,
                        50
                    ),
                    RoundingMode.HALF_UP
                )

                else -> throw Exception("something went wrong")
            }
            return "$result$secondOperandLastChar" // возвращение результата двух чисел и знак числа2 в конце
        } catch (e: Exception) {
            Log.e("action function", e.toString())
            return null
        }
    }
}