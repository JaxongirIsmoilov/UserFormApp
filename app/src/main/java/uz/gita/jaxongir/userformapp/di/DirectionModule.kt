package uz.gita.jaxongir.userformapp.di

import dagger.Binds
import dagger.Module
import dagger.hilt.InstallIn
import dagger.hilt.android.components.ViewModelComponent
import uz.gita.jaxongir.userformapp.presenter.submitteddetails.DetailsDirection
import uz.gita.jaxongir.userformapp.presenter.submitteddetails.DetailsDirectionImpl
import uz.gita.jaxongir.userformapp.presenter.drafts_detail.DraftDetailsDirection
import uz.gita.jaxongir.userformapp.presenter.drafts_detail.DraftDetailsDirectionImpl
import uz.gita.jaxongir.userformapp.presenter.drafts_list.DraftDirection
import uz.gita.jaxongir.userformapp.presenter.drafts_list.DraftDirectionImpl
import uz.gita.jaxongir.userformapp.presenter.intro_screen.EntryScreenDirection
import uz.gita.jaxongir.userformapp.presenter.intro_screen.EntryScreenDirectionImpl
import uz.gita.jaxongir.userformapp.presenter.login.LoginDirection
import uz.gita.jaxongir.userformapp.presenter.login.LoginDirectionIMpl
import uz.gita.jaxongir.userformapp.presenter.main.MainDirection
import uz.gita.jaxongir.userformapp.presenter.main.MainDirectionImpl
import uz.gita.jaxongir.userformapp.presenter.splash.SplashDirection
import uz.gita.jaxongir.userformapp.presenter.splash.SplashDirectionImpl
import uz.gita.jaxongir.userformapp.presenter.submitedScreen.SubmitedScreenDirection
import uz.gita.jaxongir.userformapp.presenter.submitedScreen.SubmitedScreenDirectionImpl

@Module
@InstallIn(ViewModelComponent::class)
interface DirectionModule {

    @Binds
    fun bindEntryScreen(impl: EntryScreenDirectionImpl): EntryScreenDirection

    @Binds
    fun bindDraftsListScreen(impl: DraftDirectionImpl): DraftDirection

    @Binds
    fun bindDraftsDetailScreen(impl: DraftDetailsDirectionImpl): DraftDetailsDirection

    @Binds
    fun splashDirection(impl: SplashDirectionImpl): SplashDirection

    @Binds
    fun bindsLoginDirection(impl: LoginDirectionIMpl): LoginDirection

    @Binds
    fun bindsMainDirection(impl: MainDirectionImpl): MainDirection

    @Binds
    fun bindSubmitedDirection(impl: SubmitedScreenDirectionImpl): SubmitedScreenDirection

    @Binds
    fun bindSubmitedDetailsDirection(impl: DetailsDirectionImpl): DetailsDirection


}

