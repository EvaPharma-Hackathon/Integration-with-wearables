package com.evapharma.integrationwithwearables.features.covid_cases.data.local.data_source

import androidx.health.connect.client.HealthConnectClient
import com.evapharma.integrationwithwearables.features.covid_cases.data.local.healthy_data.BloodSugarData
import com.evapharma.integrationwithwearables.features.covid_cases.data.local.healthy_data.CaloriesData
import com.evapharma.integrationwithwearables.features.covid_cases.data.local.healthy_data.DistanceData
import com.evapharma.integrationwithwearables.features.covid_cases.data.local.healthy_data.HeartRateData
import com.evapharma.integrationwithwearables.features.covid_cases.data.local.healthy_data.OxygenSaturationData
import com.evapharma.integrationwithwearables.features.covid_cases.data.local.healthy_data.SleepData
import com.evapharma.integrationwithwearables.features.covid_cases.data.local.healthy_data.StepsData
import com.evapharma.integrationwithwearables.features.covid_cases.data.local.model.VitalsData
import javax.inject.Inject

class HealthyLocalDataSourceImpl @Inject constructor(private val healthConnectClient: HealthConnectClient) : HealthyLocalDataSource {

    private val stepsData: StepsData by lazy { StepsData(healthConnectClient) }
    private val caloriesData: CaloriesData by lazy { CaloriesData(healthConnectClient) }
    private val sleepData: SleepData by lazy { SleepData(healthConnectClient) }
    private val distanceData:DistanceData by lazy { DistanceData(healthConnectClient) }
    private val bloodSugarData: BloodSugarData by lazy { BloodSugarData(healthConnectClient) }
    private val oxygenSaturationData: OxygenSaturationData by lazy { OxygenSaturationData(healthConnectClient) }
    private val heartRateData: HeartRateData by lazy { HeartRateData(healthConnectClient) }

    override suspend fun readStepsData(interval: Long): List<VitalsData> {
        return stepsData.readDataForInterval(interval)
    }

    override suspend fun readCaloriesData(interval: Long): List<VitalsData> {
        return caloriesData.readDataForInterval(interval)
    }

    override suspend fun readSleepData(interval: Long): List<VitalsData> {
        return sleepData.readDataForInterval(interval)
    }

    override suspend fun readDistanceData(interval: Long): List<VitalsData> {
        return distanceData.readDataForInterval(interval)
    }

    override suspend fun readBloodSugarData(interval: Long): List<VitalsData> {
        return bloodSugarData.readDataForInterval(interval)
    }

    override suspend fun readOxygenSaturationData(interval: Long): List<VitalsData> {
        return oxygenSaturationData.readDataForInterval(interval)
    }

    override suspend fun readHeartRateData(interval: Long): List<VitalsData> {
        return heartRateData.readDataForInterval(interval)
    }
}
