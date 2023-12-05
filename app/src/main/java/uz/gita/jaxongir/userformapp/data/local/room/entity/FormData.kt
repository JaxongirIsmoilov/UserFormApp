package uz.gita.jaxongir.userformapp.data.local.room.entity


data class FormData(
    val id: String,
    val listComponentIds: List<String>,
    val isDraft: Boolean,
    val userId: String,
    val enteredValues: List<String>,
    val selectedValue: List<String>,
    val selectedStates: List<Boolean>


)