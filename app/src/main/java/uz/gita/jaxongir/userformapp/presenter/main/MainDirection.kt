package uz.gita.jaxongir.userformapp.presenter.main

import uz.gita.jaxongir.userformapp.presenter.login.LoginScreen
import uz.gita.jaxongir.userformapp.utills.navigation.AppNavigator
import javax.inject.Inject
import javax.inject.Singleton

interface MainDirection {
    suspend fun moveToLogin()
    suspend fun back()
}

@Singleton
class MainDirectionImpl @Inject constructor(
    private val appNavigator: AppNavigator
) : MainDirection {
    override suspend fun moveToLogin() {
        appNavigator.replaceScreen(LoginScreen())
    }

    override suspend fun back() {
        appNavigator.back()
    }


}