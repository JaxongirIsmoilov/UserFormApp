package uz.gita.jaxongir.userformapp.presenter.drafts_detail

import android.content.Context
import kotlinx.coroutines.flow.StateFlow
import uz.gita.jaxongir.userformapp.data.local.room.entity.FormEntity
import uz.gita.jaxongir.userformapp.data.model.ComponentData

interface DraftScreenContract {

    interface ViewModel {
        fun onEventDispatcher(intent: Intent)
        val uiState: StateFlow<UiState>
    }


    data class UiState(
        val list: List<ComponentData> = listOf()
    )

    interface Intent {
        object Back : Intent
        data class SaveAsDraft(val entity: FormEntity, val context: Context) : Intent
        data class SaveAsSaved(val entity: FormEntity, val context: Context) : Intent
        data class UpdateComponent(val componentData: ComponentData) : Intent
        data class UpdateList(val list: List<ComponentData>) : Intent


    }

}