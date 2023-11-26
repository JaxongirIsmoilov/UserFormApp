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
    var newContent by remember {
        mutableStateOf("0")
    }
    val type =
        when (componentData.textFieldType) {
            TextFieldType.Email -> {
                KeyboardType.Email
            }

            TextFieldType.Number -> {
                KeyboardType.Number
            }

            TextFieldType.Text -> {
                KeyboardType.Text
            }

            TextFieldType.Phone -> {
                KeyboardType.Phone
            }
        }

    val maxLength = if(componentData.maxLength == 0) Integer.MAX_VALUE else componentData.maxLength
    val minLength = if (componentData.minLength == 0) Integer.MIN_VALUE else componentData.minLength
    val maxValue = if(componentData.maxValue == 0) Integer.MAX_VALUE else componentData.maxValue
    val minValue = if(componentData.minValue == 0) Integer.MIN_VALUE else componentData.minValue


        OutlinedTextField(
            value = newContent,
            onValueChange = {
                if (maxLength < it.length) {
                    newContent = it
                    onEdit(newContent)
                } else if(maxValue < it.toInt()) {
                    newContent = it
                    onEdit(newContent)
                }else if(minValue> it.toInt()){
                    newContent = it
                    onEdit(newContent)
                }else{
                    newContent = newContent
                }
            },
            keyboardOptions = KeyboardOptions(keyboardType = type),
            label = { Text(text = componentData.content) },
            modifier = Modifier.fillMaxWidth(),
            colors = OutlinedTextFieldDefaults.colors(
                focusedBorderColor = Color(0xFFFF3951),
                unfocusedBorderColor = Color(0xFFFF7686)
            )
        )

}