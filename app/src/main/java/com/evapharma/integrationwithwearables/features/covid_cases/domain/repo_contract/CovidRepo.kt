package com.evapharma.integrationwithwearables.features.covid_cases.domain.repo_contract

import com.evapharma.integrationwithwearables.core.models.DataState
import com.evapharma.integrationwithwearables.features.covid_cases.data.local.model.VitalsRecord
import com.evapharma.integrationwithwearables.features.covid_cases.data.remote.model.CovidCasesResponse

interface CovidRepo {

    suspend fun getCovidCases(): DataState<CovidCasesResponse>
    suspend fun readStepsData(interval: Long): List<VitalsRecord>
    suspend fun readCaloriesData(interval: Long): List<VitalsRecord>
    suspend fun readSleepData(interval: Long): List<VitalsRecord>
    suspend fun readDistanceData(interval: Long): List<VitalsRecord>
    suspend fun readBloodSugarData(interval: Long): List<VitalsRecord>
    suspend fun readOxygenData(interval: Long): List<VitalsRecord>
    suspend fun readHeartRateData(interval: Long): List<VitalsRecord>
    suspend fun readWeightData(interval: Long): List<VitalsRecord>
    suspend fun readHeightData(interval: Long): List<VitalsRecord>
}