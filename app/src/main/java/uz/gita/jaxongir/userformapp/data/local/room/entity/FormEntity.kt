package uz.gita.jaxongir.userformapp.data.local.room.entity

import androidx.room.Entity
import androidx.room.PrimaryKey
import uz.gita.jaxongir.userformapp.data.model.ComponentData
import java.util.Date


@Entity
data class FormEntity(
    @PrimaryKey(autoGenerate = true)
    val id: Int,
    val listComponents: List<ComponentData>,
    val isDraft: Boolean,
    val isSubmitted: Boolean,
    val userId: String,
    val date: String
)