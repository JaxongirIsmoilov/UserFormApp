package uz.gita.jaxongir.userformapp.presenter.intro_screen

import kotlinx.coroutines.flow.StateFlow
import uz.gita.jaxongir.userformapp.presenter.main.MainContract

interface EntryScreenContract {

    interface ViewModel {
        val uiState: StateFlow<UIState>
        fun onEventDispatcher(intent: Intent)
    }

    interface Intent {
        object MoveToAdd : Intent
        object MoveToSubmit : Intent
        object MoveToDraft : Intent
        object Logout : Intent

    }

    data class UIState(
        val userName: String = "User"
    )

}