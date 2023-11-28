package uz.gita.jaxongir.userformapp.presenter.drafts_detail

import android.os.Build
import android.util.Log
import androidx.annotation.RequiresApi
import androidx.compose.foundation.background
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
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.wrapContentHeight
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Icon
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
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import cafe.adriel.voyager.androidx.AndroidScreen
import cafe.adriel.voyager.hilt.getViewModel
import uz.gita.jaxongir.userformapp.R
import uz.gita.jaxongir.userformapp.data.enums.ComponentEnum
import uz.gita.jaxongir.userformapp.data.model.ComponentData
import uz.gita.jaxongir.userformapp.ui.components.DatePickerPreview
import uz.gita.jaxongir.userformapp.ui.components.InputField
import uz.gita.jaxongir.userformapp.ui.components.SampleSpinnerPreview
import uz.gita.jaxongir.userformapp.ui.components.SelectorItem

class DraftDetails(private val list: List<ComponentData>) : AndroidScreen() {
    @RequiresApi(Build.VERSION_CODES.O)
    @Composable
    override fun Content() {
        val vm: DraftScreenContract.ViewModel = getViewModel<DraftDetailsViewModelImpl>()
        DraftDetailsContent(list, vm::onEventDispatcher)
    }
}

@RequiresApi(Build.VERSION_CODES.O)
@Composable
fun DraftDetailsContent(
    list: List<ComponentData>,
    onEventDispatchers: (DraftScreenContract.Intent) -> Unit,
) {
    Box(modifier = Modifier.fillMaxSize()) {
        Box(
            modifier = Modifier
                .background(Color(0xFFFF3951))
                .height(56.dp)
                .fillMaxWidth()
        ) {
            Text(
                text = "Draft Details",
                modifier = Modifier.align(Alignment.Center),
                color = Color.White,
                fontWeight = FontWeight.Bold,
                fontSize = 30.sp

            )
            Icon(
                painter = painterResource(id = R.drawable.baseline_arrow_back_ios_24),
                contentDescription = "backHome", modifier = Modifier
                    .padding(start = 20.dp)
                    .padding(top = 10.dp)
                    .size(30.dp)
                    .clickable {
                        onEventDispatchers.invoke(DraftScreenContract.Intent.Back)
                    }, tint = Color.White
            )
        }
        Column(
            modifier = Modifier
                .fillMaxSize()
        ) {

            Box(modifier = Modifier.fillMaxSize()) {
                if (list.isEmpty()) {
                    Text(
                        text = "There is no components yet!",
                        fontSize = 22.sp,
                        modifier = Modifier.align(Alignment.Center)
                    )

                } else {
                    Column(modifier = Modifier.padding(horizontal = 15.dp)) {
                        Text(
                            text = "Components",
                            modifier = Modifier
                                .padding(top = 15.dp)
                                .align(Alignment.CenterHorizontally),
                            fontWeight = FontWeight.Black,
                            fontSize = 25.sp
                        )
                        LazyColumn(
                            modifier = Modifier
                                .fillMaxWidth()
                                .wrapContentHeight()
                                .padding(top = 10.dp)
                        ) {
                            list.forEach { data ->
                                when (data.type) {
                                    ComponentEnum.Spinner -> {
                                        item {
                                            SampleSpinnerPreview(
                                                list = data.variants ?: listOf(),
                                                preselected = data.variants[0] ?: "",
                                                onSelectionChanged = {


                                                },
                                                content = data.content,
                                                componentData = data
                                            ) {
                                            }

                                        }
                                    }

                                    ComponentEnum.Selector -> {
                                        item {

                                            Column {
                                                SelectorItem(
                                                    question = data.content,
                                                    list = data.variants,
                                                    componentData = data,
                                                    onSaveStates = {

                                                    }
                                                ) {

                                                }
                                            }
                                        }
                                    }

                                    ComponentEnum.SampleText -> {
                                        item {


                                            Row(
                                                modifier = Modifier
                                                    .then(
                                                        if (data.isVisible) Modifier
                                                            .fillMaxWidth()
                                                            .clip(RoundedCornerShape(12.dp))
                                                            .border(
                                                                1.dp,
                                                                Color(0xFFFF7686),
                                                                RoundedCornerShape(12.dp)
                                                            )
                                                            .background(Color(0x33C4C4C4))
                                                            .padding(
                                                                horizontal = 16.dp,
                                                                vertical = 5.dp
                                                            )
                                                        else Modifier.size(0.dp)
                                                    ),
                                                verticalAlignment = Alignment.CenterVertically
                                            ) {
                                                Text(
                                                    text = data.content,
                                                    fontSize = 22.sp,
                                                    modifier = Modifier
                                                        .padding(bottom = 10.dp)
                                                )
                                            }
                                            Spacer(modifier = Modifier.height(16.dp))

                                        }
                                    }

                                    ComponentEnum.Input -> {
                                        item {

                                            var inputVal by remember {
                                                mutableStateOf(data.enteredValue)
                                            }

                                            Column(modifier = Modifier.fillMaxWidth()) {
                                                Spacer(modifier = Modifier.size(10.dp))
                                                Log.d(
                                                    "DDD",
                                                    "MainScreenContent: ${data.isRequired}"
                                                )
                                                if (data.isRequired) {
                                                    Text(
                                                        text = "This Field is required",
                                                        fontWeight = FontWeight(600),
                                                        color = Color(0xFFff7686)
                                                    )
                                                }
                                                Spacer(modifier = Modifier.size(10.dp))
                                                InputField(onEdit = {

                                                    if (data.operators.isNotEmpty()) {

                                                    }

                                                }, componentData = data)
                                            }

                                        }
                                    }

                                    ComponentEnum.Dater -> {
                                        item {

                                            DatePickerPreview(
                                                componentData = data,
                                                content = data.content
                                            ) {

                                                if (data.operators.isNotEmpty()) {

                                                }
                                            }
                                        }
                                    }
                                }
                            }
                        }
                    }
                    Row(modifier = Modifier.fillMaxWidth()) {
                        Button(
                            onClick = {  },
                            modifier = Modifier
                                .background(Color.White),
                            colors = ButtonDefaults.buttonColors(containerColor = Color(0xFFFA1466))
                        ) {
                            Text(text = "Cancel", color = Color(0xFFFFFFFF))
                        }
                        Spacer(modifier = Modifier.weight(1f))
                        Button(
                            onClick = {  },
                            modifier = Modifier
                                .background(Color.White),
                            colors = ButtonDefaults.buttonColors(containerColor = Color(0xFFFA1466))
                        ) {
                            Text(text = "Delete", color = Color(0xFFFFFFFF))
                        }
                    }
                }
            }
        }
    }

}





@RequiresApi(Build.VERSION_CODES.O)
@Preview(showBackground = true)
@Composable
fun DraftDetailsPreview() {
    DraftDetailsContent(emptyList(),onEventDispatchers = {})
}