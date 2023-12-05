package uz.gita.jaxongir.userformapp.presenter.submitedScreen

import uz.gita.jaxongir.userformapp.data.model.ComponentData
import uz.gita.jaxongir.userformapp.presenter.submitteddetails.DetailsScreen
import uz.gita.jaxongir.userformapp.utills.navigation.AppNavigator
import javax.inject.Inject

interface SubmitedScreenDirection {
    suspend fun back()
    suspend fun moveToComponenetDetailScreen(list: List<ComponentData>)
}


class SubmitedScreenDirectionImpl @Inject constructor(
    private val appNavigator: AppNavigator
) : SubmitedScreenDirection {
    override suspend fun back() {
        appNavigator.back()
    }

    override suspend fun moveToComponenetDetailScreen(list: List<ComponentData>) {
        appNavigator.addScreen(DetailsScreen(list))
    }
}