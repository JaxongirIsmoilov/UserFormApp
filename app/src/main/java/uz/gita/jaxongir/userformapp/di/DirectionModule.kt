package uz.gita.jaxongir.userformapp.di

import dagger.Binds
import dagger.Module
import dagger.hilt.InstallIn
import dagger.hilt.android.components.ViewModelComponent
import uz.gita.jaxongir.userformapp.presenter.login.LoginDirection
import uz.gita.jaxongir.userformapp.presenter.login.LoginDirectionIMpl
import uz.gita.jaxongir.userformapp.presenter.main.MainDirection
import uz.gita.jaxongir.userformapp.presenter.main.MainDirectionImpl
import uz.gita.jaxongir.userformapp.presenter.splash.SplashDirection
import uz.gita.jaxongir.userformapp.presenter.splash.SplashDirectionImpl

@Module
@InstallIn(ViewModelComponent::class)
interface DirectionModule {
    @Binds
    fun splashDirection(impl: SplashDirectionImpl): SplashDirection
    @Binds
    fun bindsLoginDirection(impl: LoginDirectionIMpl): LoginDirection

    @Binds
    fun bindsMainDirection(impl: MainDirectionImpl):MainDirection
}

