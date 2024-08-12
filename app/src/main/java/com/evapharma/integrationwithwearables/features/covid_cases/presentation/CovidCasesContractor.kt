package com.evapharma.integrationwithwearables.features.covid_cases.presentation



import com.evapharma.integrationwithwearables.core.Action
import com.evapharma.integrationwithwearables.core.Result
import com.evapharma.integrationwithwearables.core.ViewState
import com.evapharma.integrationwithwearables.features.covid_cases.data.remote.model.CovidCasesResponse


sealed class CovidCasesActions : Action {
    data object GetCovidCases : CovidCasesActions()

}

sealed class CovidCasesResults : Result<CovidCasesViewState> {

    data class GetCovidCases(val viewState: CovidCasesViewState) : CovidCasesResults() {
        override fun reduce(
            defaultState: CovidCasesViewState,
            oldState: CovidCasesViewState
        ): CovidCasesViewState {
            return oldState.copy(
                isIdle = false,
            )
        }
    }
}


data class CovidCasesViewState(
    val isIdle: Boolean = true,
    val isEmpty: Boolean = false,
    val data: CovidCasesResponse? = null,
    val isLoading: Boolean = false,
    val error: Throwable? = null,
) : ViewState