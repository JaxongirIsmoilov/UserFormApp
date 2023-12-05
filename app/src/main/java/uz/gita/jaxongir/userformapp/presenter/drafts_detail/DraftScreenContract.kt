package uz.gita.jaxongir.userformapp.presenter.drafts_detail

import android.content.Context
import kotlinx.coroutines.flow.StateFlow
import uz.gita.jaxongir.userformapp.data.model.ComponentData

interface DraftScreenContract {

    interface ViewModel {
        fun onEventDispatcher(intent: Intent)
        val uiState: StateFlow<UiState>


    }


    data class UiState(
        val list: List<ComponentData> = listOf(),
        val listIds: List<String> = listOf(),
        val checkedComponent: ComponentData? = null
    )

    interface Intent {
        object Back : Intent
        data class SaveAsDraft(
            val list: List<ComponentData>,
            val enteredValuesList: List<String>,
            val selectedValuesList: List<String>,
            val selectedStateList: List<Boolean>,
            val context: Context
        ) : Intent

        data class SaveAsSaved(
            val list: List<ComponentData>, val context: Context,
            val enteredValuesList: List<String>,
            val selectedValuesList: List<String>,
            val selectedStateList: List<Boolean>,
        ) : Intent

        data class UpdateComponent(val componentData: ComponentData) : Intent
        data class UpdateList(val list: List<ComponentData>) : Intent
        data class CheckedComponent(val component: ComponentData) : Intent
        data class GetComponents(val list: List<String>) : Intent


    }

}