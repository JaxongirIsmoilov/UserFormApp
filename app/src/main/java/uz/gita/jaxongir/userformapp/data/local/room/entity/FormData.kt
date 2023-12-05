package uz.gita.jaxongir.userformapp.data.local.room.entity

import uz.gita.jaxongir.userformapp.data.model.ComponentData


data class FormData(
    val id: String,
    val listComponentIds: List<ComponentData>,
    val isDraft: Boolean,
    val userId: String,
    val enteredValues: List<String>,
    val selectedValue: List<String>,
    val selectedStates: List<Boolean>
)