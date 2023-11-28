package uz.gita.jaxongir.userformapp.presenter.detailsscreen

import android.content.Context
import android.widget.Toast
import androidx.compose.ui.platform.LocalContext
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
import uz.gita.jaxongir.userformapp.presenter.main.MainContract
import java.util.prefs.AbstractPreferences
import javax.inject.Inject


@HiltViewModel
class DetailsViewModelImpl @Inject constructor(
    private val direction: DetailsDirection,
    private val repository: AppRepository,
    private val pref: MyPref
) : DetailsContract.ViewModel, ViewModel() {
    override val uiState = MutableStateFlow(DetailsContract.UIState())


    init {

        uiState.update { it.copy(userName = pref.getUserName()) }

        viewModelScope.launch {
            uiState.update { it.copy(loading = true) }
            repository.getComponentsByUserId(pref.getId())
                .onEach {
                    it.onSuccess { components ->
                        uiState.update { it.copy(submittedDetails = components) }
                    }
                    it.onFailure {

                    }
                    uiState.update { it.copy(loading = false) }

                }.launchIn(viewModelScope)
        }
    }


    override fun onEventDispatcher(intent: DetailsContract.Intent) {
        when (intent) {
            DetailsContract.Intent.BackToSubmits -> {
                viewModelScope.launch {
                   direction.back()
                }
            }
        }
    }
}