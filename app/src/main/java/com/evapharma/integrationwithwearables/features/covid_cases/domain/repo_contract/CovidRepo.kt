package com.evapharma.integrationwithwearables.features.covid_cases.domain.repo_contract

import android.content.Context
import com.evapharma.integrationwithwearables.core.models.DataState
import com.evapharma.integrationwithwearables.features.covid_cases.data.remote.model.CovidCasesResponse

interface CovidRepo {

    suspend fun getCovidCases(): DataState<CovidCasesResponse>
    suspend fun checkHealthConnectInstituted(context: Context): Int
    suspend fun checkHealthPermissions(): Boolean

}