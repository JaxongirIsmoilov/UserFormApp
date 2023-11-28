package uz.gita.jaxongir.userformapp.domain.repository

import kotlinx.coroutines.flow.Flow
import uz.gita.jaxongir.userformapp.data.local.room.entity.ComponentEntity
import uz.gita.jaxongir.userformapp.data.local.room.entity.FormEntity
import uz.gita.jaxongir.userformapp.data.model.ComponentData

interface AppRepository {
    fun getDraftedItems(): List<FormEntity>
    fun getSavedComponents(): List<FormEntity>
    suspend fun addAsDraft(list: List<ComponentEntity>)
    suspend fun addAsSaved(list: List<ComponentEntity>)
    fun login(name: String, password: String): Flow<Result<Unit>>
    fun getComponentsByUserId(userID: String): Flow<Result<List<ComponentData>>>
    fun updateComponent(componentData: ComponentData): Flow<Result<Unit>>
    fun hasUserInFireBase(userID: String): Flow<Boolean>

}