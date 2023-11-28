package uz.gita.jaxongir.userformapp.ui.components

import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Icon
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.dp
import uz.gita.jaxongir.userformapp.R
import uz.gita.jaxongir.userformapp.data.local.room.entity.ComponentEntity
import uz.gita.jaxongir.userformapp.data.model.ComponentData

@Composable
fun DraftItem(
    componentData: ComponentData,
    onClick : () -> Unit
) {
    Row(
        modifier = Modifier
            .fillMaxWidth()
            .padding(12.dp)
            .border(2.dp, Color.Gray, RoundedCornerShape(10.dp))
            .clickable {
                onClick()
            }
            .padding(16.dp),
        verticalAlignment = Alignment.CenterVertically
    ) {
        Spacer(modifier = Modifier.width(8.dp))
        Text(text = "Draft Form")
        Spacer(modifier = Modifier.weight(1f))
        Icon(
            painter = painterResource(id = R.drawable.baseline_arrow_forward_ios_24),
            contentDescription = "arrow"
        )
    }

}