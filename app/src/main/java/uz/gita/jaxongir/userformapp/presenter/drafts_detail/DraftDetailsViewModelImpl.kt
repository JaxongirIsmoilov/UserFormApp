package uz.gita.jaxongir.userformapp.presenter.drafts_detail

import android.util.Log
import android.widget.Toast
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.flow.launchIn
import kotlinx.coroutines.flow.onEach
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import uz.gita.jaxongir.userformapp.data.enums.TextFieldType
import uz.gita.jaxongir.userformapp.data.local.pref.MyPref
import uz.gita.jaxongir.userformapp.domain.repository.AppRepository
import uz.gita.jaxongir.userformapp.utills.myLog
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
                        myLog2("successs add draft vm")
                        it.onSuccess { draftDispatcher.backToDraftsListScreen() }
                    }.launchIn(viewModelScope)
                    Toast.makeText(intent.context, "Saved as draft", Toast.LENGTH_SHORT).show()
                }
            }

            is DraftScreenContract.Intent.SaveAsSaved -> {
                viewModelScope.launch {

                    appRepository.addAsSaved(intent.entity).onEach {
                        it.onSuccess {
                            myLog2("successs add submitted vm")
                            draftDispatcher.backToDraftsListScreen() }
                    }.launchIn(viewModelScope)
                    Toast.makeText(intent.context, "Saved as saved", Toast.LENGTH_SHORT).show()
                }
            }

            DraftScreenContract.Intent.Back -> {
                viewModelScope.launch {
                    draftDispatcher.backToDraftsListScreen()
                }
            }

            is DraftScreenContract.Intent.CheckedComponent -> {
                var contentVisible = true
                viewModelScope.launch {
                    intent.component.connectedIds.forEachIndexed { index, item ->
                        Log.d("AJAX", "onEventDispatcher: $index")
                        findingCheckedComponent(item)
                        when (intent.component.operators[index]) {
                            "Equal" -> {
                                contentVisible =
                                    uiState.value.checkedComponent?.enteredValue == intent.component.connectedValues[index] && contentVisible
                            }

                            "Not" -> {
                                contentVisible =
                                    uiState.value.checkedComponent?.enteredValue != intent.component.connectedValues[index] && contentVisible
                            }

                            "More" -> {
                                if (uiState.value.checkedComponent?.textFieldType == TextFieldType.Number) {
                                    contentVisible =
                                        (uiState.value.checkedComponent?.enteredValue?.toInt()
                                            ?: 0) >= intent.component.connectedValues[index].toInt() && contentVisible
                                } else {
                                    contentVisible =
                                        (uiState.value.checkedComponent?.enteredValue?.length
                                            ?: 0) >= intent.component.connectedValues[index].length && contentVisible
                                }
                            }

                            "Less" -> {
                                if (uiState.value.checkedComponent?.textFieldType == TextFieldType.Number) {
                                    Log.d(
                                        "AJAX", "onEventDispatcher: ${
                                            (uiState.value.checkedComponent?.enteredValue?.toInt()
                                                ?: 0) <= intent.component.connectedValues[index].toInt() && contentVisible
                                        }"
                                    )

                                    Log.d(
                                        "TAG",
                                        "onEventDispatcher: ${intent.component.connectedValues[index].toInt()}"
                                    )

                                    contentVisible =
                                        (uiState.value.checkedComponent?.enteredValue?.toInt()
                                            ?: 0) <= intent.component.connectedValues[index].toInt() && contentVisible
                                } else {
                                    contentVisible =
                                        (uiState.value.checkedComponent?.enteredValue?.length
                                            ?: 0) <= intent.component.connectedValues[index].length && contentVisible
                                }
                            }
                        }

                    }

                    appRepository.updateComponent(
                        intent.component.copy(
                            isVisible = contentVisible,
                        )
                    ).onEach {
                        it.onSuccess {
                            appRepository.getComponentsByUserId(myShared.getId())
                                .onEach {
                                    it.onSuccess { components ->
                                        val sortedList = components.sortedBy {
                                            it.locId
                                        }
                                        uiState.update { it.copy(list = sortedList) }
                                        contentVisible = true
                                    }

                                    it.onFailure {
                                    }


                                }.collect()
                        }
                    }.collect()
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

    private fun findingCheckedComponent(componentId: String) {
        myLog("checking value : $componentId")
        uiState.value.list.forEach { data ->
            if (data.idEnteredByUser == componentId) {
                myLog("check data inside of if $data")
                uiState.update { it.copy(checkedComponent = data) }
            }
        }
    }


}