package uz.gita.jaxongir.userformapp.data.model

import uz.gita.jaxongir.userformapp.data.enums.ComponentEnum
import uz.gita.jaxongir.userformapp.data.enums.TextFieldType

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
    val variants: List<String>,
    val selected: List<Boolean>,
    val conditions: List<Conditions>,
    val type: ComponentEnum,
    val enteredValue : String = ""
)
