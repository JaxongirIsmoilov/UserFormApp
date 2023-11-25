package uz.gita.jaxongir.userformapp.presenter.splash

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch
import javax.inject.Inject


@HiltViewModel
class SplashViewModelImpl @Inject constructor(
    private val direction: SplashDirection
) : ViewModel(), SplashContract.ViewModel {
    override fun onEventDispatchers(intent: SplashContract.Intent) {
        when (intent) {
            SplashContract.Intent.MOveToLogin -> {
                viewModelScope.launch {
                    delay(1500L)
                    direction.moveToLogin()
                }
            }
        }
    }
}