package com.evapharma.integrationwithwearables.features.covid_cases.presentation.viewmodels

import android.content.Context
import android.util.Log
import androidx.health.connect.client.HealthConnectClient
import androidx.lifecycle.viewModelScope
import com.evapharma.integrationwithwearables.core.MVIBaseViewModel
import com.evapharma.integrationwithwearables.core.models.DataState
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

    private val _steps = MutableStateFlow("0")
    val steps: StateFlow<String> = _steps

    private val _calories = MutableStateFlow("0")
    val calories: StateFlow<String> = _calories

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
            val stepsData = getCovidCasesUseCase.readStepsData(interval).first()
            Log.d("ViewModel", "Fetched steps data: ${stepsData.metricValue}")
            _steps.value = stepsData.metricValue
            _calories.value =  getCovidCasesUseCase.readCaloriesData(interval).first().metricValue
        }
    }

}