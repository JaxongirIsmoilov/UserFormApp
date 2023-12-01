package uz.gita.jaxongir.userformapp.domain.repository

import kotlinx.coroutines.flow.Flow
import uz.gita.jaxongir.userformapp.data.local.room.entity.FormEntity
import uz.gita.jaxongir.userformapp.data.model.ComponentData
import uz.gita.jaxongir.userformapp.data.model.DraftModel

interface AppRepository {

    fun getAllRowItemsById(rowId: String): Flow<Result<List<ComponentData>>>
    fun getDraftedItems(draftId: String, userID: String): Flow<Result<List<DraftModel>>>
    fun getSavedComponents(draftId: String, userID: String): Flow<Result<List<DraftModel>>>
    fun addAsDraft(componentData: ComponentData, value: String, name: String, draftId: String): Flow<Result<String>>
    fun addAsSaved(componentData: ComponentData, value: String, name: String, draftId: String): Flow<Result<String>>
    fun login(name: String, password: String): Flow<Result<Unit>>
    fun getComponentsByUserId(userID: String): Flow<Result<List<ComponentData>>>
    fun updateComponent(componentData: ComponentData): Flow<Result<Unit>>
    fun hasUserInFireBase(userID: String): Flow<Boolean>

}