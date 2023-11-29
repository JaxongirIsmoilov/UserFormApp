package uz.gita.jaxongir.userformapp.presenter.drafts_detail

import android.annotation.SuppressLint
import android.os.Build
import android.util.Log
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
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
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
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import cafe.adriel.voyager.androidx.AndroidScreen
import cafe.adriel.voyager.hilt.getViewModel
import uz.gita.jaxongir.userformapp.R
import uz.gita.jaxongir.userformapp.data.enums.ComponentEnum
import uz.gita.jaxongir.userformapp.data.local.pref.MyPref
import uz.gita.jaxongir.userformapp.data.local.room.entity.FormEntity
import uz.gita.jaxongir.userformapp.data.model.ComponentData
import uz.gita.jaxongir.userformapp.ui.components.DatePickerPreview
import uz.gita.jaxongir.userformapp.ui.components.InputField
import uz.gita.jaxongir.userformapp.ui.components.SampleSpinnerPreview
import uz.gita.jaxongir.userformapp.ui.components.SelectorItem
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
    var shouldShowError by remember {
        mutableStateOf(false)
    }
    val context = LocalContext.current
    Box(modifier = Modifier.fillMaxSize()) {
        Column(
            modifier = Modifier
                .fillMaxSize()
        ) {

            Box(modifier = Modifier.fillMaxSize()) {
                if (uiState.value.list.isEmpty()) {
                    Text(
                        text = "There is no components yet!",
                        fontSize = 22.sp,
                        modifier = Modifier.align(Alignment.Center)
                    )

                } else {
                    Column(modifier = Modifier.fillMaxWidth()) {
                        Row(
                            verticalAlignment = Alignment.CenterVertically,
                            modifier = Modifier.background(Color(0xFFFF3159))
                        ) {
                            Spacer(modifier = Modifier.size(16.dp))
                            IconButton(
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
                            Spacer(modifier = Modifier.weight(1f))
                            Text(
                                text = "Draft's",
                                fontWeight = FontWeight.Black,
                                fontSize = 25.sp,
                            )
                            Spacer(modifier = Modifier.weight(1f))
                        }

                        Column(
                            modifier = Modifier
                                .fillMaxWidth()
                                .padding(horizontal = 10.dp)
                        ) {
                            LazyColumn(
                                modifier = Modifier
                                    .fillMaxWidth()
                                    .wrapContentHeight()
                                    .padding(top = 10.dp)
                            ) {
                                uiState.value.list.forEach { data ->
                                    when (data.type) {
                                        ComponentEnum.Spinner -> {
                                            item {
                                                SampleSpinnerPreview(
                                                    list = data.variants ?: listOf(),
                                                    preselected = data.selectedSpinnerText ?: "",
                                                    onSelectionChanged = {
                                                        onEventDispatchers.invoke(
                                                            DraftScreenContract.Intent.UpdateComponent(
                                                                data.copy(selectedSpinnerText = it)
                                                            )
                                                        )
                                                    },
                                                    content = data.content,
                                                    componentData = data, {}, true, true
                                                )

                                            }
                                        }

                                        ComponentEnum.Selector -> {
                                            item {
                                                Column {
                                                    SelectorItem(
                                                        question = data.content,
                                                        list = data.variants,
                                                        componentData = data,
                                                        onSaveStates = { list ->
                                                            list.forEach { state ->
                                                                if (state) {
                                                                    shouldShowError = false
                                                                }
                                                            }
                                                            onEventDispatchers.invoke(
                                                                DraftScreenContract.Intent.UpdateComponent(
                                                                    data.copy(selected = list)
                                                                )
                                                            )
                                                        },
                                                        deleteComp = {},
                                                        isEnable = true, isInDraft = true
                                                    )
                                                }
                                            }
                                        }

                                        ComponentEnum.SampleText -> {
                                            item {
                                                Row(
                                                    modifier = Modifier
                                                        .then(
                                                            if (data.isVisible) Modifier
                                                                .padding(top = 10.dp)
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
                                                            myLog2("onEdit")
                                                            onEventDispatchers.invoke(
                                                                DraftScreenContract.Intent.UpdateComponent(
                                                                    data.copy(
                                                                        enteredValue = it
                                                                    )
                                                                )
                                                            )
                                                        },
                                                        componentData = data,
                                                        isEnable = true,
                                                        isInDraft = true
                                                    )
                                                }

                                            }
                                        }

                                        ComponentEnum.Dater -> {
                                            item {
                                                DatePickerPreview(
                                                    componentData = data,
                                                    content = data.content, isEnable = true
                                                ) {
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
                                                        FormEntity(
                                                            id = 0,
                                                            uiState.value.list,
                                                            isDraft = true,
                                                            isSubmitted = false,
                                                            myPref.getId()
                                                        ), context = context
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
                                                onEventDispatchers.invoke(
                                                    DraftScreenContract.Intent.SaveAsSaved(
                                                        FormEntity(
                                                            id = 0,
                                                            listComponents = uiState.value.list,
                                                            isDraft = false,
                                                            isSubmitted = true,
                                                            myPref.getId()
                                                        ), context
                                                    )
                                                )
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

}


@SuppressLint("UnrememberedMutableState")
@RequiresApi(Build.VERSION_CODES.O)
@Preview(showBackground = true)
@Composable
fun DraftDetailsPreview() {
//    DraftDetailsContent(mutableStateOf(DraftScreenContract.UiState()), onEventDispatchers = {})
}