package uz.gita.jaxongir.userformapp.presenter.drafts_detail

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class DraftDetailsViewModelImpl @Inject constructor(
   val direction: DraftDetailsDirection
) : ViewModel(), DraftScreenContract.ViewModel {
    override fun onEventDispatcher(intent: DraftScreenContract.Intent) {
        when (intent) {
            DraftScreenContract.Intent.Back -> {
                viewModelScope.launch {
                    direction.backToDraftsListScreen()
                }
            }


        }
    }
}