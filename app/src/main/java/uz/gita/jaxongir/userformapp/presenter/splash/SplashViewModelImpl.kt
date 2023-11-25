package uz.gita.jaxongir.userformapp.presenter.splash

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch
import uz.gita.jaxongir.userformapp.data.local.MyPref
import javax.inject.Inject


@HiltViewModel
class SplashViewModelImpl @Inject constructor(
    private val direction: SplashDirection,
    private val pref: MyPref
) : ViewModel() {

    init {
        viewModelScope.launch {
            if (pref.isLogin()) {
                delay(1500L)
                direction.moveToMain()
            }else{
                delay(1500L)
                direction.moveToLogin()
            }
        }
    }
}