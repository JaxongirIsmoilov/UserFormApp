package uz.gita.jaxongir.userformapp.presenter.main

import uz.gita.jaxongir.userformapp.utills.navigation.AppNavigator
import javax.inject.Inject
import javax.inject.Singleton

interface MainDirection {
    suspend fun back()
}

@Singleton
class MainDirectionImpl @Inject constructor(
    private val appNavigator: AppNavigator,
) : MainDirection {

    override suspend fun back() {
        appNavigator.back()
    }
}