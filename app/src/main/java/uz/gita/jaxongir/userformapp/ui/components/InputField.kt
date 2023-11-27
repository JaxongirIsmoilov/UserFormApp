package uz.gita.jaxongir.userformapp.ui.components

import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.OutlinedTextFieldDefaults
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.input.KeyboardType
import uz.gita.jaxongir.userformapp.data.enums.TextFieldType
import uz.gita.jaxongir.userformapp.data.model.ComponentData


@Composable
fun InputField(
    onEdit: (String) -> Unit,
    componentData: ComponentData,
) {
    val maxLength = if (componentData.maxLength == 0) Integer.MAX_VALUE else componentData.maxLength
    val minLength = if (componentData.minLength == 0) Integer.MIN_VALUE else componentData.minLength
    val maxValue = if (componentData.maxValue == 0) Integer.MAX_VALUE else componentData.maxValue
    val minValue = if (componentData.minValue == 0) Integer.MIN_VALUE else componentData.minValue
    var newContentForNumber by remember {
        mutableStateOf("")
    }
    var newContentForOther by remember {
        mutableStateOf("")
    }

    var newContentForText by remember {
        mutableStateOf("")
    }

    var currentValue by remember {
        mutableStateOf("")
    }

    when (componentData.textFieldType) {
        TextFieldType.Email -> {
            OutlinedTextField(
                value = newContentForOther,
                onValueChange = {
                    newContentForOther = it
                    onEdit(it)
                },
                keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Email),
                label = { Text(text = componentData.content) },
                modifier = Modifier.fillMaxWidth(),
                colors = OutlinedTextFieldDefaults.colors(
                    focusedBorderColor = Color(0xFFFF3951),
                    unfocusedBorderColor = Color(0xFFFF7686)
                ), singleLine = true
            )
        }

        TextFieldType.Number -> {
            OutlinedTextField(
                value = newContentForNumber,
                onValueChange = { input ->
                    currentValue = input
                    if (input.isEmpty()) {
                        newContentForNumber = ""
                    } else {
                        if (input.toInt() < minValue) {
                            newContentForNumber = minValue.toString()
                        } else if (input.toInt() in minValue..maxValue) {
                            newContentForNumber = input
                        } else {
                            newContentForNumber = maxValue.toString()
                        }
                    }
                    onEdit(newContentForNumber)
                },
                keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Number),
                label = { Text(text = componentData.content) },
                modifier = Modifier.fillMaxWidth(),
                colors = OutlinedTextFieldDefaults.colors(
                    focusedBorderColor = Color(0xFFFF3951),
                    unfocusedBorderColor = Color(0xFFFF7686)
                ), singleLine = true
            )
        }

        TextFieldType.Text -> {
            OutlinedTextField(
                value = newContentForText,
                onValueChange = {
                    if (it.length <= maxLength) {
                        newContentForText = it
                    }
                    onEdit(newContentForText)
                },
                keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Text),
                label = { Text(text = componentData.content) },
                modifier = Modifier.fillMaxWidth(),
                colors = OutlinedTextFieldDefaults.colors(
                    focusedBorderColor = Color(0xFFFF3951),
                    unfocusedBorderColor = Color(0xFFFF7686)
                ), singleLine = true
            )
        }

        TextFieldType.Phone -> {
            OutlinedTextField(
                value = newContentForOther,
                onValueChange = {
                    newContentForOther = it
                    onEdit(newContentForOther)
                },
                keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Phone),
                label = { Text(text = componentData.content) },
                modifier = Modifier.fillMaxWidth(),
                colors = OutlinedTextFieldDefaults.colors(
                    focusedBorderColor = Color(0xFFFF3951),
                    unfocusedBorderColor = Color(0xFFFF7686)
                ), singleLine = true
            )
        }
    }

}