package uz.gita.jaxongir.userformapp.ui.components

import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.aspectRatio
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.wrapContentSize
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Checkbox
import androidx.compose.material3.CheckboxDefaults
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.OutlinedTextFieldDefaults
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.core.net.toUri
import coil.compose.AsyncImage
import uz.gita.jaxongir.userformapp.data.model.ComponentData
import uz.gita.jaxongir.userformapp.utills.toDp

@Composable
fun ImageComponent(data: ComponentData) {
    var imageUri: String by remember { mutableStateOf(data.imgUri) }
    Column(modifier = Modifier.fillMaxWidth(), horizontalAlignment = Alignment.CenterHorizontally) {
        Spacer(modifier = Modifier.size(10.dp))
        AsyncImage(
            model = imageUri.toUri(),
            contentDescription = "",
            modifier = Modifier
                .padding(horizontal = 15.dp)
                .fillMaxWidth()
                .height(data.customHeight.toDp())
                .aspectRatio((data.ratioX / data.ratioY).toFloat())
                .background(color = Color(data.backgroundColor))
        )
        Spacer(modifier = Modifier.size(10.dp))

        var checkedState by remember { mutableStateOf(false) }
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .clip(RoundedCornerShape(12.dp))
                .border(1.dp, Color(0xFFFF7686), RoundedCornerShape(12.dp))
                .background(Color(0x33C4C4C4))
                .clickable {
                    checkedState = !checkedState
                }
                .padding(horizontal = 16.dp, vertical = 5.dp),
            verticalAlignment = Alignment.CenterVertically
        ) {
            Checkbox(
                checked = checkedState,
                onCheckedChange = {
                    checkedState = it
                },
                colors = CheckboxDefaults.colors(
                    checkedColor = Color(0xFFFF7686),
                    uncheckedColor = Color(0x33c4c4c4),
                )
            )
            Text(
                "O'zim image urisini kiritmoqchiman!",
                fontSize = 22.sp,
                color = if (checkedState) Color(0xFFFF3951) else Color(0xFFFF7686)
            )
        }


        Spacer(modifier = Modifier.size(5.dp))
        if (checkedState) {
            OutlinedTextField(
                value = imageUri, onValueChange = { imageUri = it }, modifier = Modifier
                    .padding(horizontal = 15.dp)
                    .fillMaxWidth(), colors = OutlinedTextFieldDefaults.colors(
                    focusedBorderColor = Color(0xFFFF3951),
                    unfocusedBorderColor = Color(0xFFFF7686),
                )
            )
            Spacer(modifier = Modifier.size(10.dp))

            Button(
                onClick = { checkedState=false },
                colors = ButtonDefaults.buttonColors(containerColor = Color(0xFFFF3951))
            ) {
                Text(
                    text = "Upload Image",
                    color = Color.White,
                    modifier = Modifier
                        .padding(10.dp)
                        .wrapContentSize()
                )
            }

            Spacer(modifier = Modifier.size(10.dp))
        }
    }
}