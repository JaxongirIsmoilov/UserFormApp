package uz.gita.jaxongir.userformapp.presenter.login

interface LoginContract {


    interface ViewModel {

    }


    data class UIState(
        val loading: Boolean = false
    )

    interface Intent {
        data class MoveToLogin(val name: String, val password: String)
    }


}