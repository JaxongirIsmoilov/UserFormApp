package uz.gita.jaxongir.userformapp.presenter.login

import android.content.Context
import kotlinx.coroutines.flow.StateFlow
import uz.gita.jaxongir.userformapp.data.model.ComponentData

interface LoginContract {
    interface ViewModel {
        val uiState: StateFlow<UIState>
        fun onEventDispatcher(intent: Intent)
    }

    data class UIState(
        val loading: Boolean = false,
        val components: List<ComponentData> = emptyList()
    )

    interface Intent {
        data class OnLogin(val name: String, val password: String, val context: Context):Intent
    }


}