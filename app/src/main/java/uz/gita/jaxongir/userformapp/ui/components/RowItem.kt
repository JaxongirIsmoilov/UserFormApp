package uz.gita.jaxongir.userformapp.ui.components

import android.os.Build
import androidx.annotation.RequiresApi
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import uz.gita.jaxongir.userformapp.data.enums.ComponentEnum
import uz.gita.jaxongir.userformapp.data.model.ComponentData
import uz.gita.jaxongir.userformapp.presenter.main.MainContract
import uz.gita.jaxongir.userformapp.utills.myLog

@RequiresApi(Build.VERSION_CODES.O)
@Composable
fun RowItem(
    onEventDispatcher: (MainContract.Intent) -> Unit,
    components: List<ComponentData>,
) {
    Row(Modifier.fillMaxWidth()) {
        myLog("${components.size}")
        components.forEach { data ->
            when (data.type) {
                ComponentEnum.Spinner -> {
                    SampleSpinnerPreview(
                        list = data.variants ?: listOf(),
                        preselected = data.variants[0] ?: "",

                        onSelectionChanged = {

                        },
                        content = data.content,
                        componentData = data,
                        {},
                        modifier = Modifier.weight(1f),
                        true,
                        isDraft = false
                    )


                }

                ComponentEnum.Selector -> {

                    Column {
                        SelectorItem(
                            question = data.content,
                            list = data.variants,
                            componentData = data,
                            onSaveStates = {
                                    onEventDispatcher.invoke(
                                        MainContract.Intent.UpdateComponent(
                                            componentData = data.copy(selected = it)
                                        )
                                    )
                            }, deleteComp = {
                                    onEventDispatcher.invoke(
                                        MainContract.Intent.UpdateComponent(
                                            data.copy(enteredValue = "")
                                        )
                                    )
                                    if (data.operators.isNotEmpty()) {
                                        onEventDispatcher.invoke(
                                            MainContract.Intent.CheckedComponent(
                                                data
                                            )
                                        )
                                    }
                            }, isEnable = true, modifier = Modifier.weight(1f), isInDraft = false
                        )
                    }

                }

                ComponentEnum.SampleText -> {
                        myLog("sample text")
                        if (data.operators.isNotEmpty()) {
                            onEventDispatcher.invoke(
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

                ComponentEnum.Input -> {

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
                            onEdit = {
                                    onEventDispatcher.invoke(
                                        MainContract.Intent.UpdateComponent(
                                            data.copy(enteredValue = it)
                                        )
                                    )
                                    if (data.operators.isNotEmpty()) {
                                        onEventDispatcher.invoke(
                                            MainContract.Intent.CheckedComponent(
                                                data
                                            )
                                        )
                                    }

                            },
                            componentData = data,
                            isEnable = true,
                            modifier = Modifier.weight(1f),
                            isInDraft = false
                        )
                    }


                }

                ComponentEnum.Dater -> {

                    DatePickerPreview(
                        componentData = data,
                        content = data.content, modifier = Modifier.weight(1f), isEnable = true
                    ) {
                            onEventDispatcher.invoke(
                                MainContract.Intent.UpdateComponent(
                                    data.copy(enteredValue = "asdfdsa")
                                )
                            )
                            if (data.operators.isNotEmpty()) {
                                onEventDispatcher.invoke(
                                    MainContract.Intent.CheckedComponent(
                                        data
                                    )
                                )
                            }
                    }

                }


                else -> {
                    //
                }
            }
        }
    }


}