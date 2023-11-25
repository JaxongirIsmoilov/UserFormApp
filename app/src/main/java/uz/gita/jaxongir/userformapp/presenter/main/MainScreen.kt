package uz.gita.jaxongir.userformapp.presenter.main

import android.annotation.SuppressLint
import android.os.Build
import android.util.Log
import android.widget.DatePicker
import androidx.annotation.RequiresApi
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.wrapContentHeight
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.State
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
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
import org.checkerframework.checker.units.qual.m
import uz.gita.jaxongir.userformapp.R
import uz.gita.jaxongir.userformapp.data.enums.ComponentEnum
import uz.gita.jaxongir.userformapp.ui.components.InputField
import uz.gita.jaxongir.userformapp.ui.components.MyDatePicker
import uz.gita.jaxongir.userformapp.ui.components.MySampleSpinner
import uz.gita.jaxongir.userformapp.ui.components.SelectorItem
import uz.gita.jaxongir.userformapp.ui.theme.Purple80

class MainScreen() : AndroidScreen() {

    @RequiresApi(Build.VERSION_CODES.O)
    @Composable
    override fun Content() {
        val vm: MainContract.ViewModel = getViewModel<MainViewModel>()
        MainScreenContent(vm.uiState.collectAsState(), vm::onEventDispatcher)
        //
    }
}

@RequiresApi(Build.VERSION_CODES.O)
@Composable
fun MainScreenContent(
    uiState: State<MainContract.UIState>,
    onEventDispatchers: (MainContract.Intent) -> Unit
) {
    Column(modifier = Modifier.fillMaxSize()) {
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

        Log.d("TTT", "MainScreenContent: ${uiState.value.components}")

        if (uiState.value.loading) {

            CircularProgressIndicator(
                modifier = Modifier.padding(top = 8.dp).align(Alignment.CenterHorizontally),
                color = Purple80,
                strokeWidth = 4.dp
            )

        } else {
        LazyColumn(
            modifier = Modifier
                .fillMaxWidth()
                .wrapContentHeight()
                .padding(top = 8.dp)
        ) {
            val components = uiState.value.components
            components.forEach { component ->
                if (component.userId == uiState.value.userId) {
                    when (component.type) {
                        ComponentEnum.SampleText -> {
                            item {
                                Text(text = component.content)
                            }
                        }

                        ComponentEnum.Dater -> {
                            item {
                                MyDatePicker(content = component.content)
                            }
                        }

                        ComponentEnum.Input -> {
                            item {
                                InputField(textFieldType = component.textFieldType, maxLines = if (component.maxLines != 0) component.maxLines else 10, maxLength = if (component.maxLength != 0) component.maxLength else 100, minLength = if (component.minLength == 0) 0 else component.minLength, maxValue = if (component.maxValue != 0) component.maxLines else 999999999, minValue = if (component.minValue == 0) 0 else component.minValue, question = "")
                            }
                        }

                        ComponentEnum.Selector -> {
                            item {
                                SelectorItem(
                                    question = component.content,
                                    list = component.variants
                                )
                            }
                        }

                        ComponentEnum.Spinner -> {
                            item {
                                var selected by remember { mutableStateOf(if (component.variants.isNotEmpty()) component.variants[0] else "Spinner") }

                                MySampleSpinner(list = component.variants, preselected = selected, onSelectionChanged = {
                                    selected = it
                                }, content = component.content)
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