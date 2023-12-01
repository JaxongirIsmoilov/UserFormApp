package uz.gita.jaxongir.userformapp.presenter.intro_screen

import uz.gita.jaxongir.userformapp.data.local.pref.MyPref
import uz.gita.jaxongir.userformapp.presenter.drafts_list.DraftScreen
import uz.gita.jaxongir.userformapp.presenter.login.LoginScreen
import uz.gita.jaxongir.userformapp.presenter.main.MainScreen
import uz.gita.jaxongir.userformapp.presenter.submitedScreen.SubmitedScreen
import uz.gita.jaxongir.userformapp.utills.navigation.AppNavigator
import javax.inject.Inject

interface EntryScreenDirection {
    suspend fun moveToFormScreen()
    suspend fun moveToSubmitScreen()
    suspend fun moveToDraftScreen()
    suspend fun moveToLogin()

}

class EntryScreenDirectionImpl @Inject constructor(
    private val appNavigator: AppNavigator,
    private val myPref: MyPref
) : EntryScreenDirection {
    override suspend fun moveToFormScreen() {
        appNavigator.addScreen(MainScreen(myPref))
    }

    override suspend fun moveToSubmitScreen() {
        appNavigator.addScreen(SubmitedScreen())
    }

    override suspend fun moveToDraftScreen() {
        appNavigator.addScreen(DraftScreen())
    }

    override suspend fun moveToLogin() {
        appNavigator.replaceScreen(LoginScreen())
    }

}
