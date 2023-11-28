package uz.gita.jaxongir.userformapp.presenter.detailsscreen

import dagger.hilt.android.lifecycle.HiltViewModel
import uz.gita.jaxongir.userformapp.domain.repository.AppRepository
import javax.inject.Inject


@HiltViewModel
class DetailsViewModelImpl @Inject constructor(
    val direction: DetailsDirection,
    val repository: AppRepository
)  {
}