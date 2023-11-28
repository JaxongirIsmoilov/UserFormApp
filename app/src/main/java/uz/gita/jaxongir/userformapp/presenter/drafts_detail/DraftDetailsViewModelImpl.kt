package uz.gita.jaxongir.userformapp.presenter.drafts_detail

import android.widget.Toast
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.flow.launchIn
import kotlinx.coroutines.flow.onEach
import kotlinx.coroutines.launch
import uz.gita.jaxongir.userformapp.data.local.pref.MyPref
import uz.gita.jaxongir.userformapp.domain.repository.AppRepository
import javax.inject.Inject

@HiltViewModel
class DraftDetailsViewModelImpl @Inject constructor(
    private val draftDispatcher: DraftDetailsDirection,
    private val appRepository: AppRepository,
    private val myShared: MyPref
) : ViewModel(), DraftScreenContract.ViewModel {
    override fun onEventDispatcher(intent: DraftScreenContract.Intent) {
        when (intent) {
            is DraftScreenContract.Intent.SaveAsDraft -> {
                viewModelScope.launch {
                    appRepository.addAsDraft(intent.entity).onEach {
                        it.onSuccess { draftDispatcher.backToDraftsListScreen() }
                    }.launchIn(viewModelScope)
                    Toast.makeText(intent.context, "Saved as draft", Toast.LENGTH_SHORT).show()
                }
            }

            is DraftScreenContract.Intent.SaveAsSaved -> {
                viewModelScope.launch {
                    appRepository.addAsSaved(intent.entity).onEach {
                        it.onSuccess { draftDispatcher.backToDraftsListScreen() }
                    }.launchIn(viewModelScope)
                    Toast.makeText(intent.context, "Saved as saved", Toast.LENGTH_SHORT).show()
                }
            }

            DraftScreenContract.Intent.Back -> {
                viewModelScope.launch {
                    draftDispatcher.backToDraftsListScreen()
                }
            }

            is DraftScreenContract.Intent.UpdateComponent -> {
                viewModelScope.launch {
                    appRepository.updateComponent(intent.componentData)
                        .onEach {
                            it.onSuccess {
//                                appRepository.getComponentsByUserId(myShared.getId())
//                                    .onEach {
//                                        it.onSuccess { components ->
//
//                                        }
//
//                                        it.onFailure {
//                                            // error message
//                                        }
//
//                                    }.collect()
                            }
                                .onFailure {

                                }
                        }.collect()
                }
            }
        }
    }
}