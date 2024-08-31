package com.evapharma.integrationwithwearables.features.vitals_data.presentation

import com.evapharma.integrationwithwearables.core.Action
import com.evapharma.integrationwithwearables.core.Result
import com.evapharma.integrationwithwearables.core.ViewState
import com.evapharma.integrationwithwearables.features.vitals_data.data.remote.model.AllVitalsResponse
import com.evapharma.integrationwithwearables.features.vitals_data.data.remote.model.NewVitalsRequest
import com.evapharma.integrationwithwearables.features.vitals_data.data.remote.model.VitalsCaseResponse


sealed class VitalsActions : Action {
    data object GetVitals : VitalsActions()
    data class AddNewVitals(val vitals: NewVitalsRequest) : VitalsActions()
}

sealed class VitalsResults : Result<VitalsViewState> {
    data class GetVitals(val viewState: VitalsViewState) : VitalsResults() {
        override fun reduce(
            defaultState: VitalsViewState,
            oldState: VitalsViewState
        ): VitalsViewState {
            return oldState.copy(
                isIdle = false,
            )
        }
    }
}

data class VitalsViewState(
    val isIdle: Boolean = true,
    val isEmpty: Boolean = false,
    val data: VitalsCaseResponse? = null,
    val isLoading: Boolean = false,
    val error: Throwable? = null,
) : ViewState
