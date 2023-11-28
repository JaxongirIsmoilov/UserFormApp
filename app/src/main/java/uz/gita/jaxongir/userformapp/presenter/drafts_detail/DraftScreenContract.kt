package uz.gita.jaxongir.userformapp.presenter.drafts_detail

interface DraftScreenContract {

    interface ViewModel {
        fun onEventDispatcher(intent: Intent)
    }

    interface Intent {
        object Back : Intent

    }

}