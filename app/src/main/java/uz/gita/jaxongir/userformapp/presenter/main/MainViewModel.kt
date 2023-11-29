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
                var isVisibleEqual: Boolean = true
                var isVisibleNot = true
                var isVisibleMore = true
                var isVisibleLess = true
                viewModelScope.launch {
                    intent.componentData.connectedIds.forEachIndexed { index, item ->
                        findingCheckedComponent(item)
                        when (intent.componentData.operators[index]) {
                            "Equal" -> {

                                if (!(uiState.value.checkedComponent?.enteredValue == intent.componentData.connectedValues[index] || isVisibleEqual)) {
                                    isVisibleEqual = false
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


                                if (!(uiState.value.checkedComponent?.enteredValue != intent.componentData.connectedValues[index] || isVisibleNot)) {
                                    myLog("not equal")
                                    isVisibleNot = false
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
                                            ?: 0) >= intent.componentData.connectedValues[index].toInt() || isVisibleMore)
                                    ) {

                                        isVisibleMore = false
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
                                            ?: 0) >= intent.componentData.connectedValues[index].length || isVisibleMore
                                                )
                                    ) {
                                        isVisibleMore = false
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

                                if (uiState.value.checkedComponent?.textFieldType == TextFieldType.Number) {
                                    myLog("Entered Value : ${uiState.value.checkedComponent!!.enteredValue}")
                                    myLog("Connected Value : ${intent.componentData.connectedValues[index]}")
                                    if (!((uiState.value.checkedComponent?.enteredValue?.toInt()
                                            ?: 0) <= intent.componentData.connectedValues[index].toInt() || isVisibleLess
)                                    ) {
                                        isVisibleLess = false
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
                                            ?: 0) <= intent.componentData.connectedValues[index].length || isVisibleLess
)                                    ) {
                                        isVisibleLess = false
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

            is MainContract.Intent.CheckRowComponent -> {
                intent.componentData // check row id
            }
        }
    }

    private fun findingCheckedComponent(componentId: String) {
        myLog("checking value : $componentId")
        uiState.value.components.forEach { data ->
            myLog("Inside For each : ${data.idEnteredByUser}\t ${componentId} , ${data.isVisible}" )
            if (data.idEnteredByUser == componentId) {
                myLog("check data inside of if visibility ${data.isVisible}")
                uiState.update { it.copy(checkedComponent = data) }
            }
        }
    }

}