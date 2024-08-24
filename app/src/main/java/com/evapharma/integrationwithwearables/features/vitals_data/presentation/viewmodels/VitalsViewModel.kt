package com.evapharma.integrationwithwearables.features.vitals_data.presentation.viewmodels

import androidx.lifecycle.viewModelScope
import com.evapharma.integrationwithwearables.core.MVIBaseViewModel
import com.evapharma.integrationwithwearables.core.models.DataState
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
class VitalsViewModel   @Inject constructor(private val getCovidCasesUseCase: GetVitalsUseCase) :
    MVIBaseViewModel<VitalsActions, VitalsResults, VitalsViewState>() {

    private val _covidCasesStateFlow: MutableStateFlow<VitalsViewState> =
        MutableStateFlow(VitalsViewState(isIdle = true))

    private val _vitalsData = MutableStateFlow(VitalsData())
    val vitalsData: StateFlow<VitalsData> = _vitalsData

    private val interval: Long = 1


    override val defaultViewState: VitalsViewState
        get() = VitalsViewState(isIdle = true)

    override fun handleAction(action: VitalsActions): Flow<VitalsResults> {
        return flow {
            when (action) {
                is VitalsActions.GetVitals -> {
                    handleActionOfGetCovidCases(this, action)

                }
            }

        }
    }

    private suspend fun handleActionOfGetCovidCases(
        flowCollector: FlowCollector<VitalsResults>,
        action: VitalsActions.GetVitals
    ) {
//        val oldCovidCases = _covidCasesStateFlow.value.data
        when(val response = getCovidCasesUseCase()){

            is DataState.Success -> {
                val viewState = VitalsViewState(data = response.data)
                _covidCasesStateFlow.value = viewState
                flowCollector.emit(
                    VitalsResults.GetVitals(viewState = viewState)
                )
            }

            is DataState.Loading -> {
                val viewState = VitalsViewState(isLoading = true)
                _covidCasesStateFlow.value = viewState
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
                steps =  getCovidCasesUseCase.readStepsData(interval).first().metricValue,
                calories = getCovidCasesUseCase.readCaloriesData(interval).first().metricValue,
                sleep = getCovidCasesUseCase.readSleepData(interval).first().metricValue,
                distance = getCovidCasesUseCase.readDistanceData(interval).first().metricValue,
                bloodSugar = getCovidCasesUseCase.readBloodSugarData(interval).first().metricValue,
                oxygenSaturation = getCovidCasesUseCase.readOxygenSaturationData(interval).first().metricValue,
                heartRate = getCovidCasesUseCase.readHeartRateData(interval).first().metricValue,
                weight = getCovidCasesUseCase.readWeightData(interval).first().metricValue,
                height = getCovidCasesUseCase.readHeightData(interval).first().metricValue,
                temperature= getCovidCasesUseCase.readBodyTemperatureData(interval).first().metricValue,
                bloodPressure= getCovidCasesUseCase.readBloodPressureData(interval).first().metricValue,
                respiratoryRate=getCovidCasesUseCase.readRespiratoryRateData(interval).first().metricValue,

            )
        }
    }
}