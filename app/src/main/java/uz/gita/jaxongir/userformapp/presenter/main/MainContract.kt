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
        val rowComponenets: List<ComponentData> = listOf(),
        val checkedComponent: ComponentData? = null,
        val isLoading: Boolean = false
    )

    interface Intent {
        object Logout : Intent
        object LoadList : Intent

        data class CheckedComponent(val componentData: ComponentData) : Intent

        data class CheckRowComponent(val componentData: ComponentData) : Intent

        data class UpdateComponent(val componentData: ComponentData) : Intent

        object Load : Intent

        data class ClickAsSaved(
            val componentData: ComponentData,
            val value: String,
            val name: String,
            val savedId: String, val context: Context
        ) : Intent

        data class ClickAsDraft(
            val componentData: ComponentData,
            val value: String,
            val name: String,
            val draftId: String,
            val context: Context
        ) : Intent

        data class GetAllRowItems(val rowId: String) : Intent
    }

}