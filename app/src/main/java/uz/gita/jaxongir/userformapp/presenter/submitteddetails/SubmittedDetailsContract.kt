package uz.gita.jaxongir.userformapp.presenter.submitteddetails

import kotlinx.coroutines.flow.StateFlow
import uz.gita.jaxongir.userformapp.data.model.ComponentData
import uz.gita.jaxongir.userformapp.data.model.defaultData

interface SubmittedDetailsContract {
    interface ViewModel {
        val uiState: StateFlow<UIState>
        fun onEventDispatcher(intent: Intent)
    }

    interface Intent {
        object BackToSubmits : Intent
        data class UpdateList(val list: List<String>) : SubmittedDetailsContract.Intent
        data class CheckedComponent(val component: ComponentData) : SubmittedDetailsContract.Intent
        data class GetComponents(val list: List<String>) : SubmittedDetailsContract.Intent


    }

    data class UIState(
        val submittedDetails: List<ComponentData> = emptyList(),
        val listIds: List<String> = listOf(),
        val checkedComponent: ComponentData = defaultData
    )


}