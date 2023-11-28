package uz.gita.jaxongir.userformapp.presenter.submitedScreen

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import uz.gita.jaxongir.userformapp.domain.repository.AppRepository
import javax.inject.Inject


@HiltViewModel
class SubmitedScreenViewModelImpl @Inject constructor(
    private val direction: SubmitedScreenDirection,
    private val appRepository: AppRepository,
) : ViewModel(),
    SubmitedScreenContract.ViewModel {
    override val uiState =
        MutableStateFlow(SubmitedScreenContract.UIState())

    init {
        val list = appRepository.getDraftedItems()
        uiState.update { it.copy(list) }
    }


    override fun onEventDispatcher(intent: SubmitedScreenContract.Intent) {
        when (intent) {
            SubmitedScreenContract.Intent.Back -> {
                viewModelScope.launch {
                    direction.back()
                }
            }
        }
    }


}