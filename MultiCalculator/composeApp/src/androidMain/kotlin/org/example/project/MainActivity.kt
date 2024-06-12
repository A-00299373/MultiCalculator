package org.example.project

import App
import Calculator
import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Button
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.MutableState
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

}

@Composable
fun CalcRow() {

}

@Composable
fun CalcDisplay() {

}

@Composable
fun CalcNumericButton() {

}

@Composable
fun CalcOperationButton(operation: String, display: MutableState<String>) {

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

