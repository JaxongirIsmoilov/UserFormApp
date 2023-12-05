package uz.gita.jaxongir.userformapp.presenter.main

import android.content.Context
import kotlinx.coroutines.flow.StateFlow
import uz.gita.jaxongir.userformapp.data.model.ComponentData

interface MainContract {
    interface ViewModel {
        val uiState: StateFlow<UIState>
        fun onEventDispatcher(intent: Intent)
    }

    data class UIState(
        val loading: Boolean = false,
        val userName: String = "User",
        val userId: String = "",
        val components: List<ComponentData> = emptyList(),
        val rowComponents: List<ComponentData> = listOf(),
        val checkedComponent: ComponentData? = null,
        val isLoading: Boolean = false
    )

    interface Intent {
        object Logout : Intent
        object LoadList : Intent

        data class CheckedComponent(val componentData: ComponentData) : Intent

        data class CheckRowComponent(val componentData: ComponentData) : Intent

        data class UpdateComponent(val componentData: ComponentData) : Intent
        data class addComponentData(val componentData: ComponentData) : Intent

        object Load : Intent

        data class ClickAsSaved(
            val list: List<ComponentData>,
            val context: Context
        ) : Intent

        data class ClickAsDraft(
            val list: List<ComponentData>,
            val context: Context
        ) : Intent

        data class GetAllRowItems(val rowId: String) : Intent
    }

}