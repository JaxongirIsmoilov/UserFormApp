package uz.gita.jaxongir.userformapp.data.local.room.entity

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity
data class FormRequest(
    @PrimaryKey(autoGenerate = true)
    val listComponentIds: List<String>,
    val isDraft: Boolean,
    val userId: String,
)