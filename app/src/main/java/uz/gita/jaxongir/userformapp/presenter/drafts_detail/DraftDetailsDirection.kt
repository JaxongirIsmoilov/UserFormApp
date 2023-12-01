package uz.gita.jaxongir.userformapp.presenter.drafts_detail

import uz.gita.jaxongir.userformapp.utills.navigation.AppNavigator
import javax.inject.Inject


interface DraftDetailsDirection {
    suspend fun backToDraftsListScreen()
}

class DraftDetailsDirectionImpl @Inject constructor(
    val appNavigator: AppNavigator
) : DraftDetailsDirection {
    override suspend fun backToDraftsListScreen() {
        appNavigator.back()
    }
}