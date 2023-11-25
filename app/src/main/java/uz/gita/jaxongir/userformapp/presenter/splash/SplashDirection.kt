package uz.gita.jaxongir.userformapp.presenter.splash

import uz.gita.jaxongir.userformapp.presenter.login.LoginScreen
import uz.gita.jaxongir.userformapp.presenter.main.MainScreen
import uz.gita.jaxongir.userformapp.utills.navigation.AppNavigator
import javax.inject.Inject
import javax.inject.Singleton

interface SplashDirection {
    suspend fun moveToLogin()
    suspend fun moveToMain()
}


@Singleton
class SplashDirectionImpl @Inject constructor(
    private val appNavigator: AppNavigator
) : SplashDirection {
    override suspend fun moveToLogin() {
        appNavigator.replaceScreen(LoginScreen())
    }

    override suspend fun moveToMain() {
        appNavigator.replaceScreen(MainScreen())
    }

}