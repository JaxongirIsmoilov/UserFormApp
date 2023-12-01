package uz.gita.jaxongir.userformapp.presenter.submitedScreen

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
import uz.gita.jaxongir.userformapp.utills.myLog2
import javax.inject.Inject


@HiltViewModel
class SubmitedScreenViewModelImpl @Inject constructor(
    private val direction: SubmitedScreenDirection,
    private val appRepository: AppRepository,
    private val myPref: MyPref
) : ViewModel(),
    SubmitedScreenContract.ViewModel {
    override val uiState =
        MutableStateFlow(SubmitedScreenContract.UIState())

    init {
        appRepository.getSavedComponents().onEach {
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


    override fun onEventDispatcher(intent: SubmitedScreenContract.Intent) {
        when (intent) {
            SubmitedScreenContract.Intent.Back -> {
                viewModelScope.launch {
                    direction.back()
                }
            }

            is SubmitedScreenContract.Intent.ClickItem -> {
                viewModelScope.launch {
                    direction.moveToComponenetDetailScreen(intent.list)
                }
            }
        }
    }


}