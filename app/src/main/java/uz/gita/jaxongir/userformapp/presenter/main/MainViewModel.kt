package uz.gita.jaxongir.userformapp.presenter.main

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
import javax.inject.Inject

@HiltViewModel
class MainViewModel @Inject constructor(
    private val appRepository: AppRepository,
    private val mainDirection: MainDirection,
    private val pref: MyPref,
) : MainContract.ViewModel, ViewModel() {
    override val uiState = MutableStateFlow(MainContract.UIState())


    init {
        uiState.update { it.copy(userName = pref.getUserName()) }
        viewModelScope.launch {
            uiState.update { it.copy(loading = true) }
            appRepository.getComponentsByUserId(pref.getId())
                .onEach {
                    it.onSuccess { components ->
                        val sortedList = components.sortedBy {
                            it.locId
                        }
                        uiState.update { it.copy(components = sortedList) }
                    }

                    it.onFailure {
                        myLog("failrue viewmodle")
                    }

                    uiState.update { it.copy(loading = false) }

                }.launchIn(viewModelScope)
        }
    }

    override fun onEventDispatcher(intent: MainContract.Intent) {
        when (intent) {
            MainContract.Intent.Logout -> {
                viewModelScope.launch {
                    pref.clearData()
                    mainDirection.moveToLogin()
                }
            }

            is MainContract.Intent.ClickAsDraft -> {
                viewModelScope.launch {
                    appRepository.addAsDraft(intent.entity)
                    Toast.makeText(intent.context, "Saved as Draft", Toast.LENGTH_SHORT).show()
                    mainDirection

                }
            }

            is MainContract.Intent.ClickAsSaved -> {
                viewModelScope.launch {
                    Toast.makeText(intent.context, "Saved as Submitted", Toast.LENGTH_SHORT).show()
                    appRepository.addAsSaved(intent.entity)
                }
            }

            is MainContract.Intent.CheckedComponent -> {
                var isVisible: Boolean = true
                viewModelScope.launch {
                    intent.componentData.connectedIds.forEachIndexed { index, item ->
                        findingCheckedComponent(item)
                        when (intent.componentData.operators[index]) {
                            "Equal" -> {
                                Log.d("AJAX", "onEventDispatcher: Equal")
                                if (!(uiState.value.checkedComponent?.enteredValue == intent.componentData.connectedValues[index] && isVisible)) {
                                    isVisible = false
                                    appRepository.updateComponent(
                                        intent.componentData.copy(
                                            isVisible = false
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
                                                    }

                                                    it.onFailure {
                                                        // error message
                                                    }

                                                    uiState.update { it.copy(loading = false) }

                                                }.collect()
                                        }
                                    }.collect()
                                } else {
                                    appRepository.updateComponent(
                                        intent.componentData.copy(
                                            isVisible = true
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
                                                    }

                                                    it.onFailure {
                                                        // error message
                                                    }

                                                    uiState.update { it.copy(loading = false) }

                                                }.collect()
                                        }
                                    }.collect()
                                }
                            }

                            "Not" -> {
                                Log.d("AJAX", "onEventDispatcher: Not equal")

                                if (!(uiState.value.checkedComponent?.enteredValue != intent.componentData.connectedValues[index] && isVisible)) {
                                    myLog("not equal")
                                    isVisible = false
                                    appRepository.updateComponent(
                                        intent.componentData.copy(
                                            isVisible = false
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
                                                    }

                                                    it.onFailure {
                                                        // error message
                                                    }

                                                    uiState.update { it.copy(loading = false) }

                                                }.collect()
                                        }
                                    }.collect()
                                } else {
                                    appRepository.updateComponent(
                                        intent.componentData.copy(
                                            isVisible = true
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
                                                    }

                                                    it.onFailure {
                                                        // error message
                                                    }

                                                    uiState.update { it.copy(loading = false) }

                                                }.collect()
                                        }
                                    }.collect()
                                }
                            }

                            "More" -> {

                                if (uiState.value.checkedComponent?.textFieldType == TextFieldType.Number) {


                                    if (!((uiState.value.checkedComponent?.enteredValue?.toInt()
                                            ?: 0) >= intent.componentData.connectedValues[index].toInt() && isVisible)
                                    ) {

                                        isVisible = false
                                        appRepository.updateComponent(
                                            intent.componentData.copy(
                                                isVisible = false
                                            )
                                        ).onEach {
                                            it
                                                .onSuccess {
                                                    appRepository.getComponentsByUserId(pref.getId())
                                                        .onEach {
                                                            it.onSuccess { components ->
                                                                val sortedList =
                                                                    components.sortedBy {
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
                                        }.collect()
                                    } else {
                                        appRepository.updateComponent(
                                            intent.componentData.copy(
                                                isVisible = true
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
                                                        }

                                                        it.onFailure {
                                                            // error message
                                                        }

                                                        uiState.update { it.copy(loading = false) }

                                                    }.collect()
                                            }
                                        }.collect()
                                    }
                                } else {
                                    if (!((uiState.value.checkedComponent?.enteredValue?.length
                                            ?: 0) >= intent.componentData.connectedValues[index].length && isVisible
                                                )
                                    ) {
                                        isVisible = false
                                        appRepository.updateComponent(
                                            intent.componentData.copy(
                                                isVisible = false
                                            )
                                        ).onEach {
                                            it.onSuccess {
                                                appRepository.getComponentsByUserId(pref.getId())
                                                    .onEach {
                                                        it.onSuccess { components ->
                                                            val sortedList =
                                                                components.sortedBy { it.locId }
                                                            uiState.update { it.copy(components = sortedList) }
                                                        }

                                                        it.onFailure {
                                                            // error message
                                                        }

                                                        uiState.update { it.copy(loading = false) }

                                                    }.collect()
                                            }
                                        }.collect()
                                    } else {
                                        appRepository.updateComponent(
                                            intent.componentData.copy(
                                                isVisible = true
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
                                                        }
                                                        it.onFailure {
                                                            // error message
                                                        }

                                                        uiState.update { it.copy(loading = false) }

                                                    }.collect()
                                            }
                                        }.collect()
                                    }
                                }

                            }

                            "Less" -> {
                                Log.d("AJAX", "onEventDispatcher: Less")
                                if (uiState.value.checkedComponent?.textFieldType == TextFieldType.Number) {
                                    if (!((uiState.value.checkedComponent?.enteredValue?.toInt()
                                            ?: 0) <= intent.componentData.connectedValues[index].toInt() && isVisible
                                                )
                                    ) {
                                        isVisible = false
                                        appRepository.updateComponent(
                                            intent.componentData.copy(
                                                isVisible = false
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
                                                        }

                                                        it.onFailure {
                                                            // error message
                                                        }

                                                        uiState.update { it.copy(loading = false) }

                                                    }.collect()
                                            }
                                        }.collect()
                                    } else {
                                        appRepository.updateComponent(
                                            intent.componentData.copy(
                                                isVisible = true
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
                                                        }

                                                        it.onFailure {
                                                            // error message
                                                        }

                                                        uiState.update { it.copy(loading = false) }

                                                    }.collect()
                                            }
                                        }.collect()
                                    }
                                } else {
                                    if (!((uiState.value.checkedComponent?.enteredValue?.length
                                            ?: 0) <= intent.componentData.connectedValues[index].length && isVisible
                                                )
                                    ) {
                                        isVisible = false
                                        appRepository.updateComponent(
                                            intent.componentData.copy(
                                                isVisible = false
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
                                                        }

                                                        it.onFailure {
                                                            // error message
                                                        }

                                                        uiState.update { it.copy(loading = false) }

                                                    }.collect()
                                            }
                                        }.collect()
                                    } else {
                                        appRepository.updateComponent(
                                            intent.componentData.copy(
                                                isVisible = true
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
                                                        }

                                                        it.onFailure {
                                                            // error message
                                                        }

                                                        uiState.update { it.copy(loading = false) }

                                                    }.collect()
                                            }
                                        }.collect()
                                    }
                                }
                            }
                        }

                    }

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