package uz.gita.jaxongir.userformapp.domain.repository

import kotlinx.coroutines.flow.Flow
import uz.gita.jaxongir.userformapp.data.local.room.entity.FormData
import uz.gita.jaxongir.userformapp.data.local.room.entity.FormRequest
import uz.gita.jaxongir.userformapp.data.model.ComponentData

interface AppRepository {

    fun addDraftedItems(request: FormRequest): Flow<Result<String>>
    fun addSavedItems(request: FormRequest): Flow<Result<String>>
    fun getAllSavedItemsList(userID: String): Flow<Result<List<FormData>>>
    fun getAllDraftedItemsList(userID: String): Flow<Result<List<FormData>>>
    fun getComponentByComponentId(componentId: String): Flow<Result<ComponentData>>
    fun login(name: String, password: String): Flow<Result<Unit>>
    fun getComponentsByUserId(userID: String): Flow<Result<List<ComponentData>>>
    fun updateComponent(componentData: ComponentData): Flow<Result<Unit>>
    fun hasUserInFireBase(userID: String): Flow<Boolean>

}