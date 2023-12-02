package uz.gita.jaxongir.userformapp.presenter.drafts_detail

import android.annotation.SuppressLint
import android.net.Uri
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
import androidx.compose.foundation.layout.aspectRatio
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
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
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
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.core.graphics.blue
import androidx.core.graphics.green
import androidx.core.graphics.red
import cafe.adriel.voyager.androidx.AndroidScreen
import cafe.adriel.voyager.hilt.getViewModel
import coil.compose.AsyncImage
import uz.gita.jaxongir.userformapp.R
import uz.gita.jaxongir.userformapp.data.enums.ComponentEnum
import uz.gita.jaxongir.userformapp.data.enums.ImageTypeEnum
import uz.gita.jaxongir.userformapp.data.local.pref.MyPref
import uz.gita.jaxongir.userformapp.ui.components.DatePickerPreview
import uz.gita.jaxongir.userformapp.ui.components.InputField
import uz.gita.jaxongir.userformapp.ui.components.SampleSpinnerPreview
import uz.gita.jaxongir.userformapp.ui.components.SelectorItem
import uz.gita.jaxongir.userformapp.utills.myLog
import javax.inject.Inject

class DraftDetails @Inject constructor(
    private val list: List<String>,
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
    val density = LocalDensity.current
    val weight = LocalConfiguration.current.screenWidthDp
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
                                                    preselected = data.variants[0] ?: "",

                                                    onSelectionChanged = {
                                                        onEventDispatchers.invoke(
                                                            DraftScreenContract.Intent.UpdateComponent(
                                                                data.copy(selectedSpinnerText = it)
                                                            )
                                                        )
                                                        if (data.operators.isNotEmpty()) {
                                                            myLog("spinner compo")
                                                            onEventDispatchers.invoke(
                                                                DraftScreenContract.Intent.CheckedComponent(
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
                                                    DraftScreenContract.Intent.CheckedComponent(
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
                                                                DraftScreenContract.Intent.UpdateComponent(
                                                                    componentData = data.copy(
                                                                        selected = it
                                                                    )
                                                                )
                                                            )
                                                        },
                                                        deleteComp = {
                                                            onEventDispatchers.invoke(
                                                                DraftScreenContract.Intent.UpdateComponent(
                                                                    data.copy(enteredValue = "")
                                                                )
                                                            )
                                                            if (data.operators.isNotEmpty()) {
                                                                onEventDispatchers.invoke(
                                                                    DraftScreenContract.Intent.CheckedComponent(
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
                                                        DraftScreenContract.Intent.CheckedComponent(
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
                                                    DraftScreenContract.Intent.CheckedComponent(
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
                                                                    DraftScreenContract.Intent.UpdateComponent(
                                                                        data.copy(enteredValue = it)
                                                                    )
                                                                )
                                                                if (data.operators.isNotEmpty()) {
                                                                    onEventDispatchers.invoke(
                                                                        DraftScreenContract.Intent.CheckedComponent(
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

                                        ComponentEnum.Dater -> {
                                            item {
                                                onEventDispatchers.invoke(
                                                    DraftScreenContract.Intent.CheckedComponent(
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
                                                        DraftScreenContract.Intent.UpdateComponent(
                                                            data.copy(enteredValue = "asdfdsa")
                                                        )
                                                    )
                                                    if (data.operators.isNotEmpty()) {
                                                        onEventDispatchers.invoke(
                                                            DraftScreenContract.Intent.CheckedComponent(
                                                                data
                                                            )
                                                        )
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
                                                                            Modifier.aspectRatio(
                                                                                data.ratioX.toFloat() / data.ratioY.toFloat()
                                                                            )
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
                                                                            Modifier.aspectRatio(
                                                                                data.ratioX.toFloat() / data.ratioY.toFloat()
                                                                            )
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
                                                        uiState.value.list.filter {
                                                            it.rowId == data.idEnteredByUser
                                                        }.forEach {
                                                            when (it.type) {
                                                                ComponentEnum.Selector -> {
                                                                    Box(
                                                                        modifier = Modifier.weight(
                                                                            it.weight.toFloat()
                                                                        )
                                                                    ) {
                                                                        onEventDispatchers.invoke(
                                                                            DraftScreenContract.Intent.CheckedComponent(
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
                                                                                        DraftScreenContract.Intent.UpdateComponent(
                                                                                            componentData = data.copy(
                                                                                                selected = it
                                                                                            )
                                                                                        )
                                                                                    )
                                                                                },
                                                                                deleteComp = {
                                                                                    onEventDispatchers.invoke(
                                                                                        DraftScreenContract.Intent.UpdateComponent(
                                                                                            data.copy(
                                                                                                enteredValue = ""
                                                                                            )
                                                                                        )
                                                                                    )
                                                                                    if (data.operators.isNotEmpty()) {
                                                                                        onEventDispatchers.invoke(
                                                                                            DraftScreenContract.Intent.CheckedComponent(
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
                                                                    Box(
                                                                        modifier = Modifier.weight(
                                                                            it.weight.toFloat()
                                                                        )
                                                                    ) {
                                                                        if (data.operators.isNotEmpty()) {
                                                                            onEventDispatchers.invoke(
                                                                                DraftScreenContract.Intent.CheckedComponent(
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
                                                                                            Color(
                                                                                                0xFFFF7686
                                                                                            ),
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
                                                                                    else Modifier.size(
                                                                                        0.dp
                                                                                    )
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
                                                                        Spacer(
                                                                            modifier = Modifier.height(
                                                                                16.dp
                                                                            )
                                                                        )
                                                                    }
                                                                }

                                                                ComponentEnum.Spinner -> {
                                                                    SampleSpinnerPreview(
                                                                        list = data.variants
                                                                            ?: listOf(),
                                                                        preselected = data.variants[0]
                                                                            ?: "",

                                                                        onSelectionChanged = {
                                                                            onEventDispatchers.invoke(
                                                                                DraftScreenContract.Intent.UpdateComponent(
                                                                                    data.copy(
                                                                                        selectedSpinnerText = it
                                                                                    )
                                                                                )
                                                                            )
                                                                            if (data.operators.isNotEmpty()) {
                                                                                myLog("spinner compo")
                                                                                onEventDispatchers.invoke(
                                                                                    DraftScreenContract.Intent.CheckedComponent(
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

                                                                ComponentEnum.Input -> {
                                                                    onEventDispatchers.invoke(
                                                                        DraftScreenContract.Intent.CheckedComponent(
                                                                            data
                                                                        )
                                                                    )

                                                                    if (data.rowId == "") {
                                                                        Column(modifier = Modifier.fillMaxWidth()) {
                                                                            Spacer(
                                                                                modifier = Modifier.size(
                                                                                    10.dp
                                                                                )
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
                                                                                        DraftScreenContract.Intent.UpdateComponent(
                                                                                            data.copy(
                                                                                                enteredValue = it
                                                                                            )
                                                                                        )
                                                                                    )
                                                                                    if (data.operators.isNotEmpty()) {
                                                                                        onEventDispatchers.invoke(
                                                                                            DraftScreenContract.Intent.CheckedComponent(
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
                                                                    onEventDispatchers.invoke(
                                                                        DraftScreenContract.Intent.CheckedComponent(
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
                                                                            DraftScreenContract.Intent.UpdateComponent(
                                                                                data.copy(
                                                                                    enteredValue = "asdfdsa"
                                                                                )
                                                                            )
                                                                        )
                                                                        if (data.operators.isNotEmpty()) {
                                                                            onEventDispatchers.invoke(
                                                                                DraftScreenContract.Intent.CheckedComponent(
                                                                                    data
                                                                                )
                                                                            )
                                                                        }
                                                                    }
                                                                }

                                                                ComponentEnum.Image -> {
                                                                    val height =
                                                                        when (data.customHeight) {
                                                                            "w/3" -> {
                                                                                weight.dp / 3
                                                                            }

                                                                            "w/2" -> {
                                                                                weight.dp / 2
                                                                            }

                                                                            "w" -> {
                                                                                weight.dp
                                                                            }

                                                                            "2w" -> {
                                                                                weight.dp * 2
                                                                            }

                                                                            else -> {
                                                                                0.dp
                                                                            }

                                                                        }
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
                                                                                            Modifier.aspectRatio(
                                                                                                data.ratioX.toFloat() / data.ratioY.toFloat()
                                                                                            )
                                                                                        } else if (data.customHeight != "") {
                                                                                            Modifier.height(
                                                                                                height = height
                                                                                            )
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
                                                                                model = Uri.parse(
                                                                                    uri
                                                                                ),
                                                                                contentDescription = null,
                                                                                modifier = Modifier
                                                                                    .then(
                                                                                        if (data.ratioX != 0) {
                                                                                            Modifier.aspectRatio(
                                                                                                data.ratioX.toFloat() / data.ratioY.toFloat()
                                                                                            )
                                                                                        } else if (data.customHeight != "") {
                                                                                            Modifier.height(
                                                                                                height = height
                                                                                            )
                                                                                        } else {
                                                                                            Modifier
                                                                                        }
                                                                                    ),
                                                                                error = painterResource(
                                                                                    id = R.drawable.cats
                                                                                )
                                                                            )
                                                                        }
                                                                    }
                                                                }

                                                                else -> {}
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
                                                                    list = uiState.value.listIds,
                                                                    context
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
                                                                        uiState.value.listIds,
                                                                        context
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
                                                        uiState.value.listIds, context = context
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
                                                            uiState.value.listIds, context
                                                        )
                                                    )

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

}


@SuppressLint("UnrememberedMutableState")
@RequiresApi(Build.VERSION_CODES.O)
@Preview(showBackground = true)
@Composable
fun DraftDetailsPreview() {
//    DraftDetailsContent(mutableStateOf(DraftScreenContract.UiState()), onEventDispatchers = {})
}