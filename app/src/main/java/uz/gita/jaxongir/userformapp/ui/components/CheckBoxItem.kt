package uz.gita.jaxongir.userformapp.ui.components

import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Checkbox
import androidx.compose.material3.CheckboxDefaults
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp

@Composable
fun CheckBoxItem(
    title: String,
    onEdit: () -> Unit,
    isEnable: Boolean,
    state: Boolean,
    checkedStates: (Boolean) -> Unit,

    ) {
    var checkedState = remember { mutableStateOf(state) }
    Row(
        modifier = Modifier
            .fillMaxWidth()
            .clip(RoundedCornerShape(12.dp))
            .border(1.dp, Color(0xFFFF7686), RoundedCornerShape(12.dp))
            .background(Color(0x33C4C4C4))
            .clickable {
                checkedState.value = !checkedState.value
                onEdit()
            }
            .padding(horizontal = 16.dp, vertical = 5.dp),
        verticalAlignment = Alignment.CenterVertically
    ) {
        checkedStates.invoke(checkedState.value)
        Checkbox(
            checked = checkedState.value,
            onCheckedChange = {
                if (isEnable) {
                    checkedState.value = it

                }

            },
            colors = CheckboxDefaults.colors(
                checkedColor = Color(0xFFFF7686),
                uncheckedColor = Color(0x33c4c4c4),
            )
        )
        Text(title, fontSize = 22.sp)
    }
}