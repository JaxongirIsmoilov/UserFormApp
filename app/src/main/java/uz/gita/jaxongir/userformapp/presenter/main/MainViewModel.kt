package uz.gita.jaxongir.userformapp.presenter.main

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.onEach
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import uz.gita.jaxongir.userformapp.data.local.MyPref
import uz.gita.jaxongir.userformapp.domain.repository.AppRepository
import javax.inject.Inject

@HiltViewModel
class MainViewModel @Inject constructor(
    private val appRepository: AppRepository,
    private val mainDirection: MainDirection,
    private val pref: MyPref
) : MainContract.ViewModel, ViewModel() {
    override val uiState = MutableStateFlow(MainContract.UIState())

    init {

        uiState.update { it.copy(userName = pref.getUserName()) }

        viewModelScope.launch {
            uiState.update { it.copy(loading = true) }
            appRepository.getComponentsByUserId(pref.getId())
                .onEach {
                    it.onSuccess { components ->
                        uiState.update { it.copy(components = components) }
                    }

                    it.onFailure {
                        // error message
                    }

                            uiState.update { it.copy(loading = false) }

                }
        }
    }

    override fun onEventDispatcher(intent: MainContract.Intent) {
        when (intent) {
            MainContract.Intent.Logout -> {
                viewModelScope.launch {
                    pref.clearData()
                    mainDirection.moveToLogin()
                }
            }
        }
    }
}