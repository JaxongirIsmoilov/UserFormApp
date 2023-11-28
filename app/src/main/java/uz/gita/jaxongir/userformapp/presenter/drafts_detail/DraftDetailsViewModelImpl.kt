package uz.gita.jaxongir.userformapp.presenter.drafts_detail

import androidx.lifecycle.ViewModel
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject

@HiltViewModel
class DraftDetailsViewModelImpl @Inject constructor() : ViewModel(), DraftScreenContract.ViewModel {
    override fun onEventDispatcher(intent: DraftScreenContract.Intent) {
        when (intent) {
            DraftScreenContract.Intent.Back -> {

            }


        }
    }
}