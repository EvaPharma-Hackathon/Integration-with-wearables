package com.evapharma.integrationwithwearables.features.covid_cases.domain.repo_contract

import android.content.Context
import com.evapharma.integrationwithwearables.core.models.DataState
import com.evapharma.integrationwithwearables.features.covid_cases.data.local.model.VitalsData
import com.evapharma.integrationwithwearables.features.covid_cases.data.remote.model.CovidCasesResponse

interface CovidRepo {

    suspend fun getCovidCases(): DataState<CovidCasesResponse>
    suspend fun readStepsData(interval: Long): List<VitalsData>
    suspend fun readCaloriesData(interval: Long): List<VitalsData>
    suspend fun readSleepData(interval: Long): List<VitalsData>
    suspend fun readDistanceData(interval: Long): List<VitalsData>
    suspend fun readBloodSugarData(interval: Long): List<VitalsData>
}