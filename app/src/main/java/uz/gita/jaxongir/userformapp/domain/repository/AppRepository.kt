package uz.gita.jaxongir.userformapp.domain.repository

import kotlinx.coroutines.flow.Flow
import uz.gita.jaxongir.userformapp.data.model.ComponentData

interface AppRepository {
    fun login(name: String, password: String): Flow<Result<Unit>>
    fun getComponentsByUserId(userID: String): Flow<Result<List<ComponentData>>>
}