package uz.gita.jaxongir.userformapp.presenter.drafts_list

import kotlinx.coroutines.flow.StateFlow
import uz.gita.jaxongir.userformapp.data.local.room.entity.FormEntity
import uz.gita.jaxongir.userformapp.data.model.ComponentData

interface DraftContract {

    interface ViewModel {
        val uiState: StateFlow<UIState>
        fun onEventDispatcher(intent: Intent)
    }

    interface Intent {
        object BackToMain : Intent
        data class ClickItem(
            val list: List<ComponentData>
        ) : Intent
    }

    data class UIState(
        val list: List<FormEntity> = listOf()
    )

}