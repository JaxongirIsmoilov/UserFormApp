package uz.gita.jaxongir.userformapp.presenter.login

import uz.gita.jaxongir.userformapp.presenter.main.MainScreen
import uz.gita.jaxongir.userformapp.utills.myLog
import uz.gita.jaxongir.userformapp.utills.navigation.AppNavigator
import javax.inject.Inject
import javax.inject.Singleton

interface LoginDirection {
    suspend fun moveToMain()
}

@Singleton
class LoginDirectionIMpl @Inject constructor(
    val appNavigator: AppNavigator
):LoginDirection{
    override suspend fun moveToMain() {
        myLog("Direction worked")
        appNavigator.replaceScreen(MainScreen())
    }

}