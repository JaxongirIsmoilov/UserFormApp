package uz.gita.jaxongir.userformapp.presenter.submitteddetails

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.launchIn
import kotlinx.coroutines.flow.onEach
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import uz.gita.jaxongir.userformapp.data.local.pref.MyPref
import uz.gita.jaxongir.userformapp.domain.repository.AppRepository
import uz.gita.jaxongir.userformapp.presenter.submitedScreen.SubmitedScreenContract
import uz.gita.jaxongir.userformapp.utills.myLog
import uz.gita.jaxongir.userformapp.utills.myLog2
import javax.inject.Inject

@HiltViewModel
class SubmittedDetailsViewModelImpl @Inject constructor(
    private val direction: DetailsDirection,
    private val repository: AppRepository,
    private val pref: MyPref
) : SubmittedDetailsContract.ViewModel, ViewModel() {
    override val uiState = MutableStateFlow(SubmittedDetailsContract.UIState())


    init {
        uiState.update { it.copy(userName = pref.getUserName()) }
        viewModelScope.launch {
            uiState.update { it.copy(loading = true) }
            repository.getComponentsByUserId(pref.getId())
                .onEach { result ->
                    result.onSuccess { components ->
                        myLog2("successs submitted vm")
                        uiState.update { it.copy(submittedDetails = components) }
                    }
                    result.onFailure {

                    }
                    uiState.update { it.copy(loading = false) }

                }.launchIn(viewModelScope)
        }
    }


    override fun onEventDispatcher(intent: SubmittedDetailsContract.Intent) {
        when (intent) {
            SubmittedDetailsContract.Intent.BackToSubmits -> {
                viewModelScope.launch {
                   direction.back()
                }
            }

            is SubmittedDetailsContract.Intent.GetSubmittedItems -> {
                repository.getSavedComponents(intent.draftId, intent.userId).onEach {
                    it.onSuccess { list ->
                        myLog2("success get saved list:$list")
                        uiState.update {
                            it.copy(list = list)
                        }
                    }
                    it.onFailure {
                        myLog2("exception:${it.localizedMessage}")
                    }
                }.launchIn(viewModelScope)
        }
    }
}