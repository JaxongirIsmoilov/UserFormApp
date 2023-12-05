package uz.gita.jaxongir.userformapp.data.model

import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.toArgb
import uz.gita.jaxongir.userformapp.data.enums.ComponentEnum
import uz.gita.jaxongir.userformapp.data.enums.ImageTypeEnum
import uz.gita.jaxongir.userformapp.data.enums.TextFieldType

data class ComponentData(
    val id: String = "",
    val userId: String = "",
    val locId: Long = 0L,
    val idEnteredByUser: String = "",
    val content: String = "",
    val enteredValue: String = "",
    val textFieldType: TextFieldType = TextFieldType.Text,
    val maxLines: Int = 0,
    val maxLength: Int = 0,
    val minLength: Int = 0,
    val maxValue: Int = 0,
    val minValue: Int = 0,
    val isMulti: Boolean = false,
    val variants: List<String> = listOf(),
    val selected: List<Boolean> = listOf(),
    val connectedValues: List<String> = listOf(),
    val connectedIds: List<String> = listOf(),
    val operators: List<String> = listOf(),
    val selectedSpinnerText: String = "",
    val type: ComponentEnum = ComponentEnum.SampleText,
    val isRequired: Boolean = false,
    val imgUri: String = "",
    val ratioX: Int = 0,
    val ratioY: Int = 0,
    val customHeight: String = "",
    val rowId: String = "",
    val backgroundColor: Int = Color.Transparent.toArgb(),
    val weight: String = "",
    val imageType: ImageTypeEnum = ImageTypeEnum.NONE,
    val inValues: List<String> = listOf(),
    val isVisible: Boolean = false,
)


val defaultData = ComponentData(
    "1",
    "userID",
    1L,
    "",
    "Content",
    "",
    TextFieldType.Text,
    1,
    122,
    2,
    11,
    1,
    true,
    emptyList<String>(),
    emptyList<Boolean>(),
    emptyList<String>(),
    emptyList<String>(),
    emptyList<String>(),
    "",
    ComponentEnum.Input,
    false,
    "",
    0, 0,
    "",
    "",
    1,
    "",
    ImageTypeEnum.NONE,
    listOf(),
    false
)
