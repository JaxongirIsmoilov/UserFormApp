package uz.gita.jaxongir.userformapp.presenter.drafts_detail

import uz.gita.jaxongir.userformapp.data.model.ComponentData

interface DraftScreenContract {

    interface ViewModel {
        fun onEventDispatcher(intent : Intent)
    }

    interface Intent {
        object Back : Intent
        data class Submit(
            val componentData: ComponentData
        ) : Intent
        data class Draft(
            val componentData: ComponentData
        ) : Intent
    }

}