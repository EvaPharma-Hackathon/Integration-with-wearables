package com.evapharma.integrationwithwearables.features.covid_cases.presentation.viewmodels

import android.content.Context
import android.util.Log
import androidx.health.connect.client.HealthConnectClient
import androidx.lifecycle.viewModelScope
import com.evapharma.integrationwithwearables.core.MVIBaseViewModel
import com.evapharma.integrationwithwearables.core.models.DataState
import com.evapharma.integrationwithwearables.features.covid_cases.data.remote.model.VitalsData
import com.evapharma.integrationwithwearables.features.covid_cases.domain.use_cases.GetCovidCasesUseCase
import com.evapharma.integrationwithwearables.features.covid_cases.presentation.CovidCasesActions
import com.evapharma.integrationwithwearables.features.covid_cases.presentation.CovidCasesResults
import com.evapharma.integrationwithwearables.features.covid_cases.presentation.CovidCasesViewState
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.FlowCollector
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class VitalsViewModel   @Inject constructor(private val getCovidCasesUseCase: GetCovidCasesUseCase) :
    MVIBaseViewModel<CovidCasesActions, CovidCasesResults, CovidCasesViewState>() {

    private val _covidCasesStateFlow: MutableStateFlow<CovidCasesViewState> =
        MutableStateFlow(CovidCasesViewState(isIdle = true))

    private val _vitalsData = MutableStateFlow(VitalsData())
    val vitalsData: StateFlow<VitalsData> = _vitalsData

    private val interval: Long = 1


    override val defaultViewState: CovidCasesViewState
        get() = CovidCasesViewState(isIdle = true)

    override fun handleAction(action: CovidCasesActions): Flow<CovidCasesResults> {
        return flow {
            when (action) {
                is CovidCasesActions.GetCovidCases -> {
                    handleActionOfGetCovidCases(this, action)

                }
            }

        }
    }

    private suspend fun handleActionOfGetCovidCases(
        flowCollector: FlowCollector<CovidCasesResults>,
        action: CovidCasesActions.GetCovidCases
    ) {
//        val oldCovidCases = _covidCasesStateFlow.value.data
        when(val response = getCovidCasesUseCase()){

            is DataState.Success -> {
                val viewState = CovidCasesViewState(data = response.data)
                _covidCasesStateFlow.value = viewState
                flowCollector.emit(
                    CovidCasesResults.GetCovidCases(viewState = viewState)
                )
            }

            is DataState.Loading -> {
                val viewState = CovidCasesViewState(isLoading = true)
                _covidCasesStateFlow.value = viewState
                flowCollector.emit(
                    CovidCasesResults.GetCovidCases(viewState = viewState)
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

            )
        }
    }
}