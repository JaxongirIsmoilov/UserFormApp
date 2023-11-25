package uz.gita.jaxongir.userformapp.di

import dagger.Binds
import dagger.Module
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import uz.gita.jaxongir.userformapp.utills.navigation.AppNavigationDispatcher
import uz.gita.jaxongir.userformapp.utills.navigation.AppNavigationHandler
import uz.gita.jaxongir.userformapp.utills.navigation.AppNavigator
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
interface NavigationModule {

    @[Binds Singleton]
    fun bindHandler(impl: AppNavigationDispatcher): AppNavigationHandler

    @[Binds Singleton]
    fun bindNavigator(impl: AppNavigationDispatcher): AppNavigator
}