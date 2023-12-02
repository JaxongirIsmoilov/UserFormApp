package uz.gita.jaxongir.userformapp.presenter.submitedScreen

import kotlinx.coroutines.flow.StateFlow
import uz.gita.jaxongir.userformapp.data.local.room.entity.FormData
import uz.gita.jaxongir.userformapp.data.model.ComponentData
import uz.gita.jaxongir.userformapp.data.model.DraftModel

interface SubmitedScreenContract {

    interface ViewModel {
        val uiState: StateFlow<UIState>

        fun onEventDispatcher(intent: Intent)
    }


    data class UIState(
        val list: List<FormData> = listOf(),
        val isLoading :Boolean = false
    )


    interface Intent {
        object Back : Intent
        data class ClickItem(val list: List<String>) : Intent
        data class GetSubmittedItems(val userId: String, val draftId: String) : Intent
    }
}