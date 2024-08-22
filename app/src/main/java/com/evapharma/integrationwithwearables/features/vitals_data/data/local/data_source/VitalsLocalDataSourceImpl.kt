package com.evapharma.integrationwithwearables.features.vitals_data.data.local.data_source

import androidx.health.connect.client.HealthConnectClient
import com.evapharma.integrationwithwearables.features.vitals_data.data.local.healthy_data.BloodPressureData
import com.evapharma.integrationwithwearables.features.vitals_data.data.local.healthy_data.BloodSugarData
import com.evapharma.integrationwithwearables.features.vitals_data.data.local.healthy_data.BodyTemperatureData
import com.evapharma.integrationwithwearables.features.vitals_data.data.local.healthy_data.CaloriesData
import com.evapharma.integrationwithwearables.features.vitals_data.data.local.healthy_data.DistanceData
import com.evapharma.integrationwithwearables.features.vitals_data.data.local.healthy_data.HeartRateData
import com.evapharma.integrationwithwearables.features.vitals_data.data.local.healthy_data.HeightData
import com.evapharma.integrationwithwearables.features.vitals_data.data.local.healthy_data.OxygenSaturationData
import com.evapharma.integrationwithwearables.features.vitals_data.data.local.healthy_data.RespiratoryRateData
import com.evapharma.integrationwithwearables.features.vitals_data.data.local.healthy_data.SleepData
import com.evapharma.integrationwithwearables.features.vitals_data.data.local.healthy_data.StepsData
import com.evapharma.integrationwithwearables.features.vitals_data.data.local.healthy_data.WeightData
import com.evapharma.integrationwithwearables.features.vitals_data.data.local.model.VitalsRecord
import javax.inject.Inject

class VitalsLocalDataSourceImpl @Inject constructor(
    private val healthConnectClient: HealthConnectClient?
) : VitalsLocalDataSource {

    private val stepsData: StepsData? by lazy { healthConnectClient?.let { StepsData(it) } }
    private val caloriesData: CaloriesData? by lazy { healthConnectClient?.let { CaloriesData(it) } }
    private val sleepData: SleepData? by lazy { healthConnectClient?.let { SleepData(it) } }
    private val distanceData: DistanceData? by lazy { healthConnectClient?.let { DistanceData(it) } }
    private val bloodSugarData: BloodSugarData? by lazy { healthConnectClient?.let { BloodSugarData(it) } }
    private val oxygenSaturationData: OxygenSaturationData? by lazy { healthConnectClient?.let { OxygenSaturationData(it) } }
    private val heartRateData: HeartRateData? by lazy { healthConnectClient?.let { HeartRateData(it) } }
    private val weightData: WeightData? by lazy { healthConnectClient?.let { WeightData(it) } }
    private val heightData: HeightData? by lazy { healthConnectClient?.let { HeightData(it) } }
    private val bodyTemperature: BodyTemperatureData? by lazy { healthConnectClient?.let { BodyTemperatureData(it) } }
    private val bloodPressure: BloodPressureData? by lazy { healthConnectClient?.let { BloodPressureData(it) } }
    private val respiratoryRate: RespiratoryRateData? by lazy { healthConnectClient?.let { RespiratoryRateData(it) } }

    override suspend fun readStepsData(interval: Long): List<VitalsRecord> {
        return stepsData?.readDataForInterval(interval) ?: emptyList()
    }

    override suspend fun readCaloriesData(interval: Long): List<VitalsRecord> {
        return caloriesData?.readDataForInterval(interval) ?: emptyList()
    }

    override suspend fun readSleepData(interval: Long): List<VitalsRecord> {
        return sleepData?.readDataForInterval(interval) ?: emptyList()
    }

    override suspend fun readDistanceData(interval: Long): List<VitalsRecord> {
        return distanceData?.readDataForInterval(interval) ?: emptyList()
    }

    override suspend fun readBloodSugarData(interval: Long): List<VitalsRecord> {
        return bloodSugarData?.readDataForInterval(interval) ?: emptyList()
    }

    override suspend fun readOxygenSaturationData(interval: Long): List<VitalsRecord> {
        return oxygenSaturationData?.readDataForInterval(interval) ?: emptyList()
    }

    override suspend fun readHeartRateData(interval: Long): List<VitalsRecord> {
        return heartRateData?.readDataForInterval(interval) ?: emptyList()
    }

    override suspend fun readWeightData(interval: Long): List<VitalsRecord> {
        return weightData?.readDataForInterval(interval) ?: emptyList()
    }

    override suspend fun readHeightData(interval: Long): List<VitalsRecord> {
        return heightData?.readDataForInterval(interval) ?: emptyList()
    }

    override suspend fun readBodyTemperatureData(interval: Long): List<VitalsRecord> {
        return bodyTemperature?.readDataForInterval(interval) ?: emptyList()
    }

    override suspend fun readBloodPressureData(interval: Long): List<VitalsRecord> {
        return bloodPressure?.readDataForInterval(interval) ?: emptyList()
    }

    override suspend fun readRespiratoryRate(interval: Long): List<VitalsRecord> {
        return respiratoryRate?.readDataForInterval(interval) ?: emptyList()
    }
}
