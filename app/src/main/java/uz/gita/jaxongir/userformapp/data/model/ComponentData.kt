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
    val variants: List<String> = listOf(),
    val selected: List<Boolean> = listOf(),
    val connectedValues: List<String> = listOf(),      //visibitily boyicha berilgan qiymatlar
    val connectedIds: List<String> = listOf(),         //boglangan id lar
    val operators: List<String> = listOf(),
    val type: ComponentEnum,
    val enteredValue: String = "",
    val isVisible: Boolean = true,
    val isRequired: Boolean = false,
    val selectedSpinnerText:String
)
