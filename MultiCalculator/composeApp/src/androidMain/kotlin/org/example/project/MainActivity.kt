package org.example.project

import App
import Calculator
import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Button
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.MutableState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
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
fun App() {
    CalcView()
}

@Composable
fun CalcView() {
    var leftNumber by rememberSaveable { mutableStateOf(0) }
    var rightNumber by rememberSaveable { mutableStateOf(0) }
    var operation by rememberSaveable { mutableStateOf("") }
    var complete by rememberSaveable { mutableStateOf(false) }
    val displayText = remember { mutableStateOf("0") }

    val inputText = remember { mutableStateOf("") }

    if (complete && operation.isNotEmpty()) {
        var answer = 0
        when (operation) {
            "+" -> answer = leftNumber + rightNumber
            "-" -> answer = leftNumber - rightNumber
            "*" -> answer = leftNumber * rightNumber
            "/" -> answer = leftNumber / rightNumber
        }
        displayText.value = answer.toString()

        leftNumber = answer
        rightNumber = 0
        complete = false
        operation = ""
    }

    fun numberPress(btnNum: Int) {
        if (complete) {
            leftNumber = 0
            rightNumber = 0
            operation = ""
            complete = false
            inputText.value = ""
            displayText.value = ""
        }

        if (operation.isNotEmpty() && !complete) {
            rightNumber = rightNumber * 10 + btnNum
            inputText.value = rightNumber.toString()
        } else if (operation.isEmpty() && !complete) {
            leftNumber = leftNumber * 10 + btnNum
            inputText.value = leftNumber.toString()
        }

        displayText.value = if (operation.isNotEmpty()) {
            "$leftNumber $operation ${inputText.value}"
        } else {
            if (inputText.value.isEmpty()) "0" else inputText.value
        }
    }

    fun operationPress(op: String) {
        if (inputText.value.isNotEmpty()) {
            leftNumber = inputText.value.toInt()
            inputText.value = ""
        }
        operation = op
        displayText.value = if (operation.isNotEmpty()) {
            "$leftNumber $operation ${inputText.value}"
        } else {
            if (inputText.value.isEmpty()) "0" else inputText.value
        }
    }

    fun equalsPress() {
        if (operation.isNotEmpty() && inputText.value.isNotEmpty()) {
            rightNumber = inputText.value.toInt()
            inputText.value = ""
            complete = true
            displayText.value = if (operation.isNotEmpty()) {
                "$leftNumber $operation ${inputText.value}"
            } else {
                if (inputText.value.isEmpty()) "0" else inputText.value
            }
        }
    }

    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(16.dp)
            .background(color = Color.LightGray)
    ) {
        Row(
            modifier = Modifier.fillMaxWidth(),
            verticalAlignment = Alignment.CenterVertically,
            horizontalArrangement = Arrangement.End
        ) {
            CalcDisplay(displayText)
        }
        Row(
            modifier = Modifier.fillMaxWidth(),
            horizontalArrangement = Arrangement.SpaceEvenly
        ) {
            Column(
                modifier = Modifier.weight(3f),
                verticalArrangement = Arrangement.spacedBy(8.dp)
            ) {
                for (i in 7 downTo 1 step 3) {
                    CalcRow(onPress = { number -> numberPress(number) }, startNum = i, numButtons = 3)
                }
                Row(
                    modifier = Modifier.fillMaxWidth(),
                    horizontalArrangement = Arrangement.SpaceEvenly
                ) {
                    CalcNumericButton(number = 0, onPress = { number -> numberPress(number) })
                    CalcEqualsButton(onPress = { equalsPress() })
                }
            }

            Column(
                modifier = Modifier.weight(1f),
                verticalArrangement = Arrangement.spacedBy(8.dp)
            ) {
                CalcOperationButton(operation = "/", onPress = { op -> operationPress(op) })
                CalcOperationButton(operation = "*", onPress = { op -> operationPress(op) })
                CalcOperationButton(operation = "-", onPress = { op -> operationPress(op) })
                CalcOperationButton(operation = "+", onPress = { op -> operationPress(op) })
            }
        }
    }
}

@Composable
fun CalcRow(onPress: (number: Int) -> Unit, startNum: Int, numButtons: Int) {
    val endNum = startNum + numButtons
    Row(
        modifier = Modifier.padding(0.dp)
    ) {
        for (i in startNum until endNum) {
            CalcNumericButton(onPress = onPress, number = i)
        }
    }
}

@Composable
fun CalcDisplay(display: MutableState<String>) {
    Text(
        text = display.value,
        modifier = Modifier
            .fillMaxWidth()
            .height(50.dp)
            .padding(5.dp),
    )
}

    @Composable
    fun CalcNumericButton(number: Int, onPress: (number: Int) -> Unit) {
        Button(
            onClick = { onPress(number) },
            modifier = Modifier.padding(4.dp)
        ) {
            Text(text = number.toString())
        }
    }

    @Composable
    fun CalcOperationButton(operation: String, onPress: (operation: String) -> Unit) {
        Button(
            onClick = { onPress(operation) },
            modifier = Modifier.padding(4.dp)
        ) {
            Text(text = operation)
        }
    }

    @Composable
    fun CalcEqualsButton(onPress: () -> Unit) {
        Button(
            onClick = { onPress() },
            modifier = Modifier.padding(4.dp)
        ) {
            Text(text = "=")
        }
    }



