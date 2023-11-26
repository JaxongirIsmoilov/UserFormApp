package uz.gita.jaxongir.userformapp.presenter.main

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

            is MainContract.Intent.CheckedComponent -> {
                var isVisible: Boolean = true
                viewModelScope.launch {
                    intent.componentData.conditions.forEach {
                        checkForCondition(it.id)
                        when (it.operator) {
                            "Not equal" -> {
                                if (!(uiState.value.checkedComponent?.enteredValue == it.value && isVisible)) {
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

                            "Equal" -> {
                                if (!(uiState.value.checkedComponent?.enteredValue != it.value && isVisible)) {
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
                                if (intent.componentData.textFieldType == TextFieldType.Number) {
                                    if (!((uiState.value.checkedComponent?.enteredValue?.toInt()
                                            ?: 0) >= it.value.toInt() && isVisible)
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
                                    if ((uiState.value.checkedComponent?.enteredValue?.length
                                            ?: 0) >= it.value.length && isVisible
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
                                if (intent.componentData.textFieldType == TextFieldType.Number) {
                                    if ((uiState.value.checkedComponent?.enteredValue?.toInt()
                                            ?: 0) <= it.value.toInt() && isVisible
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
                                    if ((uiState.value.checkedComponent?.enteredValue?.length
                                            ?: 0) <= it.value.length && isVisible
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
                            it
                                .onSuccess {
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

    private fun checkForCondition(value: String) {
        uiState.value.components.forEach { data ->
            if (value == data.idEnteredByUser) {
                uiState.update { it.copy(checkedComponent = data) }
            }

        }
    }


}