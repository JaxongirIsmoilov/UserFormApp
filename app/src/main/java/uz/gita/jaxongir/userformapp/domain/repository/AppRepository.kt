package uz.gita.jaxongir.userformapp.domain.repository

import kotlinx.coroutines.flow.Flow
import uz.gita.jaxongir.userformapp.data.local.room.entity.FormEntity
import uz.gita.jaxongir.userformapp.data.model.ComponentData

interface AppRepository {

    fun getAllRowItemsById(rowId: String): Flow<Result<List<ComponentData>>>
    fun getDraftedItems(userID: String): Flow<Result<List<FormEntity>>>
    fun getSavedComponents(userID: String): Flow<Result<List<FormEntity>>>
    fun addAsDraft(entity: FormEntity): Flow<Result<String>>
    fun addAsSaved(entity: FormEntity): Flow<Result<String>>
    fun login(name: String, password: String): Flow<Result<Unit>>
    fun getComponentsByUserId(userID: String): Flow<Result<List<ComponentData>>>
    fun updateComponent(componentData: ComponentData): Flow<Result<Unit>>
    fun hasUserInFireBase(userID: String): Flow<Boolean>

}