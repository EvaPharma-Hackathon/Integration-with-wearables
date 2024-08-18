package com.evapharma.integrationwithwearables.features.covid_cases.data.repo

import android.util.Log
import com.evapharma.integrationwithwearables.core.models.DataState
import com.evapharma.integrationwithwearables.features.covid_cases.data.local.data_source.HealthyLocalDataSourceImpl
import com.evapharma.integrationwithwearables.features.covid_cases.data.local.model.VitalsRecord
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

    override suspend fun readStepsData(interval: Long): List<VitalsRecord> {
        return healthyLocalDataSourceImpl.readStepsData(interval)
    }

    override suspend fun readCaloriesData(interval: Long): List<VitalsRecord> {
        return healthyLocalDataSourceImpl.readCaloriesData(interval)
    }

    override suspend fun readSleepData(interval: Long): List<VitalsRecord> {
        return healthyLocalDataSourceImpl.readSleepData(interval)
    }

    override suspend fun readDistanceData(interval: Long): List<VitalsRecord> {
        return healthyLocalDataSourceImpl.readDistanceData(interval)
    }

    override suspend fun readBloodSugarData(interval: Long): List<VitalsRecord> {
        return healthyLocalDataSourceImpl.readBloodSugarData(interval)
    }

    override suspend fun readOxygenData(interval: Long): List<VitalsRecord> {
        return healthyLocalDataSourceImpl.readOxygenSaturationData(interval)
    }

    override suspend fun readHeartRateData(interval: Long): List<VitalsRecord> {
        return healthyLocalDataSourceImpl.readHeartRateData(interval)
    }

    override suspend fun readWeightData(interval: Long): List<VitalsRecord> {
        return healthyLocalDataSourceImpl.readHeightData(interval)
    }

    override suspend fun readHeightData(interval: Long): List<VitalsRecord> {
        return healthyLocalDataSourceImpl.readHeightData(interval)
    }

    override suspend fun readBodyTemperatureData(interval: Long): List<VitalsRecord> {
        return healthyLocalDataSourceImpl.readBodyTemperatureData(interval)
    }


}