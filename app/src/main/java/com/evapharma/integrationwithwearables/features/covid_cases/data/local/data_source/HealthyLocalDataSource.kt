package com.evapharma.integrationwithwearables.features.covid_cases.data.local.data_source

import android.content.Context
import com.evapharma.integrationwithwearables.features.covid_cases.data.local.model.VitalsData

interface HealthyLocalDataSource {
    suspend fun readStepsData(interval: Long): List<VitalsData>
    suspend fun readCaloriesData(interval: Long): List<VitalsData>
    suspend fun readSleepData(interval: Long): List<VitalsData>
    suspend fun readDistanceData(interval: Long): List<VitalsData>
    suspend fun readBloodSugarData(interval: Long): List<VitalsData>
    suspend fun readOxygenSaturationData(interval: Long): List<VitalsData>
    suspend fun readHeartRateData(interval: Long): List<VitalsData>
}