package uz.gita.jaxongir.userformapp.presenter.main

import uz.gita.jaxongir.userformapp.presenter.login.LoginScreen
import uz.gita.jaxongir.userformapp.utills.navigation.AppNavigator
import javax.inject.Inject

interface MainDirection {
    suspend fun moveToLogin()
}

class MainDirectionImpl @Inject constructor(
    private val appNavigator: AppNavigator
) : MainDirection{
    override suspend fun moveToLogin() {
        appNavigator.replaceScreen(LoginScreen())
    }


}