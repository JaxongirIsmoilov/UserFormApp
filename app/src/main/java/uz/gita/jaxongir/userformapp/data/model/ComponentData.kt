package uz.gita.jaxongir.userformapp.data.model

import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.toArgb
import com.google.gson.Gson
import uz.gita.jaxongir.userformapp.data.enums.ComponentEnum
import uz.gita.jaxongir.userformapp.data.enums.TextFieldType
import uz.gita.jaxongir.userformapp.data.local.room.entity.ComponentEntity
import java.io.Serializable

data class ComponentData(
    val id: String,
    val userId: String,
    val locId: Long,
    val idEnteredByUser: String = "",
    val content: String,
    val textFieldType: TextFieldType,
    val maxLines: Int,
    val maxLength: Int,
    val minLength: Int,
    val maxValue: Int,
    val minValue: Int,
    val isMulti: Boolean,
    val variants: List<String> = listOf(),
    val selected: List<Boolean> = listOf(),
    val connectedValues: List<String> = listOf(),
    val connectedIds: List<String> = listOf(),
    val operators: List<String> = listOf(),
    val type: ComponentEnum,
    val enteredValue: String = "",
    val isVisible: Boolean = true,
    val isRequired: Boolean = false,
    val selectedSpinnerText : String,
    val imgUri : String = "",
    val ratioX : Int,
    val ratioY : Int,
    val customHeight : String,
    val rowId: String = "",
    val backgroundColor: Int = Color.Transparent.toArgb()
) : Serializable {
    private val converter = Gson()

    fun toEntity(): ComponentEntity = ComponentEntity(
        id = id,
        userId = userId,
        locId = locId,
        idEnteredByUser = idEnteredByUser,
        content = content,
        textFieldType = textFieldType,
        maxLines = maxLines,
        maxLength = maxLength,
        minLength = minLength,
        maxValue = maxValue,
        minValue = minValue,
        isMulti = isMulti,
        variants = variants,
        selected = selected,
        connectedValues = connectedValues,
        connectedIds = connectedIds,
        operators = operators,
        type = type,
        isRequired = isRequired,
        selectedSpinnerText = selectedSpinnerText,
        imgUri = imgUri,
        ratioX = ratioX,
        ratioY = ratioY,
        customHeight = customHeight,
        rowId = rowId,
        backgroundColor = backgroundColor,
    )
}

val defaultData = ComponentData(
    "1",
    "userID",
    1L,
    "",
    "Content",
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
    ComponentEnum.Input,
    "",
    true,
    false,
    "",
    "",
    1,
    1,
    "",
    "",
    Color.Transparent.toArgb()
)
