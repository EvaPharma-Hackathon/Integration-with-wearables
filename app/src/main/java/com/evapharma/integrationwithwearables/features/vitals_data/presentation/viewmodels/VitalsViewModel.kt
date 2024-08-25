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
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class VitalsViewModel @Inject constructor(private val getVitalsUseCase: GetVitalsUseCase) :
    MVIBaseViewModel<VitalsActions, VitalsResults, VitalsViewState>() {

    private val _vitalsCasesStateFlow: MutableStateFlow<VitalsViewState> =
        MutableStateFlow(VitalsViewState(isIdle = true))

    private val _addNewVitals: MutableStateFlow<VitalsViewState> =
        MutableStateFlow(VitalsViewState(isIdle = true))


    private val _vitalsData = MutableStateFlow(VitalsData())
    val vitalsData: StateFlow<VitalsData> = _vitalsData


    private val _addNewVitalsState: MutableStateFlow<VitalsViewState> =
        MutableStateFlow(defaultViewState)
    val addNewVitalsState: StateFlow<VitalsViewState> = _addNewVitalsState

    private val interval: Long = 1

    override val defaultViewState: VitalsViewState
        get() = VitalsViewState(isIdle = true)

    override fun handleAction(action: VitalsActions): Flow<VitalsResults> {
        return flow {
            when (action) {
                is VitalsActions.GetVitals -> {
                    handleActionOfGetCovidCases(this, action)
                }

                is VitalsActions.AddNewVitals -> handleActionOfAddNewVitals(this, action.vitals)
            }
        }
    }

    private suspend fun handleActionOfGetCovidCases(
        flowCollector: FlowCollector<VitalsResults>,
        action: VitalsActions.GetVitals
    ) {
        when (val response = getVitalsUseCase()) {
            is DataState.Success -> {
                val viewState = VitalsViewState(data = response.data)
                _vitalsCasesStateFlow.value = viewState
                flowCollector.emit(
                    VitalsResults.GetVitals(viewState = viewState)
                )
            }

            is DataState.Loading -> {
                val viewState = VitalsViewState(isLoading = true)
                _vitalsCasesStateFlow.value = viewState
                flowCollector.emit(
                    VitalsResults.GetVitals(viewState = viewState)
                )
            }

            is DataState.ErrorV2 -> response.exception?.let { exception -> emitException(exception) }
            else -> {}
        }
    }

    fun fetchHealthData() {
        viewModelScope.launch {
            _vitalsData.value = VitalsData(
                steps = getVitalsUseCase.readStepsData(interval).first().metricValue,
                calories = getVitalsUseCase.readCaloriesData(interval).first().metricValue,
                sleep = getVitalsUseCase.readSleepData(interval).first().metricValue,
                distance = getVitalsUseCase.readDistanceData(interval).first().metricValue,
                bloodSugar = getVitalsUseCase.readBloodSugarData(interval).first().metricValue,
                oxygenSaturation = getVitalsUseCase.readOxygenSaturationData(interval)
                    .first().metricValue,
                heartRate = getVitalsUseCase.readHeartRateData(interval).first().metricValue,
                weight = getVitalsUseCase.readWeightData(interval).first().metricValue,
                height = getVitalsUseCase.readHeightData(interval).first().metricValue,
                temperature = getVitalsUseCase.readBodyTemperatureData(interval)
                    .first().metricValue,
                bloodPressure = getVitalsUseCase.readBloodPressureData(interval)
                    .first().metricValue,
                respiratoryRate = getVitalsUseCase.readRespiratoryRateData(interval)
                    .first().metricValue,
            )
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
                _addNewVitals.value = viewState
                flowCollector.emit(VitalsResults.GetVitals(viewState = viewState))
            }

            is DataState.Loading -> {
                viewState = VitalsViewState(isLoading = true)
                _addNewVitals.value = viewState
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
