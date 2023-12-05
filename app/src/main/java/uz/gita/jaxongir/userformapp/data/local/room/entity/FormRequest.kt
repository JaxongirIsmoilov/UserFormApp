package uz.gita.jaxongir.userformapp.data.local.room.entity

import uz.gita.jaxongir.userformapp.data.model.ComponentData

data class FormRequest(
    val listComponentIds: List<ComponentData> = listOf(),
    val isDraft: Boolean = false,
    val userId: String = "",
    val enteredValues: List<String> = listOf(),
    val selectedValue: List<String> = listOf(),
    val selectedStates: List<Boolean> = listOf()
)
//testpush