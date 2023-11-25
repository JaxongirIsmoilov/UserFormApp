package uz.gita.jaxongir.userformapp.presenter.main

import kotlinx.coroutines.flow.StateFlow

interface MainContract {
    interface ViewModel {
        val uiState: StateFlow<UIState>
        fun onEventDispatcher(intent: Intent)
    }

    data class UIState(val loading: Boolean, val name: String)

    interface Intent {
        object LogOut : Intent
    }
}