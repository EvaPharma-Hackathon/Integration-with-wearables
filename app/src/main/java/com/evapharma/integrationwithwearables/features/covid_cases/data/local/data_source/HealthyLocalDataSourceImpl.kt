package com.evapharma.integrationwithwearables.features.covid_cases.data.local.data_source

import android.content.Context
import androidx.health.connect.client.HealthConnectClient
import com.evapharma.integrationwithwearables.features.covid_cases.data.local.healthy_data.CaloriesData
import com.evapharma.integrationwithwearables.features.covid_cases.data.local.healthy_data.StepsData
import com.evapharma.integrationwithwearables.features.covid_cases.data.local.model.VitalsData
import javax.inject.Inject

class HealthyLocalDataSourceImpl @Inject constructor(private val healthConnectClient: HealthConnectClient) : HealthyLocalDataSource {

    private val stepsData: StepsData by lazy { StepsData(healthConnectClient) }
    private val caloriesData: CaloriesData by lazy { CaloriesData(healthConnectClient) }

    override suspend fun readStepsData(interval: Long): List<VitalsData> {
        return stepsData.readDataForInterval(interval)
    }

    override suspend fun readCaloriesData(interval: Long): List<VitalsData> {
        return caloriesData.readDataForInterval(interval)
    }

    override suspend fun readSleepData(interval: Long): List<VitalsData> {
        TODO("Not yet implemented")
    }
}
