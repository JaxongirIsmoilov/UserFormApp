package uz.gita.jaxongir.userformapp.ui.components

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.wrapContentHeight
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import uz.gita.jaxongir.userformapp.data.model.ComponentData

@Composable
fun SelectorItem(
    question: String,
    list: List<String>,
    onSaveStates: (List<Boolean>) -> Unit,
    componentData: ComponentData,
    deleteComp: (String) -> Unit,
    isEnable: Boolean,
    isInDraft: Boolean
) {
    val selectedItem = remember { mutableStateOf<String?>(null) }
    val listSem = arrayListOf<Boolean>()

    Column(
        modifier = Modifier
            .then(
                if (componentData.isVisible) Modifier
                    .fillMaxWidth()
                    .wrapContentHeight()
                else Modifier.size(0.dp)
            )

    ) {

        Spacer(modifier = Modifier.size(10.dp))
        Row(verticalAlignment = Alignment.CenterVertically) {
            Text(
                text = question,
                fontSize = 22.sp,
            )
            Spacer(modifier = Modifier.weight(1f))
            if (componentData.isRequired) {
                Text(
                    text = "This Field is required",
                    fontWeight = FontWeight(600),
                    color = Color(0xFFff7686)
                )
            }
        }
        Spacer(modifier = Modifier.size(16.dp))
        list.forEach {
            CheckBoxItem(title = it, { deleteComp(it) }, isEnable) {
                listSem.add(it)
                onSaveStates.invoke(listSem)
            }
            Spacer(modifier = Modifier.height(8.dp))
        }
    }
}

@Preview(showBackground = true)
@Composable
fun getSelectorPreview() {

}