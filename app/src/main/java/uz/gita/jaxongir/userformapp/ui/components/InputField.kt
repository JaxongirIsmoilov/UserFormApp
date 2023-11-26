package uz.gita.jaxongir.userformapp.ui.components

import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material3.TextField
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.text.input.KeyboardType
import uz.gita.jaxongir.userformapp.data.enums.TextFieldType
import uz.gita.jaxongir.userformapp.data.model.ComponentData


@Composable
fun InputField(
    onEdit: (String) -> Unit,
    componentData: ComponentData,
) {
    var newContent by remember {
        mutableStateOf("")
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
        TextField(
            value = newContent,
            onValueChange = {
                if (maxLength > it.length) {
                    newContent = it
                    onEdit(it)
                }

            },
            keyboardOptions = KeyboardOptions(keyboardType = type),
        )

}