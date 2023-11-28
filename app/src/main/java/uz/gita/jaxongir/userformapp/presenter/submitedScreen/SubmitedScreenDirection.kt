package uz.gita.jaxongir.userformapp.presenter.submitedScreen

import uz.gita.jaxongir.userformapp.utills.navigation.AppNavigator
import javax.inject.Inject

interface SubmitedScreenDirection {
    suspend fun back()

}


class SubmitedScreenDirectionImpl @Inject constructor(
    private val appNavigator: AppNavigator
) : SubmitedScreenDirection {
    override suspend fun back() {
        appNavigator.back()
    }

}