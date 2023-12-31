package uz.gita.jaxongir.userformapp.ui.components

import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import uz.gita.jaxongir.userformapp.data.local.room.entity.FormData

@Composable
fun SelectedItem(
    entity: FormData,
    onClick: () -> Unit
) {
    Box(
        modifier = Modifier
            .fillMaxWidth()
            .height(134.dp)
            .padding(12.dp)
            .border(2.dp, Color.Blue, RoundedCornerShape(10.dp))
            .clickable {
                onClick()
            }
            .padding(start = 16.dp, top = 16.dp, end = 16.dp),
    ) {
        Column(modifier = Modifier.fillMaxSize()) {
            Spacer(modifier = Modifier.width(8.dp))
            Text(
                text = "Submitted FormID: ${entity.id}",
                modifier = Modifier
//                    .align(Alignment.CenterVertically)
                    .padding(bottom = 16.dp),
                style = TextStyle(
                    fontSize = 16.sp,
                    fontStyle = FontStyle.Normal,
                    fontWeight = FontWeight.Bold
                )
            )
//            Spacer(modifier = Modifier.width(8.dp))
            Text(
                text = "List: ${entity.listComponentIds.size}", modifier = Modifier
//                    .align(Alignment.CenterVertically)
//                    .padding(bottom = 16.dp)
                ,
                style = TextStyle(
                    fontSize = 16.sp,
                    fontStyle = FontStyle.Normal,
                    fontWeight = FontWeight.Bold
                )
            )

//            Spacer(modifier = Modifier.weight(1f))


        }
        Text(
            text = "now", modifier = Modifier
                    .align(Alignment.BottomEnd)
                .padding(start = 300.dp, bottom = 2.dp),
            style = TextStyle(
                fontSize = 14.sp,
                fontStyle = FontStyle.Normal,
                fontWeight = FontWeight.Bold
            )
        )

    }

}

@Composable
@Preview(showBackground = true)
fun Selectedpreview(){
    SelectedItem(entity = FormData("", emptyList(), true, "", emptyList(), emptyList(),
        emptyList()
    )) {
        
    }
}
