package uz.gita.jaxongir.userformapp.presenter.drafts_detail

import android.annotation.SuppressLint
import android.net.Uri
import android.os.Build
import android.widget.Toast
import androidx.annotation.RequiresApi
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.aspectRatio
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
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
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalConfiguration
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.platform.LocalDensity
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
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
import uz.gita.jaxongir.userformapp.data.model.ComponentData
import uz.gita.jaxongir.userformapp.ui.components.DatePickerPreview
import uz.gita.jaxongir.userformapp.ui.components.InputField
import uz.gita.jaxongir.userformapp.ui.components.SampleSpinnerPreview
import uz.gita.jaxongir.userformapp.ui.components.SelectorItem
import uz.gita.jaxongir.userformapp.ui.components.TextComponent
import uz.gita.jaxongir.userformapp.utills.myLog2
import javax.inject.Inject

class DraftDetails @Inject constructor(
    private val list: List<ComponentData>,
    private val myPref: MyPref
) : AndroidScreen() {
    @RequiresApi(Build.VERSION_CODES.O)
    @Composable
    override fun Content() {
        val vm: DraftScreenContract.ViewModel = getViewModel<DraftDetailsViewModelImpl>()
        DraftDetailsContent(vm.uiState.collectAsState(), vm::onEventDispatcher, myPref)
        vm.onEventDispatcher(DraftScreenContract.Intent.UpdateList(list))
    }
}

