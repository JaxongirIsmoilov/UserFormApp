package uz.gita.jaxongir.userformapp.presenter.intro_screen

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import uz.gita.jaxongir.userformapp.data.local.pref.MyPref
import javax.inject.Inject

@HiltViewModel
class EntryScreenViewModelImpl @Inject constructor(
    private val direction: EntryScreenDirection,
    private val pref: MyPref
) : ViewModel(), EntryScreenContract.ViewModel {

    override val uiState = MutableStateFlow(EntryScreenContract.UIState())

    init {
        uiState.update { it.copy(userName = pref.getUserName()) }
    }


    override fun onEventDispatcher(intent: EntryScreenContract.Intent) {
        when (intent) {
            EntryScreenContract.Intent.MoveToAdd -> {
                viewModelScope.launch {
                    direction.moveToFormScreen()
                }
            }

            EntryScreenContract.Intent.MoveToDraft -> {
                viewModelScope.launch {
                    direction.moveToDraftScreen()
                }
            }

            EntryScreenContract.Intent.MoveToSubmit -> {
                viewModelScope.launch {
                    direction.moveToSubmitScreen()
                }
            }
        }
    }

}