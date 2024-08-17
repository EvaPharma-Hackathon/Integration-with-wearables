package com.evapharma.integrationwithwearables.features.covid_cases.data.local.data_source

import android.content.Context
import com.evapharma.integrationwithwearables.features.covid_cases.data.local.model.VitalsData

interface HealthyLocalDataSource {
    suspend fun readStepsData(interval: Long): List<VitalsData>
    suspend fun readCaloriesData(interval: Long): List<VitalsData>
    suspend fun readSleepData(interval: Long): List<VitalsData>

}