package uz.gita.jaxongir.userformapp.presenter.main

import kotlinx.coroutines.flow.StateFlow
import uz.gita.jaxongir.userformapp.data.model.ComponentData

interface MainContract {
    interface ViewModel {
        val uiState: StateFlow<UIState>
        fun onEventDispatcher(intent: Intent)
    }

    data class UIState(
        val loading: Boolean = false,
        val userName:String = "User",
        val userId:String = "",
        val components: List<ComponentData> = emptyList()
    )

    interface Intent {
        object Logout:Intent


    }

}