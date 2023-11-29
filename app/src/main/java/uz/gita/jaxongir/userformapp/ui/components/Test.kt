package uz.gita.jaxongir.userformapp.ui.components

import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.aspectRatio
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.material.TextField
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview

@Composable
fun MyRow() {
    Row (modifier = Modifier.fillMaxWidth()){
        TextField(value = "Salom", onValueChange = {

        }, modifier = Modifier.weight(1f).aspectRatio(0.75f))
        TextField(value = "Salom", onValueChange = {

        }, modifier = Modifier.weight(1f))
        TextField(value = "Salom", onValueChange = {

        }, modifier = Modifier.weight(1f))


    }
}

@Preview(showBackground = true)
@Composable
fun MyRowPreview(){
    MyRow()
}