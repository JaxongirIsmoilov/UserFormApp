package uz.gita.jaxongir.userformapp.presenter.main

import android.net.Uri
import android.os.Build
import android.util.Log
import androidx.annotation.RequiresApi
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.aspectRatio
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.wrapContentHeight
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.OutlinedTextFieldDefaults
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
import androidx.compose.ui.platform.LocalConfiguration
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.platform.LocalDensity
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.core.graphics.blue
import androidx.core.graphics.green
import androidx.core.graphics.red
import cafe.adriel.voyager.androidx.AndroidScreen
import cafe.adriel.voyager.hilt.getViewModel
import coil.compose.AsyncImage
import com.google.accompanist.systemuicontroller.rememberSystemUiController
import uz.gita.jaxongir.userformapp.R
import uz.gita.jaxongir.userformapp.data.enums.ComponentEnum
import uz.gita.jaxongir.userformapp.data.enums.ImageTypeEnum
import uz.gita.jaxongir.userformapp.data.local.pref.MyPref
import uz.gita.jaxongir.userformapp.ui.components.DatePickerPreview
import uz.gita.jaxongir.userformapp.ui.components.InputField
import uz.gita.jaxongir.userformapp.ui.components.SampleSpinnerPreview
import uz.gita.jaxongir.userformapp.ui.components.SelectorItem
import uz.gita.jaxongir.userformapp.ui.components.TextComponent
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
    val density = LocalDensity.current
    val weight = LocalConfiguration.current.screenWidthDp
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
                                        if (data.rowId == "") {
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
                                    }

                                    ComponentEnum.Selector -> {
                                        if (data.rowId==""){
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

                                    }

                                    ComponentEnum.SampleText -> {
                                        if (data.rowId == ""){
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

                                    }

                                    ComponentEnum.Input -> {
                                        if (data.rowId == ""){
                                            item {
                                                onEventDispatchers.invoke(
                                                    MainContract.Intent.CheckedComponent(
                                                        data
                                                    )
                                                )

                                                if (data.rowId == "") {
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
                                        }
                                    }

                                    ComponentEnum.Dater -> {
                                       if (data.rowId ==""){
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
                                    }

                                    ComponentEnum.Image -> {
                                        if (data.rowId == "") {
                                            val height = when (data.customHeight) {
                                                "w/3" -> {
                                                    with(density) { weight.dp / 3 }
                                                }

                                                "w/2" -> {
                                                    with(density) { weight.dp / 2 }
                                                }

                                                "w" -> {
                                                    with(density) { weight.dp }
                                                }

                                                "2w" -> {
                                                    with(density) { weight.dp * 2 }
                                                }

                                                else -> {
                                                    0.dp
                                                }

                                            }
                                            item {
                                                if (data.imageType == ImageTypeEnum.GALLERY) {
                                                    Box(
                                                        modifier = Modifier
                                                            .fillMaxWidth()
                                                            .background(
                                                                color = Color(
                                                                    data.backgroundColor.red,
                                                                    data.backgroundColor.green,
                                                                    data.backgroundColor.blue

                                                                )
                                                            )
                                                    )
                                                    {
                                                        AsyncImage(
                                                            model = data.imgUri,
                                                            contentDescription = null,
                                                            modifier = Modifier
                                                                .then(
                                                                    if (data.ratioX != 0) {
                                                                        Modifier.aspectRatio(data.ratioX.toFloat() / data.ratioY.toFloat())
                                                                    } else if (data.customHeight != "") {
                                                                        Modifier.height(height = height)
                                                                    } else {
                                                                        Modifier
                                                                    }
                                                                )
                                                        )
                                                    }
                                                } else {
                                                    var uri by remember {
                                                        mutableStateOf("")
                                                    }
                                                    Column(
                                                        Modifier
                                                            .fillMaxWidth()
                                                            .background(
                                                                color = Color(
                                                                    data.backgroundColor.red,
                                                                    data.backgroundColor.green,
                                                                    data.backgroundColor.blue
                                                                )
                                                            )
                                                    ) {
                                                        OutlinedTextField(
                                                            value = "",
                                                            onValueChange = {
                                                                uri = it
                                                            },
                                                            singleLine = true,
                                                            label = {
                                                                Text(text = "Rasm Uri kiriting")
                                                            },
                                                            modifier = Modifier.fillMaxWidth(),
                                                            colors = OutlinedTextFieldDefaults.colors(
                                                                focusedBorderColor = Color(
                                                                    0xFFFF3951
                                                                ),
                                                                unfocusedBorderColor = Color(
                                                                    0xFFFF7686
                                                                )
                                                            ),
                                                            maxLines = 1,
                                                        )

                                                        AsyncImage(
                                                            model = Uri.parse(uri),
                                                            contentDescription = null,
                                                            modifier = Modifier
                                                                .then(
                                                                    if (data.ratioX != 0) {
                                                                        Modifier.aspectRatio(data.ratioX.toFloat() / data.ratioY.toFloat())
                                                                    } else if (data.customHeight != "") {
                                                                        Modifier.height(height = height)
                                                                    } else {
                                                                        Modifier
                                                                    }
                                                                ),
                                                            error = painterResource(id = R.drawable.cats)
                                                        )
                                                    }
                                                }
                                            }

                                        }
                                    }

                                    ComponentEnum.LazyRow -> {
                                        item {
                                            Row(modifier = Modifier.fillMaxWidth()) {
                                                uiState.value.components.filter {
                                                    it.rowId == data.idEnteredByUser
                                                }.forEach { dta ->
                                                    when (dta.type) {
                                                        ComponentEnum.Selector -> {
                                                            Box(modifier = Modifier.weight(dta.weight.toFloat())) {
                                                                SelectorItem(
                                                                    question = dta.content,
                                                                    list = dta.variants,
                                                                    componentData = dta,
                                                                    modifier = Modifier,
                                                                    onSaveStates = {
                                                                        onEventDispatchers.invoke(
                                                                            MainContract.Intent.UpdateComponent(
                                                                                componentData = dta.copy(
                                                                                    selected = it
                                                                                )
                                                                            )
                                                                        )
                                                                    },
                                                                    deleteComp = {
                                                                        onEventDispatchers.invoke(
                                                                            MainContract.Intent.UpdateComponent(
                                                                                dta.copy(
                                                                                    enteredValue = ""
                                                                                )
                                                                            )
                                                                        )
                                                                        if (data.operators.isNotEmpty()) {
                                                                            onEventDispatchers.invoke(
                                                                                MainContract.Intent.CheckedComponent(
                                                                                    dta
                                                                                )
                                                                            )
                                                                        }
                                                                    },
                                                                    isEnable = true,
                                                                    isInDraft = false
                                                                )
                                                            }
                                                        }

                                                        ComponentEnum.SampleText -> {
                                                            Box(modifier = Modifier.weight(dta.weight.toFloat())) {
                                                                TextComponent(
                                                                    componentData = dta
                                                                )
                                                                Spacer(modifier = Modifier.height(10.dp))
                                                            }
                                                        }

                                                        ComponentEnum.Spinner -> {
                                                            Box(modifier = Modifier.weight(dta.weight.toFloat())) {
                                                                SampleSpinnerPreview(
                                                                    list = dta.variants,
                                                                    preselected = dta.variants[0],
                                                                    onSelectionChanged = {
                                                                        onEventDispatchers.invoke(
                                                                            MainContract.Intent.UpdateComponent(
                                                                                dta.copy(
                                                                                    selectedSpinnerText = it
                                                                                )
                                                                            )
                                                                        )
                                                                        if (dta.operators.isNotEmpty()) {
                                                                            myLog("spinner compo")
                                                                            onEventDispatchers.invoke(
                                                                                MainContract.Intent.CheckedComponent(
                                                                                    dta
                                                                                )
                                                                            )
                                                                        }
                                                                    },
                                                                    content = dta.content,
                                                                    componentData = dta,
                                                                    {},
                                                                    modifier = Modifier,
                                                                    true,
                                                                    isDraft = false
                                                                )
                                                            }
                                                        }

                                                        ComponentEnum.Input -> {
                                                            Box(modifier = Modifier.weight(dta.weight.toFloat())) {
                                                                InputField(
                                                                    onEdit = {
                                                                        onEventDispatchers.invoke(
                                                                            MainContract.Intent.UpdateComponent(
                                                                                dta.copy(
                                                                                    enteredValue = it
                                                                                )
                                                                            )
                                                                        )
                                                                        if (data.operators.isNotEmpty()) {
                                                                            onEventDispatchers.invoke(
                                                                                MainContract.Intent.CheckedComponent(
                                                                                    dta
                                                                                )
                                                                            )
                                                                        }

                                                                    },
                                                                    componentData = dta,
                                                                    isEnable = true,
                                                                    modifier = Modifier.fillMaxWidth(),
                                                                    isInDraft = false
                                                                )
                                                            }
                                                        }

                                                        ComponentEnum.Dater -> {
                                                            onEventDispatchers.invoke(
                                                                MainContract.Intent.CheckedComponent(
                                                                    dta
                                                                )
                                                            )
                                                            Box(modifier = Modifier.weight(dta.weight.toFloat())) {
                                                                DatePickerPreview(
                                                                    componentData = dta,
                                                                    content = dta.content,
                                                                    modifier = Modifier,
                                                                    isEnable = true
                                                                ) {
                                                                    onEventDispatchers.invoke(
                                                                        MainContract.Intent.UpdateComponent(
                                                                            dta.copy(enteredValue = "asdfdsa")
                                                                        )
                                                                    )
                                                                    if (dta.operators.isNotEmpty()) {
                                                                        onEventDispatchers.invoke(
                                                                            MainContract.Intent.CheckedComponent(
                                                                                dta
                                                                            )
                                                                        )
                                                                    }
                                                                }
                                                            }
                                                        }

                                                        else -> {

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
    }
}