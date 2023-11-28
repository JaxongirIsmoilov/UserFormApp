package uz.gita.jaxongir.userformapp.presenter.drafts_list

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.launch
import uz.gita.jaxongir.userformapp.data.local.pref.MyPref
import uz.gita.jaxongir.userformapp.domain.repository.AppRepository
import javax.inject.Inject

@HiltViewModel
class DraftViewModelImpl @Inject constructor(
    private val direction: DraftDirection,
    private val repository: AppRepository,
    private val myPref: MyPref
) : DraftContract.ViewModel, ViewModel() {

    override val uiState = MutableStateFlow(DraftContract.UIState())


    override fun onEventDispatcher(intent: DraftContract.Intent) {
        when (intent) {
            DraftContract.Intent.BackToMain -> {
                viewModelScope.launch {
                    direction.backToMain()
                }
            }

            is DraftContract.Intent.DraftDetails -> {
                viewModelScope.launch {
                    direction.draftDetails(intent.componentEntity)
                }
            }
        }
    }
}