package uz.gita.jaxongir.userformapp.presenter.login

import android.widget.Toast
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
import javax.inject.Inject

@HiltViewModel
class LoginViewModelImpl @Inject constructor(
    private val appRepository: AppRepository,
    private val loginDirection: LoginDirection,
    private val pref: MyPref
) : ViewModel(), LoginContract.ViewModel {
    override val uiState = MutableStateFlow(LoginContract.UIState())

    override fun onEventDispatcher(intent: LoginContract.Intent) {
        when (intent) {
            is LoginContract.Intent.OnLogin -> {
                viewModelScope.launch {
                    uiState.update { it.copy(loading = true) }
                    appRepository.login(intent.name, intent.password)
                        .onEach {
                            it.onSuccess {
                                pref.saveUserName(intent.name)
                                pref.setLogin(true)
                                loginDirection.moveToMain()
                            }
                            it.onFailure {
                                Toast.makeText(
                                    intent.context,
                                    "Something went wrong",
                                    Toast.LENGTH_SHORT
                                )
                                    .show()
                            }

                            uiState.update { it.copy(loading = false) }
                        }.launchIn(viewModelScope)

                }
            }
        }
    }
}