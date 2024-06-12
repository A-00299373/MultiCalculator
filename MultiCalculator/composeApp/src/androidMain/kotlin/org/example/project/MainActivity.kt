package org.example.project

import App
import Calculator
import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Button
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.MutableState
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        setContent {
            App()
        }
    }
}

@Preview
@Composable
fun AppAndroidPreview() {
    App()
}

@Composable
fun CalcView() {
    val displayText = remember { mutableStateOf("0") }
    }

@Composable
fun CalcRow(display: MutableState<String>, startNum: Int, numButtons: Int) {
    val endNum = startNum + numButtons
    Row(
        modifier = Modifier
            .padding(0.dp)
    ) {
        for (i in startNum until endNum) {
            CalcNumericButton(number = i, display = display)
        }
    }
}

@Composable
fun CalcDisplay(display: MutableState<String>) {
    Text(
        text = display.value,
        modifier = Modifier
            .height(50.dp)
            .padding(5.dp)
            .fillMaxWidth(),
        fontSize = 24.sp,
    )
}

@Composable
fun CalcNumericButton(number: Int, display: MutableState<String>, modifier: Modifier = Modifier) {
    Button(
        onClick = {
            if (display.value == "0") {
                display.value = number.toString()
            } else {
                display.value += number.toString()
            }
        },
        modifier = modifier
            .padding(4.dp)
    ) {
        Text(text = number.toString(), fontSize = 24.sp)
    }
}

@Composable
fun CalcOperationButton(operation: String, display: MutableState<String>) {
    Button(
        onClick = {
            if (display.value != "0") {
                display.value += operation
            }
        },
        modifier = Modifier
            .padding(4.dp)
    ) {
        Text(text = operation, fontSize = 24.sp, color = Color.White)
    }
}

@Composable
fun CalcEqualsButton(display: MutableState<String>, calculator: Calculator) {
    Button(
        onClick = {
            if (display.value != "0") {
                try {
                    val expression = display.value
                    val parts = expression.split("\\+|\\-|\\*|\\/".toRegex())
                    val left = parts[0].toInt()
                    val right = parts[1].toInt()
                    val operation = expression.replace("\\d+".toRegex(), "")

                    when (operation) {
                        "+" -> {
                            val total = calculator.add(left, right)
                            display.value = total.toString()
                        }
                        "-" -> {
                            val total = calculator.subtract(left, right)
                            display.value = total.toString()
                        }
                        "*" -> {
                            val total = calculator.multiply(left, right)
                            display.value = total.toString()
                        }
                        "/" -> {
                            val total = calculator.divide(left, right)
                            display.value = total.toString()
                        }
                    }
                } catch (e: Exception) {
                    display.value = "0"
                }
            }
        },
        modifier = Modifier
            .padding(4.dp)
    ) {
        Text(text = "=", fontSize = 24.sp, color = Color.White)
    }
}

