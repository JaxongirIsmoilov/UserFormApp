package uz.gita.jaxongir.userformapp.ui.components

import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.ExperimentalFoundationApi
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.wrapContentWidth
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Card
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import uz.gita.jaxongir.userformapp.data.model.ComponentData

@Composable
fun TextComponent(
    componentData: ComponentData
) {
    Card(
        modifier = Modifier
            .padding(vertical = 10.dp, horizontal = 12.dp)
            .fillMaxWidth()
            .height(54.dp)
            .clip(RoundedCornerShape(12.dp))
            .border(BorderStroke(2.dp, Color(0xFFFF7686)), RoundedCornerShape(12.dp))

    ) {
        Row(
            modifier = Modifier
                .fillMaxSize()
                .background(color = Color(0x33D1D1D1))
                .padding(vertical = 10.dp, horizontal = 15.dp)
        ) {


            Spacer(modifier = Modifier.size(15.dp))


            Box(
                modifier = Modifier
                    .fillMaxHeight()
                    .wrapContentWidth()
            ) {
                Column(modifier = Modifier.align(Alignment.Center)) {
                    Text(
                        text = componentData.content,
                        fontSize = 22.sp,
                        fontWeight = FontWeight.SemiBold
                    )

                }

            }
        }
    }
}