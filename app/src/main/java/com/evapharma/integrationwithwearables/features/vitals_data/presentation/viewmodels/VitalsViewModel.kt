package com.evapharma.integrationwithwearables.features.vitals_data.presentation.viewmodels

import androidx.lifecycle.viewModelScope
import com.evapharma.integrationwithwearables.core.MVIBaseViewModel
import com.evapharma.integrationwithwearables.core.models.DataState
import com.evapharma.integrationwithwearables.features.vitals_data.data.remote.model.NewVitalsRequest
import com.evapharma.integrationwithwearables.features.vitals_data.data.remote.model.VitalsData
import com.evapharma.integrationwithwearables.features.vitals_data.domain.use_cases.GetVitalsUseCase
import com.evapharma.integrationwithwearables.features.vitals_data.presentation.VitalsActions
import com.evapharma.integrationwithwearables.features.vitals_data.presentation.VitalsResults
import com.evapharma.integrationwithwearables.features.vitals_data.presentation.VitalsViewState
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.FlowCollector
import kotlinx.coroutines.flow.MutableSharedFlow
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.SharedFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class VitalsViewModel @Inject constructor(private val getVitalsUseCase: GetVitalsUseCase) :
    MVIBaseViewModel<VitalsActions, VitalsResults, VitalsViewState>() {

    private val _vitalsCasesStateFlow: MutableStateFlow<VitalsViewState> =
        MutableStateFlow(VitalsViewState(isIdle = true))


    private val _vitalsData = MutableStateFlow(VitalsData("" ,"", "", "", "", "", "", "", "", "", "", ""))
    val vitalsData: StateFlow<VitalsData> = _vitalsData


    private val _addNewVitalsState: MutableStateFlow<VitalsViewState> =
        MutableStateFlow(defaultViewState)
    val addNewVitalsState: StateFlow<VitalsViewState> = _addNewVitalsState

    override val defaultViewState: VitalsViewState
        get() = VitalsViewState(isIdle = true)

    override fun handleAction(action: VitalsActions): Flow<VitalsResults> {
        return flow {
            when (action) {
                is VitalsActions.GetVitals -> {
                    handleActionOfGetVitalsCases(this, action)
                }

                is VitalsActions.AddNewVitals -> {handleActionOfAddNewVitals(this, action.vitals)}
            }
        }
    }

    private suspend fun handleActionOfGetVitalsCases(
        flowCollector: FlowCollector<VitalsResults>,
        action: VitalsActions.GetVitals
    ) {

    }

    fun fetchHealthData() {
        viewModelScope.launch {
            val data = getVitalsUseCase.readVitalsData()
            _vitalsData.value = data.copy()
        }
    }

    private suspend fun handleActionOfAddNewVitals(
        flowCollector: FlowCollector<VitalsResults>,
        vitals: NewVitalsRequest
    ) {
        val viewState: VitalsViewState
        when (val response = getVitalsUseCase.addVitals(vitals)) {
            is DataState.Success -> {
                viewState = VitalsViewState(isIdle = true)
                _vitalsCasesStateFlow.value = viewState
                flowCollector.emit(VitalsResults.GetVitals(viewState = viewState))
            }

            is DataState.Loading -> {
                viewState = VitalsViewState(isLoading = true)
                _vitalsCasesStateFlow.value = viewState
                flowCollector.emit(VitalsResults.GetVitals(viewState = viewState))
            }

            is DataState.ErrorV2 -> response.exception?.let { exception -> emitException(exception) }
            else -> {}
        }
    }

    fun addNewVitals(vitals: NewVitalsRequest) {
        viewModelScope.launch {
            handleAction(VitalsActions.AddNewVitals(vitals)).collect { result ->
                _addNewVitalsState.value = result.reduce(defaultViewState, _addNewVitalsState.value)
            }
        }
    }
}
