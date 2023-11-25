package uz.gita.jaxongir.userformapp.presenter.splash

import uz.gita.jaxongir.userformapp.presenter.login.LoginScreen
import uz.gita.jaxongir.userformapp.utills.navigation.AppNavigator
import javax.inject.Inject

interface SplashDirection {
    suspend fun moveToLogin()
}


class SplashDirectionImpl @Inject constructor(
    private val appNavigator: AppNavigator
) : SplashDirection {
    override suspend fun moveToLogin() {
        appNavigator.addScreen(LoginScreen())
    }

}