package uz.gita.jaxongir.userformapp.presenter.drafts_detail

import android.widget.Toast
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.launchIn
import kotlinx.coroutines.flow.onEach
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import uz.gita.jaxongir.userformapp.data.local.pref.MyPref
import uz.gita.jaxongir.userformapp.domain.repository.AppRepository
import uz.gita.jaxongir.userformapp.utills.myLog2
import javax.inject.Inject

@HiltViewModel
class DraftDetailsViewModelImpl @Inject constructor(
    private val draftDispatcher: DraftDetailsDirection,
    private val appRepository: AppRepository,
    private val myShared: MyPref
) : ViewModel(), DraftScreenContract.ViewModel {
    override val uiState =
        MutableStateFlow(DraftScreenContract.UiState())

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

            is DraftScreenContract.Intent.UpdateList -> {
                uiState.update { it.copy(list = intent.list) }
            }


            is DraftScreenContract.Intent.UpdateComponent -> {
                viewModelScope.launch {
                    appRepository.updateComponent(intent.componentData)
                        .onEach {
                            myLog2("onEach")
                            it.onSuccess {
                                appRepository.getComponentsByUserId(myShared.getId())
                                    .onEach {
                                        it.onSuccess { components ->
                                            myLog2("success update")
                                            val sortedList = components.sortedBy {
                                                it.locId
                                            }
                                            uiState.update { it.copy(list = sortedList) }
                                        }

                                        it.onFailure {
                                            // error message
                                        }

                                    }.launchIn(viewModelScope)
                            }
                                .onFailure {

                                }
                        }.launchIn(viewModelScope)
                }
            }
        }
    }


}