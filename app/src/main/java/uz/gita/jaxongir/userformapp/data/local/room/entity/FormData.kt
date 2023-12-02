package uz.gita.jaxongir.userformapp.data.local.room.entity

import androidx.room.Entity
import androidx.room.PrimaryKey


@Entity
data class FormData(
    @PrimaryKey(autoGenerate = true)
    val id: String,
    val listComponentIds: List<String>,
    val isDraft: Boolean,
    val userId: String,
)