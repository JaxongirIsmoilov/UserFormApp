package uz.gita.jaxongir.userformapp.ui.components

import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.Text
import androidx.compose.material3.Card
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import uz.gita.jaxongir.userformapp.data.local.room.entity.FormEntity


@Composable
fun SubmitedItem(
    formEntity: FormEntity
) {

    var count by remember {
        mutableStateOf(listOf<String>())
    }

    Card(
        modifier = Modifier
            .padding(horizontal = 10.dp)
            .fillMaxWidth()
            .background(Color(0xFFF6F2F7))
            .border(1.dp, Color(0xFFFF3951), RoundedCornerShape(12.dp))
            .height(100.dp)
    ) {
        Text(
            text = "Submited form:${count+1}",
            modifier = Modifier
                .padding(start = 20.dp)
                .padding(top = 20.dp), fontWeight = FontWeight.Bold
        )

        Text(
            text = "Items count:      [${formEntity.listComponents.size}]",
            modifier = Modifier
                .padding(start = 20.dp)
                .padding(top = 20.dp), fontWeight = FontWeight.Bold
        )

        Text(
            text = "Date:",
            modifier = Modifier.padding(start = 260.dp),
            fontWeight = FontWeight.Bold
        )
    }

}


@Composable
@Preview(showBackground = true)
fun SubmitedItemPreview() {
    SubmitedItem(  FormEntity(1, listOf(), true, true))
}