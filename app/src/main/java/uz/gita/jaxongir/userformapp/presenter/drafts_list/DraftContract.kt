package uz.gita.jaxongir.userformapp.presenter.drafts_list

import kotlinx.coroutines.flow.StateFlow
import uz.gita.jaxongir.userformapp.data.model.ComponentData

interface DraftContract {

    interface ViewModel{
        val uiState: StateFlow<UIState>
        fun onEventDispatcher(intent: Intent)
    }

    interface Intent {
        object BackToMain: Intent
        data class DraftDetails(
            val componentEntity: ComponentData
        ): Intent
    }

    data class UIState(
        val drafts : List<ComponentData> = emptyList()
    )

}