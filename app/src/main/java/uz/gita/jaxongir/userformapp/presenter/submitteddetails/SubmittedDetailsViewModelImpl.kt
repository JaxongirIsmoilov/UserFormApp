package uz.gita.jaxongir.userformapp.presenter.submitteddetails

import android.util.Log
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
import uz.gita.jaxongir.userformapp.data.model.ComponentData
import uz.gita.jaxongir.userformapp.domain.repository.AppRepository
import uz.gita.jaxongir.userformapp.presenter.drafts_detail.DraftScreenContract
import uz.gita.jaxongir.userformapp.presenter.submitedScreen.SubmitedScreenContract
import uz.gita.jaxongir.userformapp.utills.myLog
import uz.gita.jaxongir.userformapp.utills.myLog2
import javax.inject.Inject

@HiltViewModel
class SubmittedDetailsViewModelImpl @Inject constructor(
    private val direction: DetailsDirection,
    private val repository: AppRepository,
    private val pref: MyPref
) : SubmittedDetailsContract.ViewModel, ViewModel() {
    override val uiState = MutableStateFlow(SubmittedDetailsContract.UIState())


    init {
        viewModelScope.launch {
            repository.getComponentsByUserId(pref.getId())
                .onEach { result ->
                    result.onSuccess { components ->
                        uiState.update { it.copy(submittedDetails = components) }
                    }
                    result.onFailure {

                    }

                }.launchIn(viewModelScope)
        }
    }


    override fun onEventDispatcher(intent: SubmittedDetailsContract.Intent) {
        when (intent) {
            SubmittedDetailsContract.Intent.BackToSubmits -> {
                viewModelScope.launch {
                   direction.back()
                }
            }
            is SubmittedDetailsContract.Intent.GetComponents ->{
                val list= arrayListOf<ComponentData>()
                viewModelScope.launch {
                    intent.list.forEach {
                        repository.getComponentByComponentId(it).onEach {
                            it.onFailure {

                            }
                            it.onSuccess {
                                list.add(it)
                            }
                        }.launchIn(viewModelScope)
                    }
                    uiState.update { it.copy(submittedDetails = list) }
                }
            }

            is SubmittedDetailsContract.Intent.CheckedComponent -> {
                var contentVisible = true
                viewModelScope.launch {
                    intent.component.connectedIds.forEachIndexed { index, item ->
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

                    repository.updateComponent(
                        intent.component.copy(
                            isVisible = contentVisible,
                        )
                    ).onEach {
                        it.onSuccess {
                            repository.getComponentsByUserId(pref.getId())
                                .onEach {
                                    it.onSuccess { components ->
                                        val sortedList = components.sortedBy {
                                            it.locId
                                        }
                                        uiState.update { it.copy(submittedDetails = sortedList) }
                                        contentVisible = true
                                    }

                                    it.onFailure {
                                    }


                                }.collect()
                        }
                    }.collect()
                }
            }


            is SubmittedDetailsContract.Intent.UpdateList -> {
                uiState.update { it.copy(listIds = intent.list) }
            }
        }

    }

    private fun findingCheckedComponent(componentId: String) {
        myLog("checking value : $componentId")
        uiState.value.submittedDetails.forEach { data ->
            if (data.idEnteredByUser == componentId) {
                myLog("check data inside of if $data")
                uiState.update { it.copy(checkedComponent = data) }
            }
        }
    }
}