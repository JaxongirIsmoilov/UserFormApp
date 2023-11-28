package uz.gita.jaxongir.userformapp.presenter.splash

import uz.gita.jaxongir.userformapp.presenter.intro_screen.EntryPreview
import uz.gita.jaxongir.userformapp.presenter.intro_screen.EntryScreen
import uz.gita.jaxongir.userformapp.presenter.login.LoginScreen
import uz.gita.jaxongir.userformapp.presenter.main.MainScreen
import uz.gita.jaxongir.userformapp.utills.navigation.AppNavigator
import javax.inject.Inject
import javax.inject.Singleton

interface SplashDirection {
    suspend fun moveToLogin()
    suspend fun moveToEntry()
}


@Singleton
class SplashDirectionImpl @Inject constructor(
    private val appNavigator: AppNavigator
) : SplashDirection {
    override suspend fun moveToLogin() {
        appNavigator.replaceScreen(LoginScreen())
    }

    override suspend fun moveToEntry() {
        appNavigator.replaceScreen(EntryScreen())
    }

}