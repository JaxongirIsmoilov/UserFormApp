package uz.gita.jaxongir.userformapp.presenter.intro_screen

import android.annotation.SuppressLint
import android.content.Context
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import dagger.hilt.android.lifecycle.HiltViewModel
import dagger.hilt.android.qualifiers.ApplicationContext
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
@SuppressLint("StaticFieldLeak")
class EntryScreenViewModelImpl @Inject constructor(
    private val direction: EntryScreenDirection,
    private val pref: MyPref,
    private val repository: AppRepository,
    @ApplicationContext private val  context: Context
) : ViewModel(), EntryScreenContract.ViewModel {

    override val uiState = MutableStateFlow(EntryScreenContract.UIState())

    init {
        repository.hasUserInFireBase(pref.getId()).onEach {
            myLog2("state:$it")
                uiState.update { it.copy(userName = pref.getUserName()) }

        }.launchIn(viewModelScope)

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

            EntryScreenContract.Intent.Logout -> {
                viewModelScope.launch {
                    pref.clearData()
                    direction.moveToLogin()
                }

            }
        }
    }

}