package com.evapharma.integrationwithwearables.features.vitals_data.presentation.viewmodels

import com.evapharma.integrationwithwearables.core.MVIBaseViewModel
import com.evapharma.integrationwithwearables.core.models.DataState
import com.evapharma.integrationwithwearables.features.vitals_data.domain.use_cases.GetVitalsUseCase
import com.evapharma.integrationwithwearables.features.vitals_data.presentation.VitalsActions
import com.evapharma.integrationwithwearables.features.vitals_data.presentation.VitalsResults
import com.evapharma.integrationwithwearables.features.vitals_data.presentation.VitalsViewState
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.FlowCollector
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.flow
import javax.inject.Inject

@HiltViewModel
class MainViewModel @Inject constructor(private val getCovidCasesUseCase: GetVitalsUseCase) :
    MVIBaseViewModel<VitalsActions, VitalsResults, VitalsViewState>() {

    private val _covidCasesStateFlow: MutableStateFlow<VitalsViewState> =
        MutableStateFlow(VitalsViewState(isIdle = true))


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


}