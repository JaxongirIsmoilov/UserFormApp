package uz.gita.jaxongir.userformapp.presenter.drafts_detail

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import cafe.adriel.voyager.androidx.AndroidScreen
import cafe.adriel.voyager.hilt.getViewModel
import uz.gita.jaxongir.userformapp.R
import uz.gita.jaxongir.userformapp.data.model.ComponentData

class DraftDetails(private val componentData: ComponentData) : AndroidScreen() {
    @Composable
    override fun Content() {
        val vm:DraftScreenContract.ViewModel = getViewModel<DraftDetailsViewModelImpl>()
        DraftDetailsContent(componentData, vm::onEventDispatcher)
    }
}

@Composable
fun DraftDetailsContent(
    data:ComponentData,
    onEventDispatchers: (DraftScreenContract.Intent) -> Unit,
    ) {
    Box(modifier = Modifier.fillMaxSize()){
        Column(
            modifier = Modifier
                .fillMaxSize()
        ) {
            Box(
                modifier = Modifier
                    .fillMaxWidth()
                    .background(color = Color(0xFFFF3951))
                    .height(70.dp)
            ) {

                Row (modifier = Modifier.fillMaxSize()){
                    IconButton(
                        modifier = Modifier
                            .align(Alignment.CenterVertically)
                            .padding(start = 16.dp),
                        onClick = {
                            onEventDispatchers.invoke(DraftScreenContract.Intent.Back)
                        }
                    ) {
                        Icon(
                            painter = painterResource(id = R.drawable.baseline_arrow_back_ios_24),
                            contentDescription = null,
                            modifier = Modifier.size(36.dp),
                            tint = Color.White
                        )
                    }

                    Spacer(modifier = Modifier.size(8.dp))

                    Text(
                        text = "User: Name",
                        style = TextStyle(
                            fontSize = 24.sp,
                            fontStyle = FontStyle.Normal,
                            fontWeight = FontWeight.Bold
                        ),
                        modifier = Modifier
                            .align(Alignment.CenterVertically)
                            .padding(start = 16.dp),
                        color = Color.White
                    )

                    Spacer(modifier = Modifier.weight(1f))

                    IconButton(
                        modifier = Modifier
                            .align(Alignment.CenterVertically)
                            .padding(end = 16.dp),
                        onClick = {
                            onEventDispatchers.invoke(DraftScreenContract.Intent.Submit(data))
                        }
                    ) {
                        Icon(
                            painter = painterResource(id = R.drawable.ic_submit),
                            contentDescription = null,
                            modifier = Modifier.size(36.dp),
                            tint = Color.White
                        )
                    }
                }



            }

            LazyColumn(modifier = Modifier.padding(top = 16.dp)){


            }
        }

    }
}

@Preview(showBackground = true)
@Composable
fun DraftDetailsPreview(){
    //DraftDetailsContent(onEventDispatchers = {}, data = ComponentData())
}