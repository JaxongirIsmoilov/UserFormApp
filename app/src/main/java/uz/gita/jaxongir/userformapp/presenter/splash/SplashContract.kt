package uz.gita.jaxongir.userformapp.presenter.splash

interface SplashContract {


    interface ViewModel {
        fun onEventDispatchers(intent: Intent)

    }


    interface Intent {
        object MOveToLogin : Intent
    }
}