package uz.gita.jaxongir.userformapp.ui.components

import androidx.compose.foundation.border
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Card
import androidx.compose.material3.DropdownMenuItem
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.ExposedDropdownMenuBox
import androidx.compose.material3.Text
import androidx.compose.material3.TextFieldDefaults
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp

@Composable
@Preview(showBackground = true)
fun DialogSpinnerPreview() {
    DialogSpinner()
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun DialogSpinner(
    list: List<String> = listOf("iphone", "nokia", "samsung", "redmi", "xiomi"),
    selectedItem: String = ""
) {

    var expanded by remember {
        mutableStateOf(false)
    }
    var selected by remember {
        mutableStateOf(selectedItem)
    }
    var selectedOptionText by remember { mutableStateOf(list[0]) }

    Card(
        modifier = Modifier
            .fillMaxWidth()
            .height(240.dp)
            .border(1.dp, Color(0xFFFF3951), RoundedCornerShape(12.dp))
    ) {
        var text by remember {
            mutableStateOf("Hello World!")
        }
        androidx.compose.material3.TextField(
            value = text, onValueChange = {
                text = it
            },
            modifier = Modifier
                .padding(top = 10.dp, start = 12.dp, end = 12.dp)
                .fillMaxWidth()
                .align(Alignment.CenterHorizontally),
            colors = TextFieldDefaults.outlinedTextFieldColors(
                focusedBorderColor = Color(0xFFFF3951),
                unfocusedBorderColor = Color(0xFFFF7686)
            )
        )
        Spacer(modifier = Modifier.height(8.dp))
        Column(
            modifier = Modifier
                .padding(top = 10.dp, start = 12.dp, end = 12.dp, bottom = 10.dp)
                .fillMaxWidth()
        ) {
            ExposedDropdownMenuBox(
                expanded = expanded,
                onExpandedChange = {
                    expanded = !expanded
                },
            ) {
                androidx.compose.material3.TextField(
                    readOnly = true,
                    value = selected,
                    onValueChange = { },
                    label = { Text("Label") },
                    trailingIcon = {
                        androidx.compose.material3.ExposedDropdownMenuDefaults.TrailingIcon(
                            expanded = expanded
                        )
                    },
                    colors = TextFieldDefaults.outlinedTextFieldColors(
                        focusedBorderColor = Color(0xFFFF3951),
                        unfocusedBorderColor = Color(0xFFFF7686)
                    ),
                    modifier = Modifier.fillMaxWidth()
                )
                ExposedDropdownMenu(
                    expanded = expanded,
                    onDismissRequest = {
                        expanded = false
                    }
                ) {
                    list.forEach { selectionOption ->
                        DropdownMenuItem(
                            text = { Text(text = selectionOption) },
                            onClick = {
                                selectedOptionText = selectionOption
                                expanded = false
                            }
                        )
                    }
                }
            }
            Spacer(modifier = Modifier.size(10.dp))
            ExposedDropdownMenuBox(
                expanded = expanded,
                onExpandedChange = {
                    expanded = !expanded
                },
            ) {
                androidx.compose.material3.TextField(
                    readOnly = true,
                    value = selected,
                    onValueChange = { },
                    label = { Text("Label") },
                    trailingIcon = {
                        androidx.compose.material3.ExposedDropdownMenuDefaults.TrailingIcon(
                            expanded = expanded
                        )
                    },
                    colors = TextFieldDefaults.outlinedTextFieldColors(
                        focusedBorderColor = Color(0xFFFF3951),
                        unfocusedBorderColor = Color(0xFFFF7686)
                    ),
                    modifier = Modifier.fillMaxWidth()
                )
                ExposedDropdownMenu(
                    expanded = expanded,
                    onDismissRequest = {
                        expanded = false
                    }
                ) {
                    list.forEach { selectionOption ->
                        DropdownMenuItem(
                            text = { Text(text = selectionOption) },
                            onClick = {
                                selectedOptionText = selectionOption
                                expanded = false
                            }
                        )
                    }
                }
            }
        }

    }
}




