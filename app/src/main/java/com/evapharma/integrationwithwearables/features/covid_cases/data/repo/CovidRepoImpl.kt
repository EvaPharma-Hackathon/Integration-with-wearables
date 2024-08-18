package com.evapharma.integrationwithwearables.features.covid_cases.data.repo

import android.content.Context
import android.util.Log
import com.evapharma.integrationwithwearables.core.models.DataState
import com.evapharma.integrationwithwearables.features.covid_cases.data.local.data_source.HealthyLocalDataSourceImpl
import com.evapharma.integrationwithwearables.features.covid_cases.data.local.model.VitalsData
import com.evapharma.integrationwithwearables.features.covid_cases.data.remote.data_source.CovidRemoteDataSourceImpl
import com.evapharma.integrationwithwearables.features.covid_cases.data.remote.model.CovidCasesResponse
import com.evapharma.integrationwithwearables.features.covid_cases.domain.repo_contract.CovidRepo
import javax.inject.Inject

class CovidRepoImpl @Inject constructor(private val covidRemoteDataSourceImpl: CovidRemoteDataSourceImpl , private val healthyLocalDataSourceImpl: HealthyLocalDataSourceImpl):
    CovidRepo {
    override suspend fun getCovidCases(): DataState<CovidCasesResponse> {
        val response = covidRemoteDataSourceImpl.getCovidCases()
        Log.d("TAG", "getCovidCases: $response")
        return response
    }

    override suspend fun readStepsData(interval: Long): List<VitalsData> {
        return healthyLocalDataSourceImpl.readStepsData(interval)
    }

    override suspend fun readCaloriesData(interval: Long): List<VitalsData> {
        return healthyLocalDataSourceImpl.readCaloriesData(interval)
    }

    override suspend fun readSleepData(interval: Long): List<VitalsData> {
        return healthyLocalDataSourceImpl.readSleepData(interval)
    }

    override suspend fun readDistanceData(interval: Long): List<VitalsData> {
        return healthyLocalDataSourceImpl.readDistanceData(interval)
    }

}