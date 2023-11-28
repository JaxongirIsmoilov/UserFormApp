package uz.gita.jaxongir.userformapp.ui.components

import android.os.Build
import androidx.annotation.RequiresApi
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Icon
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.derivedStateOf
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.vanpra.composematerialdialogs.MaterialDialog
import com.vanpra.composematerialdialogs.datetime.date.datepicker
import com.vanpra.composematerialdialogs.rememberMaterialDialogState
import uz.gita.jaxongir.userformapp.R
import uz.gita.jaxongir.userformapp.data.enums.ComponentEnum
import uz.gita.jaxongir.userformapp.data.enums.TextFieldType
import uz.gita.jaxongir.userformapp.data.model.ComponentData
import java.time.LocalDate
import java.time.format.DateTimeFormatter

@RequiresApi(Build.VERSION_CODES.O)
@Composable
fun DatePickerPreview(
    componentData: ComponentData,
    content: String,
    isEnable: Boolean,
    deleteComp: () -> Unit = {}
) {
    var pickedDate by remember {
        mutableStateOf(LocalDate.now())
    }

    val formattedDate by remember {
        derivedStateOf {
            DateTimeFormatter
                .ofPattern("MMM dd yyyy")
                .format(pickedDate)
        }
    }

    val dateDialogState = rememberMaterialDialogState()

    Column(
        modifier = Modifier.then(
            if (componentData.isVisible) Modifier.fillMaxWidth() else Modifier.size(0.dp)
        )
    ) {
        Text(text = content, fontSize = 16.sp)
        Spacer(modifier = Modifier.height(8.dp))
        Row {

            Row(
                Modifier
                    .weight(1f)
                    .clip(RoundedCornerShape(12.dp))
                    .background(Color(0xFFF6F2F7))
                    .border(1.dp, Color(0xFFFF3951), RoundedCornerShape(12.dp))
                    .padding(horizontal = 12.dp, vertical = 12.dp),
                verticalAlignment = Alignment.CenterVertically
            ) {
                Text(text = formattedDate, modifier = Modifier.weight(1f), fontSize = 14.sp)
                Icon(painter = painterResource(id = R.drawable.ic_date),
                    contentDescription = "date",
                    tint = Color(0xFFFF7686),
                    modifier = Modifier
                        .clickable {
                            if (isEnable) {
                                dateDialogState.show()
                            }
                        }
                        .width(36.dp))
            }
        }
    }

    MaterialDialog(
        dialogState = dateDialogState,
        buttons = {
            positiveButton(text = "Ok")
            negativeButton(text = "Cancel")
        }
    ) {
        datepicker(
            initialDate = LocalDate.now(),
            title = "Choose a date",
        ) {
            pickedDate = it
        }
    }
}

@RequiresApi(Build.VERSION_CODES.O)
@Preview
@Composable
fun DatePickerPreviewNew() {
    DatePickerPreview(
        ComponentData(
            id = "",
            userId = "",
            locId = 0,
            idEnteredByUser = "",
            content = "",
            textFieldType = TextFieldType.Text,
            maxLines = 0,
            maxLength = 0,
            minLength = 0,
            maxValue = 0,
            minValue = 0,
            isMulti = false,
            variants = listOf(),
            selected = listOf(),
            connectedValues = listOf(),
            connectedIds = listOf(),
            operators = listOf(),
            type = ComponentEnum.Dater,
            enteredValue = "",
            isVisible = false, isRequired = false, selectedSpinnerText = ""
        ),
        "", false,
    )
}
