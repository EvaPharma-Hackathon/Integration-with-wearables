package com.evapharma.integrationwithwearables.features.covid_cases.data.remote.data_source

import com.evapharma.integrationwithwearables.core.models.DataState
import com.evapharma.integrationwithwearables.features.covid_cases.data.remote.model.CovidCasesResponse

interface CovidCasesRemoteDataSource {
    suspend fun getCovidCases(): DataState<CovidCasesResponse>
}
