package uz.gita.jaxongir.userformapp.presenter.submitteddetails

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
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import cafe.adriel.voyager.androidx.AndroidScreen
import cafe.adriel.voyager.hilt.getViewModel
import uz.gita.jaxongir.userformapp.data.enums.ComponentEnum
import uz.gita.jaxongir.userformapp.data.model.ComponentData
import uz.gita.jaxongir.userformapp.ui.components.DatePickerPreview
import uz.gita.jaxongir.userformapp.ui.components.InputField
import uz.gita.jaxongir.userformapp.ui.components.SampleSpinnerPreview
import uz.gita.jaxongir.userformapp.ui.components.SelectorItem

class DetailsScreen(val list: List<ComponentData>) : AndroidScreen() {
    @RequiresApi(Build.VERSION_CODES.O)
    @Composable
    override fun Content() {
        val viewModel: SubmittedDetailsContract.ViewModel =
            getViewModel<SubmittedDetailsViewModelImpl>()
        DetailsScreenContent(
            uiState = viewModel.uiState.collectAsState(),
            onEventDispatchers = viewModel::onEventDispatcher,
            list
        )
    }
}

@RequiresApi(Build.VERSION_CODES.O)
@Composable
fun DetailsScreenContent(
    uiState: State<SubmittedDetailsContract.UIState>,
    onEventDispatchers: (SubmittedDetailsContract.Intent) -> Unit,
    list: List<ComponentData>
) {

    Box(modifier = Modifier.fillMaxSize()) {
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
                                                onSelectionChanged = {},
                                                content = data.content,
                                                componentData = data,
                                                deleteComp = {}, false, isDraft = true
                                            )

                                        }
                                    }

                                    ComponentEnum.Selector -> {
                                        item {
                                            Column {
                                                SelectorItem(
                                                    question = data.content,
                                                    list = data.variants,
                                                    onSaveStates = {},
                                                    componentData = data,
                                                    {},
                                                    isEnable = false, true
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
                                                if (data.isRequired) {
                                                    Text(
                                                        text = "This Field is required",
                                                        fontWeight = FontWeight(600),
                                                        color = Color(0xFFff7686)
                                                    )
                                                }
                                                Spacer(modifier = Modifier.size(10.dp))
                                                InputField(
                                                    onEdit = { },
                                                    componentData = data,
                                                    isEnable = false, isInDraft = true
                                                )
                                            }

                                        }
                                    }

                                    ComponentEnum.Dater -> {
                                        item {

                                            DatePickerPreview(
                                                componentData = data,
                                                content = data.content,
                                                isEnable = false
                                            ) {
                                            }
                                        }
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
}