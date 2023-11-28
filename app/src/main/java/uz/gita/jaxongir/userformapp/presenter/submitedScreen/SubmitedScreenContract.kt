package uz.gita.jaxongir.userformapp.presenter.submitedScreen

import kotlinx.coroutines.flow.StateFlow
import uz.gita.jaxongir.userformapp.data.local.room.entity.FormEntity

interface SubmitedScreenContract {

    interface ViewModel {
        val uiState: StateFlow<UIState>

        fun onEventDispatcher(intent: Intent)
    }


    data class UIState(
        val list: List<FormEntity> = listOf()
    )


    interface Intent {
        object Back : Intent
    }
}