@RequiresApi(Build.VERSION_CODES.O)
@Composable
fun DraftDetailsContent(
    uiState: State<DraftScreenContract.UiState>,
    onEventDispatchers: (DraftScreenContract.Intent) -> Unit,
    myPref: MyPref
) {
    val density = LocalDensity.current
    val weight = LocalConfiguration.current.screenWidthDp

    val context = LocalContext.current
    val systemUiController = rememberSystemUiController()
    systemUiController.setStatusBarColor(color = Color(0xFFFF3951))
    var shouldShowError by remember {
        mutableStateOf(false)
    }
    var enteredValue by remember {
        mutableStateOf("")
    }
    var selectesStates by remember {
        mutableStateOf(arrayListOf<Boolean>())
    }
    val componentsList by remember {
        mutableStateOf(mutableSetOf<ComponentData>())
    }
    Box(modifier = Modifier.fillMaxSize()) {
        if (uiState.value.list.isEmpty()) {
            Text(
                text = "There is no components",
                fontSize = 18.sp,
                textAlign = TextAlign.Center,
                modifier = Modifier.align(Alignment.Center),
                color = Color(0xFFFF3951)
            )
        } else {
            Box(modifier = Modifier.fillMaxSize()) {
                Column(modifier = Modifier.fillMaxSize()) {
                    Row(
                        modifier = Modifier
                            .fillMaxWidth()
                            .height(70.dp)
                            .background(Color(0xFFff7686))
                            .padding(horizontal = 16.dp, vertical = 12.dp),
                        verticalAlignment = Alignment.CenterVertically
                    ) {
                        Text(
                            text = "Fill these sheets",
                            color = Color.White,
                            fontSize = 28.sp,
                            fontWeight = FontWeight.Bold,
                        )
                    }


                    LazyColumn(
                        modifier = Modifier
                            .padding(top = 10.dp, start = 16.dp, end = 16.dp)
                            .fillMaxSize(),
                        horizontalAlignment = Alignment.CenterHorizontally
                    ) {
                        componentsList.clear()
                        uiState.value.list.forEach { data ->
                            when (data.type) {
                                ComponentEnum.Spinner -> {
                                    myLog2("spinnner")
                                    if (data.rowId == "") {
                                        item {
                                            SampleSpinnerPreview(
                                                list = data.variants,
                                                preselected = data.variants.firstOrNull() ?: "",
                                                onSelectionChanged = { enteredValue = it },
                                                content = data.content,
                                                componentData = data,
                                                deleteComp = { word -> },
                                                isEnable = true,
                                                isDraft = true
                                            )
                                            Spacer(modifier = Modifier.height(10.dp))
                                        }
                                        componentsList.add(data.copy(selectedSpinnerText = enteredValue))

                                    }
                                }

                                ComponentEnum.Selector -> {
                                    if (data.rowId == "") {
                                        item {
                                            Column {
                                                myLog2("Selector")
                                                SelectorItem(
                                                    question = data.content,
                                                    list = data.variants,
                                                    componentData = data,
                                                    deleteComp = {},
                                                    onSaveStates = { selectesStates.addAll(it) },
                                                    isEnable = true,
                                                    isInDraft = true
                                                )
                                                Spacer(modifier = Modifier.height(10.dp))
                                            }
                                            componentsList.add(data.copy(selected = selectesStates))
                                        }
                                    }
                                }

                                ComponentEnum.SampleText -> {
                                    if (data.rowId == "") {
                                        myLog2("Text")
                                        item {
                                            TextComponent(
                                                componentData = data
                                            )
                                            Spacer(modifier = Modifier.height(10.dp))
                                        }
                                        componentsList.add(data)
                                    }
                                }

                                ComponentEnum.Input -> {
                                    if (data.rowId == "") {
                                        item {
                                            myLog2("input")
                                            Column(modifier = Modifier.fillMaxWidth()) {
                                                if (data.isRequired) {
                                                    Text(
                                                        text = "Required field!",
                                                        modifier = Modifier.align(
                                                            Alignment.CenterHorizontally
                                                        )
                                                    )

                                                }
                                                InputField(
                                                    onEdit = { enteredValue = it },
                                                    componentData = data,
                                                    isEnable = true,
                                                    modifier = Modifier,
                                                    isInDraft = true
                                                )
                                                Spacer(modifier = Modifier.height(10.dp))
                                            }
                                            componentsList.add(data.copy(enteredValue = enteredValue))
                                        }
                                    }
                                }

                                ComponentEnum.Dater -> {
                                    if (data.rowId == "") {
                                        myLog2("dater")
                                        item {
                                            DatePickerPreview(
                                                componentData = data,
                                                content = data.content,
                                                isEnable = true,
                                                gettingDate = { enteredValue = it }
                                            )
                                            Spacer(modifier = Modifier.height(10.dp))
                                        }
                                        componentsList.add(data.copy(enteredValue = enteredValue))
                                    }
                                }

                                ComponentEnum.Image -> {
                                    if (data.rowId == "") {
                                        myLog2("image")
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
                                                        value = uri,
                                                        onValueChange = {
                                                            uri = it
                                                        },
                                                        singleLine = true,
                                                        label = {
                                                            Text(text = "Rasm Uri kiriting")
                                                        },
                                                        modifier = Modifier.fillMaxWidth(),
                                                        colors = OutlinedTextFieldDefaults.colors(
                                                            focusedBorderColor = Color(0xFFFF3951),
                                                            unfocusedBorderColor = Color(0xFFFF7686)
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
                                        componentsList.add(data)
                                    }
                                }

                                ComponentEnum.LazyRow -> {
                                    item {
                                        Row(modifier = Modifier.fillMaxWidth()) {
                                            uiState.value.list.filter {
                                                it.rowId == data.idEnteredByUser
                                            }.forEach {
                                                when (it.type) {
                                                    ComponentEnum.Selector -> {
                                                        Box(modifier = Modifier.weight(it.weight.toFloat())) {
                                                            SelectorItem(
                                                                question = data.content,
                                                                list = data.variants,
                                                                componentData = data,
                                                                onSaveStates = {
                                                                    selectesStates.addAll(
                                                                        it
                                                                    )
                                                                },
                                                                deleteComp = {},
                                                                isEnable = true,
                                                                isInDraft = true
                                                            )

                                                        }
                                                    }

                                                    ComponentEnum.SampleText -> {
                                                        Box(modifier = Modifier.weight(it.weight.toFloat())) {
                                                            TextComponent(
                                                                componentData = data
                                                            )
                                                            Spacer(modifier = Modifier.height(10.dp))
                                                        }
                                                    }

                                                    ComponentEnum.Spinner -> {
                                                        Box(modifier = Modifier.weight(it.weight.toFloat())) {
                                                            SampleSpinnerPreview(
                                                                list = data.variants,
                                                                preselected = data.variants[0],
                                                                onSelectionChanged = {
                                                                    enteredValue = it
                                                                },
                                                                content = data.content,
                                                                componentData = data,
                                                                deleteComp = {},
                                                                isEnable = true,
                                                                isDraft = true
                                                            )
                                                            componentsList.add(
                                                                data.copy(
                                                                    selectedSpinnerText = enteredValue
                                                                )
                                                            )
                                                        }
                                                    }

                                                    ComponentEnum.Input -> {
                                                        myLog2("input")
                                                        Column(modifier = Modifier.fillMaxWidth()) {
                                                            if (data.isRequired) {
                                                                Text(
                                                                    text = "Required field!",
                                                                    modifier = Modifier.align(
                                                                        Alignment.CenterHorizontally
                                                                    )
                                                                )

                                                            }
                                                            InputField(
                                                                onEdit = { enteredValue = it },
                                                                componentData = data,
                                                                isEnable = true,
                                                                modifier = Modifier,
                                                                isInDraft = true
                                                            )
                                                            componentsList.add(
                                                                data.copy(
                                                                    enteredValue = enteredValue
                                                                )
                                                            )
                                                            Spacer(modifier = Modifier.height(10.dp))
                                                        }
                                                    }


                                                    ComponentEnum.Dater -> {
                                                        Box(modifier = Modifier.weight(it.weight.toFloat())) {
                                                            DatePickerPreview(
                                                                componentData = data,
                                                                content = data.content,
                                                                isEnable = true,
                                                                gettingDate = { enteredValue = it }
                                                            )
                                                            componentsList.add(
                                                                data.copy(
                                                                    enteredValue = enteredValue
                                                                )
                                                            )
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
                                            DraftScreenContract.Intent.SaveAsDraft(
                                                list = componentsList,
                                                listOf(), listOf(), listOf(), context,
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
                                                DraftScreenContract.Intent.SaveAsSaved(
                                                    componentsList,
                                                    context,
                                                    listOf(),
                                                    listOf(),
                                                    listOf()
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

                            }
                        }

                    }
                }

            }
        }
    }
}

        @SuppressLint("UnrememberedMutableState")
        @RequiresApi(Build.VERSION_CODES.O)
        @Preview(showBackground = true)
        @Composable
        fun DraftDetailsPreview() {
        }
