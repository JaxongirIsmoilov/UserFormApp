package uz.gita.jaxongir.userformapp.presenter.login

import uz.gita.jaxongir.userformapp.utills.navigation.AppNavigator
import javax.inject.Inject

interface LoginDirection {


}
class LoginDirectionIMpl @Inject constructor(
    val appNavigator: AppNavigator
):LoginDirection{

}