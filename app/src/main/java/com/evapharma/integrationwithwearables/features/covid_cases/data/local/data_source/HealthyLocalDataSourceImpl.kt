package com.evapharma.integrationwithwearables.features.covid_cases.data.local.data_source

import androidx.health.connect.client.HealthConnectClient
import com.evapharma.integrationwithwearables.features.covid_cases.data.local.healthy_data.BloodPressureData
import com.evapharma.integrationwithwearables.features.covid_cases.data.local.healthy_data.BloodSugarData
import com.evapharma.integrationwithwearables.features.covid_cases.data.local.healthy_data.BodyTemperatureData
import com.evapharma.integrationwithwearables.features.covid_cases.data.local.healthy_data.CaloriesData
import com.evapharma.integrationwithwearables.features.covid_cases.data.local.healthy_data.DistanceData
import com.evapharma.integrationwithwearables.features.covid_cases.data.local.healthy_data.HeartRateData
import com.evapharma.integrationwithwearables.features.covid_cases.data.local.healthy_data.HeightData
import com.evapharma.integrationwithwearables.features.covid_cases.data.local.healthy_data.OxygenSaturationData
import com.evapharma.integrationwithwearables.features.covid_cases.data.local.healthy_data.RespiratoryRateData
import com.evapharma.integrationwithwearables.features.covid_cases.data.local.healthy_data.SleepData
import com.evapharma.integrationwithwearables.features.covid_cases.data.local.healthy_data.StepsData
import com.evapharma.integrationwithwearables.features.covid_cases.data.local.healthy_data.WeightData
import com.evapharma.integrationwithwearables.features.covid_cases.data.local.model.VitalsRecord
import javax.inject.Inject

class HealthyLocalDataSourceImpl @Inject constructor(private val healthConnectClient: HealthConnectClient) : HealthyLocalDataSource {

    private val stepsData: StepsData by lazy { StepsData(healthConnectClient) }
    private val caloriesData: CaloriesData by lazy { CaloriesData(healthConnectClient) }
    private val sleepData: SleepData by lazy { SleepData(healthConnectClient) }
    private val distanceData:DistanceData by lazy { DistanceData(healthConnectClient) }
    private val bloodSugarData: BloodSugarData by lazy { BloodSugarData(healthConnectClient) }
    private val oxygenSaturationData: OxygenSaturationData by lazy { OxygenSaturationData(healthConnectClient) }
    private val heartRateData: HeartRateData by lazy { HeartRateData(healthConnectClient) }
    private val weightData: WeightData by lazy { WeightData(healthConnectClient) }
    private val heightData: HeightData by lazy { HeightData(healthConnectClient) }
    private val bodyTemperature: BodyTemperatureData by lazy {BodyTemperatureData(healthConnectClient)}
    private val bloodPressure: BloodPressureData by lazy {BloodPressureData(healthConnectClient)}
    private val respiratoryRate: RespiratoryRateData by lazy {RespiratoryRateData(healthConnectClient)}

    override suspend fun readStepsData(interval: Long): List<VitalsRecord> {
        return stepsData.readDataForInterval(interval)
    }

    override suspend fun readCaloriesData(interval: Long): List<VitalsRecord> {
        return caloriesData.readDataForInterval(interval)
    }

    override suspend fun readSleepData(interval: Long): List<VitalsRecord> {
        return sleepData.readDataForInterval(interval)
    }

    override suspend fun readDistanceData(interval: Long): List<VitalsRecord> {
        return distanceData.readDataForInterval(interval)
    }

    override suspend fun readBloodSugarData(interval: Long): List<VitalsRecord> {
        return bloodSugarData.readDataForInterval(interval)
    }

    override suspend fun readOxygenSaturationData(interval: Long): List<VitalsRecord> {
        return oxygenSaturationData.readDataForInterval(interval)
    }

    override suspend fun readHeartRateData(interval: Long): List<VitalsRecord> {
        return heartRateData.readDataForInterval(interval)
    }

    override suspend fun readWeightData(interval: Long): List<VitalsRecord> {
        return weightData.readDataForInterval(interval)
    }

    override suspend fun readHeightData(interval: Long): List<VitalsRecord> {
        return heightData.readDataForInterval(interval)
    }

    override suspend fun readBodyTemperatureData(interval: Long): List<VitalsRecord> {
        return bodyTemperature.readDataForInterval(interval)
    }

    override suspend fun readBloodPressureData(interval: Long): List<VitalsRecord> {
        return bloodPressure.readDataForInterval(interval)
    }

    override suspend fun readRespiratoryRate(interval: Long): List<VitalsRecord> {
        return respiratoryRate.readDataForInterval(interval)
    }
}
