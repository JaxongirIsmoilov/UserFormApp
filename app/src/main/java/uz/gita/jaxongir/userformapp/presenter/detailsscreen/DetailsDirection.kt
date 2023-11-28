package uz.gita.jaxongir.userformapp.presenter.detailsscreen

import uz.gita.jaxongir.userformapp.utills.navigation.AppNavigator
import javax.inject.Inject

interface DetailsDirection {
    suspend fun back()
}

class DetailsDirectionImpl @Inject constructor(
    private val appNavigator: AppNavigator
): DetailsDirection{
    override suspend fun back() {
        appNavigator.back()
    }

}