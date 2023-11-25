package uz.gita.jaxongir.userformapp.ui.components

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Text
import androidx.compose.material3.TextFieldDefaults
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.tooling.preview.Preview
import uz.gita.jaxongir.userformapp.data.enums.TextFieldType

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun InputField(
    textFieldType: TextFieldType,
    maxLines: Int,                      //tipi text bogan input bosa maximal qatorla soni
    maxLength: Int,                     //tipi text bogan input bosa maximal uzunligi
    minLength: Int,                     //tipi text bogan input bosa minimal uzunligi
    maxValue: Int,                      //tipi number bogan inputga qiymatni maximal chegarasi
    minValue: Int,
    question: String
) {
    var valueForEmail by remember {
        mutableStateOf("Hello world")
    }

    var valueForNumber by remember {
        mutableStateOf("Hello world")
    }

    var valueForPhone by remember {
        mutableStateOf("Hello world")
    }

    var valueForText by remember {
        mutableStateOf("Hello world")
    }

    when (textFieldType) {
        TextFieldType.Email -> {
            OutlinedTextField(
                value = valueForEmail, onValueChange = {
                    valueForEmail = it
                },
                modifier = Modifier
                    .fillMaxWidth()
                    .background(Color(0x33C4C4C4)),
                colors = TextFieldDefaults.outlinedTextFieldColors(
                    focusedBorderColor = Color(0xFFFF3951),
                    unfocusedBorderColor = Color(0xFFFF7686)
                ),
                //                maxLength = maxLength,
                maxLines = maxLines,
//                minLength = minLength,
//                minValue = minValue,
//                maxValue = maxValue,
                keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Email),
                label = { Text(text = question) }
            )
        }

        TextFieldType.Number -> {
            OutlinedTextField(
                value = valueForNumber, onValueChange = {
                    valueForNumber = it
                },
                modifier = Modifier
                    .fillMaxWidth()
                    .background(Color(0x33C4C4C4)),
                colors = TextFieldDefaults.outlinedTextFieldColors(
                    focusedBorderColor = Color(0xFFFF3951),
                    unfocusedBorderColor = Color(0xFFFF7686)
                ),
                //                maxLength = maxLength,
                maxLines = maxLines,
//                minLength = minLength,
//                minValue = minValue,
//                maxValue = maxValue,
                keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Number),
                label = { Text(text = question) }
            )
        }

        TextFieldType.Phone -> {
            OutlinedTextField(
                value = valueForPhone, onValueChange = {
                    valueForPhone = it
                },
                modifier = Modifier
                    .fillMaxWidth()
                    .background(Color(0x33C4C4C4)),
                colors = TextFieldDefaults.outlinedTextFieldColors(
                    focusedBorderColor = Color(0xFFFF3951),
                    unfocusedBorderColor = Color(0xFFFF7686)
                ),
//                maxLength = maxLength,
                maxLines = maxLines,
//                minLength = minLength,
//                minValue = minValue,
//                maxValue = maxValue,
                keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Phone),
                label = { Text(text = question) }
            )
        }

        TextFieldType.Text -> {
            OutlinedTextField(
                value = valueForText, onValueChange = {
                    valueForText = it
                },
                modifier = Modifier
                    .fillMaxWidth()
                    .background(Color(0x33C4C4C4)),
                colors = TextFieldDefaults.outlinedTextFieldColors(
                    focusedBorderColor = Color(0xFFFF3951),
                    unfocusedBorderColor = Color(0xFFFF7686)
                ),

//                maxLength = maxLength,
                maxLines = maxLines,
//                minLength = minLength,
//                minValue = minValue,
//                maxValue = maxValue,
                keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Text),
                label = { Text(text = question) }
            )
        }
    }


}

@Preview(showBackground = true)
@Composable
fun getTextFieldPreview() {
    InputField(TextFieldType.Text, 0, 0, 0, 0, 0, "")
}