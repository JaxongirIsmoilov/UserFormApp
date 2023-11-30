package uz.gita.jaxongir.userformapp.presenter.main

import android.annotation.SuppressLint
import android.os.Build
import android.util.Log
import android.widget.Toast
import androidx.annotation.RequiresApi
import androidx.compose.foundation.BorderStroke
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
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
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
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import cafe.adriel.voyager.androidx.AndroidScreen
import cafe.adriel.voyager.hilt.getViewModel
import com.google.accompanist.systemuicontroller.rememberSystemUiController
import uz.gita.jaxongir.userformapp.data.enums.ComponentEnum
import uz.gita.jaxongir.userformapp.data.local.pref.MyPref
import uz.gita.jaxongir.userformapp.data.local.room.entity.FormEntity
import uz.gita.jaxongir.userformapp.ui.components.DatePickerPreview
import uz.gita.jaxongir.userformapp.ui.components.ImageComponent
import uz.gita.jaxongir.userformapp.ui.components.InputField
import uz.gita.jaxongir.userformapp.ui.components.SampleSpinnerPreview
import uz.gita.jaxongir.userformapp.ui.components.SelectorItem
import uz.gita.jaxongir.userformapp.utills.myLog
import javax.inject.Inject

class MainScreen @Inject constructor(val myPref: MyPref) : AndroidScreen() {

    @RequiresApi(Build.VERSION_CODES.O)
    @Composable
    override fun Content() {
        val vm: MainContract.ViewModel = getViewModel<MainViewModel>()
        vm.onEventDispatcher(MainContract.Intent.LoadList)
        MainScreenContent(vm.uiState.collectAsState(), vm::onEventDispatcher, myPref)

    }
}

