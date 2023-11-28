package uz.gita.jaxongir.userformapp.presenter.drafts_list

import uz.gita.jaxongir.userformapp.data.model.ComponentData

interface DraftDirection {
    suspend fun backToMain()
    suspend fun draftDetails(component: ComponentData)
}