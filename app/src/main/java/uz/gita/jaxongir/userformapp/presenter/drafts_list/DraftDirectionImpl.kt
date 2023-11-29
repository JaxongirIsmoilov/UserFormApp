package uz.gita.jaxongir.userformapp.presenter.drafts_list

import uz.gita.jaxongir.userformapp.data.local.pref.MyPref
import uz.gita.jaxongir.userformapp.data.model.ComponentData
import uz.gita.jaxongir.userformapp.presenter.drafts_detail.DraftDetails
import uz.gita.jaxongir.userformapp.utills.navigation.AppNavigator
import javax.inject.Inject

class DraftDirectionImpl @Inject constructor(
    private val appNavigator: AppNavigator,
    private val myPref: MyPref
) : DraftDirection {
    override suspend fun backToMain() {
        appNavigator.back()
    }

    override suspend fun draftDetails(list: List<ComponentData>) {
        appNavigator.addScreen(DraftDetails(list, myPref))
    }
}