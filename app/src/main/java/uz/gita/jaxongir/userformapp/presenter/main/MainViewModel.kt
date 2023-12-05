package uz.gita.jaxongir.userformapp.presenter.main

import android.annotation.SuppressLint
import android.content.Context
import android.util.Log
import android.widget.Toast
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import dagger.hilt.android.lifecycle.HiltViewModel
import dagger.hilt.android.qualifiers.ApplicationContext
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.flow.launchIn
import kotlinx.coroutines.flow.onCompletion
import kotlinx.coroutines.flow.onEach
import kotlinx.coroutines.flow.onStart
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import uz.gita.jaxongir.userformapp.data.enums.TextFieldType
import uz.gita.jaxongir.userformapp.data.local.pref.MyPref
import uz.gita.jaxongir.userformapp.data.local.room.entity.FormRequest
import uz.gita.jaxongir.userformapp.domain.repository.AppRepository
import uz.gita.jaxongir.userformapp.utills.myLog
import javax.inject.Inject

@HiltViewModel
@SuppressLint("StaticFieldLeak")
class MainViewModel @Inject constructor(
    private val appRepository: AppRepository,
    private val mainDirection: MainDirection,
    private val pref: MyPref,
    @ApplicationContext private val context: Context
) : MainContract.ViewModel, ViewModel() {
    override val uiState = MutableStateFlow(MainContract.UIState())


    init {
        uiState.update { it.copy(userName = pref.getUserName()) }
        viewModelScope.launch {
            uiState.update { it.copy(loading = true) }
            appRepository.getComponentsByUserId(pref.getId())
                .onStart { uiState.update { it.copy(isLoading = true) } }
                .onCompletion { uiState.update { it.copy(isLoading = false) } }
                .onEach {
                    it.onSuccess { components ->
                        val sortedList = components.sortedBy {
                            it.locId
                        }
                        uiState.update { it.copy(components = sortedList) }
                    }

                    it.onFailure {
                        myLog("failrue viewmodel")
                    }

                    uiState.update { it.copy(loading = false) }

                }.launchIn(viewModelScope)
        }
    }

    override fun onEventDispatcher(intent: MainContract.Intent) {
        when (intent) {
            is MainContract.Intent.ClickAsDraft -> {
                viewModelScope.launch {
                    appRepository.addDraftedItems(
                        FormRequest(intent.list, true, pref.getId())
                    ).onEach {
                        it.onSuccess {
                            mainDirection.back()
                        }
                        it.onFailure {}
                    }.launchIn(viewModelScope)
                }
            }
            is MainContract.Intent.addComponentData ->{

            }


            is MainContract.Intent.ClickAsSaved -> {
                viewModelScope.launch {
                    appRepository.addSavedItems(
                        FormRequest(intent.list, isDraft = false, pref.getId())
                    ).onEach {
                        it.onSuccess {
                            mainDirection.back()
                        }
                        it.onFailure {
                            Toast.makeText(intent.context, "exception:$it", Toast.LENGTH_SHORT)
                                .show()
                        }
                    }.launchIn(viewModelScope)

                }
            }

            is MainContract.Intent.CheckedComponent -> {
                var contentVisible = true
                viewModelScope.launch {
                    intent.componentData.connectedIds.forEachIndexed { index, item ->
                        Log.d("AJAX", "onEventDispatcher: $index")
                        findingCheckedComponent(item)
                        when (intent.componentData.operators[index]) {
                            "Equal" -> {
                                contentVisible =
                                    uiState.value.checkedComponent?.enteredValue == intent.componentData.connectedValues[index] && contentVisible
                            }

                            "In" -> {
                                contentVisible =
                                    intent.componentData.inValues.contains(uiState.value.checkedComponent?.enteredValue) == true && contentVisible
                            }

                            "!In" -> {
                                contentVisible =
                                    intent.componentData.inValues.contains(uiState.value.checkedComponent?.enteredValue) == false && contentVisible
                            }

                            "Not" -> {
                                contentVisible =
                                    uiState.value.checkedComponent?.enteredValue != intent.componentData.connectedValues[index] && contentVisible
                            }

                            "More" -> {
                                if (uiState.value.checkedComponent?.textFieldType == TextFieldType.Number) {
                                    contentVisible =
                                        (uiState.value.checkedComponent?.enteredValue?.toInt()
                                            ?: 0) >= intent.componentData.connectedValues[index].toInt() && contentVisible
                                } else {
                                    contentVisible =
                                        (uiState.value.checkedComponent?.enteredValue?.length
                                            ?: 0) >= intent.componentData.connectedValues[index].length && contentVisible
                                }
                            }

                            "Less" -> {
                                if (uiState.value.checkedComponent?.textFieldType == TextFieldType.Number) {
                                    Log.d(
                                        "AJAX", "onEventDispatcher: ${
                                            (uiState.value.checkedComponent?.enteredValue?.toInt()
                                                ?: 0) <= intent.componentData.connectedValues[index].toInt() && contentVisible
                                        }"
                                    )

                                    Log.d(
                                        "TAG",
                                        "onEventDispatcher: ${intent.componentData.connectedValues[index].toInt()}"
                                    )

                                    contentVisible =
                                        (uiState.value.checkedComponent?.enteredValue?.toInt()
                                            ?: 0) <= intent.componentData.connectedValues[index].toInt() && contentVisible
                                } else {
                                    contentVisible =
                                        (uiState.value.checkedComponent?.enteredValue?.length
                                            ?: 0) <= intent.componentData.connectedValues[index].length && contentVisible
                                }
                            }
                        }

                    }

                    appRepository.updateComponent(
                        intent.componentData.copy(
                            isVisible = contentVisible,
                        )
                    ).onEach {
                        it.onSuccess {
                            appRepository.getComponentsByUserId(pref.getId())
                                .onEach {
                                    it.onSuccess { components ->
                                        val sortedList = components.sortedBy {
                                            it.locId
                                        }
                                        uiState.update { it.copy(components = sortedList) }
                                        contentVisible = true
                                    }

                                    it.onFailure {
                                    }

                                    uiState.update { it.copy(loading = false) }

                                }.collect()
                        }
                    }.collect()
                }
            }

            is MainContract.Intent.UpdateComponent -> {
                viewModelScope.launch {
                    appRepository.updateComponent(intent.componentData)
                        .onEach {
                            it.onSuccess {
                                appRepository.getComponentsByUserId(pref.getId())
                                    .onEach {
                                        it.onSuccess { components ->
                                            val sortedList = components.sortedBy {
                                                it.locId
                                            }
                                            uiState.update { it.copy(components = sortedList) }
                                        }

                                        it.onFailure {
                                            // error message
                                        }

                                        uiState.update { it.copy(loading = false) }
                                    }.collect()
                            }
                                .onFailure {

                                }
                        }.collect()
                }
            }

            is MainContract.Intent.GetAllRowItems -> {
                appRepository.getComponentsByUserId(intent.rowId)
                    .onStart { uiState.update { it.copy(isLoading = true) } }
                    .onCompletion { uiState.update { it.copy(isLoading = false) } }
                    .onEach {
                        it.onSuccess { list ->
                            uiState.update {
                                it.copy(rowComponents = list)
                            }
                        }
                        it.onFailure {
                            Toast.makeText(context, "Cannot be loaded!", Toast.LENGTH_SHORT).show()
                        }
                    }.launchIn(viewModelScope)
            }
        }
    }

    private fun findingCheckedComponent(componentId: String) {
        myLog("checking value : $componentId")
        uiState.value.components.forEach { data ->
            if (data.idEnteredByUser == componentId) {
                myLog("check data inside of if $data")
                uiState.update { it.copy(checkedComponent = data) }
            }
        }
    }


}