package uz.gita.jaxongir.userformapp.presenter.intro_screen

import kotlinx.coroutines.flow.StateFlow

interface EntryScreenContract {

    interface ViewModel {
        val uiState: StateFlow<UIState>
        fun onEventDispatcher(intent : Intent)
    }

    interface Intent {
        object MoveToAdd : Intent
        object MoveToSubmit : Intent
        object MoveToDraft : Intent
    }

    data class UIState(
        val userName: String = "User"
    )

}