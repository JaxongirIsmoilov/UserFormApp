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
    isEnable: Boolean,
    isInDraft: Boolean
) {
    val maxLength = if (componentData.maxLength == 0) Integer.MAX_VALUE else componentData.maxLength
    val minLength = if (componentData.minLength == 0) Integer.MIN_VALUE else componentData.minLength
    val maxValue = if (componentData.maxValue == 0) Integer.MAX_VALUE else componentData.maxValue
    val minValue = if (componentData.minValue == 0) Integer.MIN_VALUE else componentData.minValue
    var newContentForNumber by remember {
        mutableStateOf(if (isInDraft) componentData.enteredValue else "0")
    }
    var newContentForOther by remember {
        mutableStateOf(if (isInDraft) componentData.enteredValue else "0")
    }

    var newContentForText by remember {
        mutableStateOf(if (isInDraft) componentData.enteredValue else "")
    }

    var currentValue by remember {
        mutableStateOf(if (isInDraft) componentData.enteredValue else "0")
    }

    when (componentData.textFieldType) {
        TextFieldType.Email -> {
            OutlinedTextField(
                value = newContentForOther,
                onValueChange = {
                    if (isEnable) {
                        newContentForOther = it
                        onEdit(it)
                    }

                },
                keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Email),
                label = { Text(text = componentData.content) },
                modifier = Modifier.fillMaxWidth(),
                colors = OutlinedTextFieldDefaults.colors(
                    focusedBorderColor = Color(0xFFFF3951),
                    unfocusedBorderColor = Color(0xFFFF7686)
                ), singleLine = true, readOnly = !isEnable
            )
        }

        TextFieldType.Number -> {
            OutlinedTextField(
                value = newContentForNumber,
                onValueChange = { input ->
                    if (isEnable) {
                        currentValue = input
                        if (input.isEmpty()) {
                            newContentForNumber = ""
                            onEdit("0")
                        } else {
                            if (input.toInt() < minValue) {
                                newContentForNumber = minValue.toString()
                                onEdit(newContentForNumber)
                            } else if (input.toInt() in minValue..maxValue) {
                                newContentForNumber = input
                                onEdit(newContentForNumber)
                            } else {
                                newContentForNumber = maxValue.toString()
                                onEdit(newContentForNumber)
                            }
                        }

                    }
                },
                keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Number),
                label = { Text(text = componentData.content) },
                modifier = Modifier.fillMaxWidth(),
                colors = OutlinedTextFieldDefaults.colors(
                    focusedBorderColor = Color(0xFFFF3951),
                    unfocusedBorderColor = Color(0xFFFF7686)
                ), singleLine = true, readOnly = !isEnable
            )
        }

        TextFieldType.Text -> {
            OutlinedTextField(
                value = newContentForText,
                onValueChange = {
                    if (isEnable) {
                        if (it.length <= maxLength) {
                            newContentForText = it
                        }
                        onEdit(newContentForText)
                    }

                },
                keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Text),
                label = { Text(text = componentData.content) },
                modifier = Modifier.fillMaxWidth(),
                colors = OutlinedTextFieldDefaults.colors(
                    focusedBorderColor = Color(0xFFFF3951),
                    unfocusedBorderColor = Color(0xFFFF7686)
                ), singleLine = true, readOnly = !isEnable
            )
        }

        TextFieldType.Phone -> {
            OutlinedTextField(
                value = newContentForOther,
                onValueChange = {
                    if (isEnable) {
                        newContentForOther = it
                        onEdit(newContentForOther)
                    }

                },
                keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Phone),
                label = { Text(text = componentData.content) },
                modifier = Modifier.fillMaxWidth(),
                colors = OutlinedTextFieldDefaults.colors(
                    focusedBorderColor = Color(0xFFFF3951),
                    unfocusedBorderColor = Color(0xFFFF7686)
                ), singleLine = true, readOnly = !isEnable
            )
        }
    }

}