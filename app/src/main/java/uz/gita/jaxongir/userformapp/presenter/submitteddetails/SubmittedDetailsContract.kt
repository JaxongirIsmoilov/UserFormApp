package uz.gita.jaxongir.userformapp.presenter.submitteddetails

import kotlinx.coroutines.flow.StateFlow
import uz.gita.jaxongir.userformapp.data.model.ComponentData
import uz.gita.jaxongir.userformapp.presenter.submitedScreen.SubmitedScreenContract

interface SubmittedDetailsContract {
    interface ViewModel{
        val uiState: StateFlow<UIState>
        fun onEventDispatcher(intent: Intent)
    }

    interface Intent {
        object BackToSubmits: Intent
        data class GetSubmittedItems(val userId: String, val draftId: String) :
            Intent


    }

    data class UIState(
        val loading: Boolean = false,
        val userName:String = "User",
        val userId:String = "",
        val submittedDetails: List<ComponentData> = emptyList()
    )



}