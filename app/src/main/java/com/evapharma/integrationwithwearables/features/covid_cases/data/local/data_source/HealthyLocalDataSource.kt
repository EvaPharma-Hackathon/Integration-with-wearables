package com.evapharma.integrationwithwearables.features.covid_cases.data.local.data_source

import com.evapharma.integrationwithwearables.features.covid_cases.data.local.model.VitalsRecord

interface HealthyLocalDataSource {
    suspend fun readStepsData(interval: Long): List<VitalsRecord>
    suspend fun readCaloriesData(interval: Long): List<VitalsRecord>
    suspend fun readSleepData(interval: Long): List<VitalsRecord>
    suspend fun readDistanceData(interval: Long): List<VitalsRecord>
    suspend fun readBloodSugarData(interval: Long): List<VitalsRecord>
    suspend fun readOxygenSaturationData(interval: Long): List<VitalsRecord>
    suspend fun readHeartRateData(interval: Long): List<VitalsRecord>
    suspend fun readWeightData(interval: Long): List<VitalsRecord>
    suspend fun readHeightData(interval: Long): List<VitalsRecord>

}