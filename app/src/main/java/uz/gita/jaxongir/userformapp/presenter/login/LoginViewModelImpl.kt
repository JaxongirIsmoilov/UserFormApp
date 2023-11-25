package uz.gita.jaxongir.userformapp.presenter.login

import androidx.lifecycle.ViewModel
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow

@HiltViewModel
class LoginViewModelImpl:ViewModel(), LoginContract.ViewModel {
    override val uiState = MutableStateFlow(LoginContract.UIState())

    override fun onEventDispatcher(intent: LoginContract.Intent) {
        when(intent){
            is LoginContract.Intent.OnLogin -> {

            }
        }
    }
}