@RequiresApi(Build.VERSION_CODES.O)
@Composable
fun MainScreenContent(
    uiState: State<MainContract.UIState>,
    onEventDispatchers: (MainContract.Intent) -> Unit,
    myPref: MyPref
) {
    val context = LocalContext.current
    val systemUiController = rememberSystemUiController()
    systemUiController.setStatusBarColor(color = Color(0xFFFF3951))
    var shouldShowError by remember {
        mutableStateOf(false)
    }
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
            }

            Box(modifier = Modifier.fillMaxSize()) {
                if (uiState.value.components.isEmpty()) {

                    Text(
                        text = "There is no components yet!",
                        fontSize = 22.sp,
                        modifier = Modifier.align(Alignment.Center)
                    )

                } else {
                    Column(modifier = Modifier.padding(horizontal = 15.dp)) {
                        Text(
                            text = "Fill these sheets",
                            modifier = Modifier
                                .padding(top = 15.dp)
                                .align(Alignment.CenterHorizontally),
                            fontWeight = FontWeight.Normal,
                            fontSize = 25.sp
                        )
                        LazyColumn(
                            modifier = Modifier
                                .fillMaxWidth()
                                .wrapContentHeight()
                                .padding(top = 10.dp)
                        ) {
                            uiState.value.components.forEach { data ->
                                when (data.type) {
                                    ComponentEnum.Spinner -> {
                                        item {
                                            SampleSpinnerPreview(
                                                list = data.variants ?: listOf(),
                                                preselected = data.variants[0] ?: "",

                                                onSelectionChanged = {
                                                    onEventDispatchers.invoke(
                                                        MainContract.Intent.UpdateComponent(
                                                            data.copy(selectedSpinnerText = it)
                                                        )
                                                    )
                                                    if (data.operators.isNotEmpty()) {
                                                        myLog("spinner compo")
                                                        onEventDispatchers.invoke(
                                                            MainContract.Intent.CheckedComponent(
                                                                data
                                                            )
                                                        )
                                                    }
                                                },
                                                content = data.content,
                                                componentData = data,
                                                {},
                                                modifier = Modifier.fillMaxWidth(),
                                                true,
                                                isDraft = false
                                            )

                                        }
                                    }

                                    ComponentEnum.Selector -> {
                                        item {
                                            onEventDispatchers.invoke(
                                                MainContract.Intent.CheckedComponent(
                                                    data
                                                )
                                            )
                                            Column {
                                                SelectorItem(
                                                    question = data.content,
                                                    list = data.variants,
                                                    componentData = data,
                                                    onSaveStates = {
                                                        onEventDispatchers.invoke(
                                                            MainContract.Intent.UpdateComponent(
                                                                componentData = data.copy(selected = it)
                                                            )
                                                        )
                                                    },
                                                    deleteComp = {
                                                        onEventDispatchers.invoke(
                                                            MainContract.Intent.UpdateComponent(
                                                                data.copy(enteredValue = "")
                                                            )
                                                        )
                                                        if (data.operators.isNotEmpty()) {
                                                            onEventDispatchers.invoke(
                                                                MainContract.Intent.CheckedComponent(
                                                                    data
                                                                )
                                                            )
                                                        }
                                                    },
                                                    isEnable = true,
                                                    modifier = Modifier.fillMaxWidth(),
                                                    isInDraft = false
                                                )
                                            }
                                        }
                                    }

                                    ComponentEnum.SampleText -> {
                                        item {
                                            myLog("sample text")
                                            if (data.operators.isNotEmpty()) {
                                                onEventDispatchers.invoke(
                                                    MainContract.Intent.CheckedComponent(
                                                        data
                                                    )
                                                )
                                            }

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
                                            onEventDispatchers.invoke(
                                                MainContract.Intent.CheckedComponent(
                                                    data
                                                )
                                            )

                                            Column(modifier = Modifier.fillMaxWidth()) {
                                                Spacer(modifier = Modifier.size(10.dp))
                                                Log.d(
                                                    "DDD",
                                                    "MainScreenContent: ${data.isRequired}"
                                                )
                                                if (data.isRequired) {
                                                    if (data.enteredValue == "") {
                                                        shouldShowError = true
                                                    }
                                                    Text(
                                                        text = "This Field is required",
                                                        fontWeight = FontWeight(600),
                                                        color = Color(0xFFff7686)
                                                    )
                                                }
                                                Spacer(modifier = Modifier.size(10.dp))
                                                InputField(
                                                    onEdit = {
                                                        onEventDispatchers.invoke(
                                                            MainContract.Intent.UpdateComponent(
                                                                data.copy(enteredValue = it)
                                                            )
                                                        )
                                                        if (data.operators.isNotEmpty()) {
                                                            onEventDispatchers.invoke(
                                                                MainContract.Intent.CheckedComponent(
                                                                    data
                                                                )
                                                            )
                                                        }

                                                    },
                                                    componentData = data,
                                                    isEnable = true,
                                                    modifier = Modifier.fillMaxWidth(),
                                                    isInDraft = false
                                                )
                                            }

                                        }
                                    }

                                    ComponentEnum.Dater -> {
                                        item {
                                            onEventDispatchers.invoke(
                                                MainContract.Intent.CheckedComponent(
                                                    data
                                                )
                                            )
                                            DatePickerPreview(
                                                componentData = data,
                                                content = data.content,
                                                modifier = Modifier.fillMaxWidth(),
                                                isEnable = true
                                            ) {
                                                onEventDispatchers.invoke(
                                                    MainContract.Intent.UpdateComponent(
                                                        data.copy(enteredValue = "asdfdsa")
                                                    )
                                                )
                                                if (data.operators.isNotEmpty()) {
                                                    onEventDispatchers.invoke(
                                                        MainContract.Intent.CheckedComponent(
                                                            data
                                                        )
                                                    )
                                                }
                                            }
                                        }
                                    }

                                    ComponentEnum.Image -> {
                                        item {
                                            ImageComponent(data = data, true)
                                        }
                                    }

                                    ComponentEnum.LazyRow -> {
                                        item {
                                            Card(
                                                modifier = Modifier
                                                    .fillMaxWidth()
                                                    .height(80.dp)
                                                    .clip(RoundedCornerShape(12.dp)),
                                                colors = CardDefaults.cardColors(),
                                                border = BorderStroke(
                                                    2.dp,
                                                    color = Color(0xFFFF3951)
                                                )
                                            ) {
                                                Row(modifier = Modifier.fillMaxSize()) {
                                                    onEventDispatchers.invoke(
                                                        MainContract.Intent.GetAllRowItems(
                                                            data.rowId
                                                        )
                                                    )
                                                    uiState.value.rowComponenets.forEach { data ->
                                                        when (data.type) {
                                                            ComponentEnum.Spinner -> {
                                                                SampleSpinnerPreview(
                                                                    list = data.variants
                                                                        ?: listOf(),
                                                                    preselected = data.variants[0]
                                                                        ?: "",

                                                                    onSelectionChanged = {
                                                                        onEventDispatchers.invoke(
                                                                            MainContract.Intent.UpdateComponent(
                                                                                data.copy(
                                                                                    selectedSpinnerText = it
                                                                                )
                                                                            )
                                                                        )
                                                                        if (data.operators.isNotEmpty()) {
                                                                            myLog("spinner compo")
                                                                            onEventDispatchers.invoke(
                                                                                MainContract.Intent.CheckedComponent(
                                                                                    data
                                                                                )
                                                                            )
                                                                        }
                                                                    },
                                                                    content = data.content,
                                                                    componentData = data,
                                                                    {},
                                                                    modifier = Modifier.weight(data.weight.toFloat()),
                                                                    true,
                                                                    isDraft = false
                                                                )

                                                            }

                                                            ComponentEnum.Selector -> {
                                                                onEventDispatchers.invoke(
                                                                    MainContract.Intent.CheckedComponent(
                                                                        data
                                                                    )
                                                                )
                                                                Column {
                                                                    SelectorItem(
                                                                        question = data.content,
                                                                        list = data.variants,
                                                                        componentData = data,
                                                                        onSaveStates = {
                                                                            onEventDispatchers.invoke(
                                                                                MainContract.Intent.UpdateComponent(
                                                                                    componentData = data.copy(
                                                                                        selected = it
                                                                                    )
                                                                                )
                                                                            )
                                                                        },
                                                                        deleteComp = {
                                                                            onEventDispatchers.invoke(
                                                                                MainContract.Intent.UpdateComponent(
                                                                                    data.copy(
                                                                                        enteredValue = ""
                                                                                    )
                                                                                )
                                                                            )
                                                                            if (data.operators.isNotEmpty()) {
                                                                                onEventDispatchers.invoke(
                                                                                    MainContract.Intent.CheckedComponent(
                                                                                        data
                                                                                    )
                                                                                )
                                                                            }
                                                                        },
                                                                        isEnable = true,
                                                                        modifier = Modifier.weight(data.weight.toFloat()),
                                                                        isInDraft = false
                                                                    )
                                                                }
                                                            }

                                                            ComponentEnum.SampleText -> {
                                                                myLog("sample text")
                                                                if (data.operators.isNotEmpty()) {
                                                                    onEventDispatchers.invoke(
                                                                        MainContract.Intent.CheckedComponent(
                                                                            data
                                                                        )
                                                                    )
                                                                }

                                                                Row(
                                                                    modifier = Modifier
                                                                        .then(
                                                                            if (data.isVisible) Modifier
                                                                                .fillMaxWidth()
                                                                                .clip(
                                                                                    RoundedCornerShape(
                                                                                        12.dp
                                                                                    )
                                                                                )
                                                                                .border(
                                                                                    1.dp,
                                                                                    Color(0xFFFF7686),
                                                                                    RoundedCornerShape(
                                                                                        12.dp
                                                                                    )
                                                                                )
                                                                                .background(
                                                                                    Color(
                                                                                        0x33C4C4C4
                                                                                    )
                                                                                )
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

                                                            ComponentEnum.Input -> {
                                                                onEventDispatchers.invoke(
                                                                    MainContract.Intent.CheckedComponent(
                                                                        data
                                                                    )
                                                                )

                                                                Column(modifier = Modifier.fillMaxWidth()) {
                                                                    Spacer(
                                                                        modifier = Modifier.size(
                                                                            10.dp
                                                                        )
                                                                    )
                                                                    Log.d(
                                                                        "DDD",
                                                                        "MainScreenContent: ${data.isRequired}"
                                                                    )
                                                                    if (data.isRequired) {
                                                                        if (data.enteredValue == "") {
                                                                            shouldShowError =
                                                                                true
                                                                        }
                                                                        Text(
                                                                            text = "This Field is required",
                                                                            fontWeight = FontWeight(
                                                                                600
                                                                            ),
                                                                            color = Color(
                                                                                0xFFff7686
                                                                            )
                                                                        )
                                                                    }
                                                                    Spacer(
                                                                        modifier = Modifier.size(
                                                                            10.dp
                                                                        )
                                                                    )
                                                                    InputField(
                                                                        onEdit = {
                                                                            onEventDispatchers.invoke(
                                                                                MainContract.Intent.UpdateComponent(
                                                                                    data.copy(
                                                                                        enteredValue = it
                                                                                    )
                                                                                )
                                                                            )
                                                                            if (data.operators.isNotEmpty()) {
                                                                                onEventDispatchers.invoke(
                                                                                    MainContract.Intent.CheckedComponent(
                                                                                        data
                                                                                    )
                                                                                )
                                                                            }

                                                                        },
                                                                        componentData = data,
                                                                        isEnable = true,
                                                                        modifier = Modifier.fillMaxWidth(),
                                                                        isInDraft = false
                                                                    )
                                                                }
                                                            }

                                                            ComponentEnum.Dater -> {
                                                                onEventDispatchers.invoke(
                                                                    MainContract.Intent.CheckedComponent(
                                                                        data
                                                                    )
                                                                )
                                                                DatePickerPreview(
                                                                    componentData = data,
                                                                    content = data.content,
                                                                    modifier = Modifier.fillMaxWidth(),
                                                                    isEnable = true
                                                                ) {
                                                                    onEventDispatchers.invoke(
                                                                        MainContract.Intent.UpdateComponent(
                                                                            data.copy(
                                                                                enteredValue = "asdfdsa"
                                                                            )
                                                                        )
                                                                    )
                                                                    if (data.operators.isNotEmpty()) {
                                                                        onEventDispatchers.invoke(
                                                                            MainContract.Intent.CheckedComponent(
                                                                                data
                                                                            )
                                                                        )
                                                                    }
                                                                }
                                                            }

                                                            ComponentEnum.Image -> {
                                                                ImageComponent(
                                                                    data = data,
                                                                    true
                                                                )
                                                            }

                                                            else -> {}
                                                        }

                                                    }
                                                }
                                            }
                                        }
                                    }
                                }
                            }
                            item {
                                Row(
                                    modifier = Modifier
                                        .padding(top = 10.dp)
                                        .fillMaxWidth()
                                        .height(80.dp)
                                        .align(Alignment.CenterHorizontally)
                                ) {
                                    Spacer(modifier = Modifier.weight(1f))
                                    Button(
                                        colors = ButtonDefaults.buttonColors(
                                            containerColor = Color(
                                                0xFFFA1466
                                            )
                                        ), onClick = {
                                            onEventDispatchers.invoke(
                                                MainContract.Intent.ClickAsDraft(
                                                    FormEntity(
                                                        id = 0,
                                                        uiState.value.components,
                                                        isDraft = true,
                                                        isSubmitted = false,
                                                        myPref.getId(),
                                                        date = uiState.value.date
                                                    ), context
                                                )
                                            )
                                        }) {
                                        Text(text = "Save As Draft")
                                    }
                                    Spacer(modifier = Modifier.weight(1f))
                                    Button(
                                        colors = ButtonDefaults.buttonColors(
                                            containerColor = Color(
                                                0xFFFA1466
                                            )
                                        ), onClick = {
                                            if (!shouldShowError) {
                                                onEventDispatchers.invoke(
                                                    MainContract.Intent.ClickAsSaved(
                                                        FormEntity(
                                                            id = 0,
                                                            listComponents = uiState.value.components,
                                                            isDraft = false,
                                                            isSubmitted = true,
                                                            myPref.getId(),
                                                            date = uiState.value.date
                                                        ), context
                                                    )
                                                )
                                            } else {
                                                Toast.makeText(
                                                    context,
                                                    "Check required fields",
                                                    Toast.LENGTH_SHORT
                                                ).show()
                                            }

                                        }) {
                                        Text(text = "Save as Saved")
                                    }
                                    Spacer(modifier = Modifier.weight(1f))
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
//    MainScreenContent(uiState = mutableStateOf(MainContract.UIState())) {}
}