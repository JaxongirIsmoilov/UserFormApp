package uz.gita.jaxongir.userformapp.di

import dagger.Binds
import dagger.Module
import dagger.hilt.InstallIn
import dagger.hilt.android.components.ViewModelComponent
import uz.gita.jaxongir.userformapp.data.repository.AppRepositoryImpl
import uz.gita.jaxongir.userformapp.domain.repository.AppRepository

@Module
@InstallIn(ViewModelComponent::class)
interface RepositoryModule {
    @Binds
    fun bindsRepository(impl: AppRepositoryImpl) : AppRepository
}