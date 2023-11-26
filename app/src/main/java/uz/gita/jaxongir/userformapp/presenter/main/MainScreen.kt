package uz.gita.jaxongir.userformapp.presenter.main

import android.annotation.SuppressLint
import android.os.Build
import androidx.annotation.RequiresApi
import androidx.compose.foundation.background
import androidx.compose.foundation.border
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
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.State
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.mutableStateOf
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
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
import com.google.accompanist.systemuicontroller.rememberSystemUiController
import uz.gita.jaxongir.userformapp.R
import uz.gita.jaxongir.userformapp.data.enums.ComponentEnum
import uz.gita.jaxongir.userformapp.ui.components.DatePickerPreview
import uz.gita.jaxongir.userformapp.ui.components.InputField
import uz.gita.jaxongir.userformapp.ui.components.SampleSpinnerPreview
import uz.gita.jaxongir.userformapp.ui.components.SelectorItem
import uz.gita.jaxongir.userformapp.utills.myLog

class MainScreen : AndroidScreen() {
    @RequiresApi(Build.VERSION_CODES.O)
    @Composable
    override fun Content() {
        val vm: MainContract.ViewModel = getViewModel<MainViewModel>()
        vm.onEventDispatcher(MainContract.Intent.LoadList)
        MainScreenContent(vm.uiState.collectAsState(), vm::onEventDispatcher)

    }
}

@RequiresApi(Build.VERSION_CODES.O)
@Composable
fun MainScreenContent(
    uiState: State<MainContract.UIState>,
    onEventDispatchers: (MainContract.Intent) -> Unit,
) {
    val systemUiController = rememberSystemUiController()
    systemUiController.setStatusBarColor(color = Color(0xFFFF3951))
    if (uiState.value.components.isEmpty()) {

    } else {
        Box(modifier = Modifier.fillMaxSize()) {
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
                    Text(
                        text = "User: ${uiState.value.userName}",
                        style = TextStyle(
                            fontSize = 24.sp,
                            fontStyle = FontStyle.Normal,
                            fontWeight = FontWeight.Bold
                        ),
                        modifier = Modifier
                            .align(Alignment.CenterStart)
                            .padding(start = 16.dp),
                        color = Color.White
                    )

                    IconButton(
                        modifier = Modifier
                            .align(Alignment.CenterEnd),
                        onClick = {
                            onEventDispatchers.invoke(MainContract.Intent.Logout)
                        }
                    ) {
                        Icon(
                            painter = painterResource(id = R.drawable.ic_logout),
                            contentDescription = null,
                            modifier = Modifier.size(36.dp),
                            tint = Color.White
                        )
                    }
                }

                Box(modifier = Modifier.fillMaxSize()) {
                    myLog("list size screen:${uiState.value.components.size}")
                    if (uiState.value.components.isEmpty()) {
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
                                    .padding(top = 55.dp)
                            ) {
                                uiState.value.components.forEach { data ->
                                    when (data.type) {
                                        ComponentEnum.Spinner -> {
                                            item {
//                                                if (uiState.value.checkedComponent?.enteredValue == data.conditions.first().value) {
                                                    SampleSpinnerPreview(
                                                        list = data.variants,
                                                        preselected = data.variants[0],
                                                        onSelectionChanged = {
                                                            onEventDispatchers.invoke(MainContract.Intent.UpdateComponent(
                                                                data.copy(enteredValue = it)
                                                            ))
                                                        },
                                                        content = data.content,
                                                        componentData = data
                                                    ) {

                                                    }
//                                                }
                                            }
                                        }

                                        ComponentEnum.Selector -> {
                                            item {
                                                SelectorItem(
                                                    question = data.content,
                                                    list = data.variants,
                                                    componentData = data
                                                ) {

                                                }
                                            }
                                        }

                                        ComponentEnum.SampleText -> {
                                            item {
                                                Row(
                                                    modifier = Modifier
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
                                                InputField(
                                                    textFieldType = data.textFieldType,
                                                    maxLines = data.maxLines,
                                                    maxLength = data.maxLength,
                                                    minLength = data.minLength,
                                                    maxValue = data.maxValue,
                                                    minValue = data.minValue,
                                                    question = data.content,
                                                    componentData = data, {}
                                                )
                                            }
                                        }

                                        ComponentEnum.Dater -> {
                                            item {
                                                DatePickerPreview(
                                                    componentData = data,
                                                    content = data.content
                                                ) {

                                                }
                                            }
                                        }
                                    }
                                }
                            }
                        }
                    }
                }
            }
        }
    }
}

@RequiresApi(Build.VERSION_CODES.O)
@SuppressLint("UnrememberedMutableState")
@Preview(showBackground = true)
@Composable
fun MainScreenPreview() {
    MainScreenContent(uiState = mutableStateOf(MainContract.UIState())) {}
}