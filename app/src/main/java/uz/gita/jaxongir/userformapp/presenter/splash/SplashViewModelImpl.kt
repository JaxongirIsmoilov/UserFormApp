package uz.gita.jaxongir.userformapp.presenter.splash

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.launchIn
import kotlinx.coroutines.flow.onEach
import kotlinx.coroutines.launch
import uz.gita.jaxongir.userformapp.data.local.pref.MyPref
import uz.gita.jaxongir.userformapp.domain.repository.AppRepository
import javax.inject.Inject


@HiltViewModel
class SplashViewModelImpl @Inject constructor(
    private val direction: SplashDirection,
    private val repository: AppRepository,
    private val pref: MyPref
) : ViewModel() {
    init {
        viewModelScope.launch {
            repository.hasUserInFireBase(pref.getId()).onEach {
                if (pref.isLogin()) {
                    delay(1500L)
                    direction.moveToEntry()
                } else {
                    delay(1500L)
                    direction.moveToLogin()
                }
            }.launchIn(viewModelScope)
        }
    }
}