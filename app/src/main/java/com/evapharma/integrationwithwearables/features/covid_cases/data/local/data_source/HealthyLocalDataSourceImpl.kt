package com.evapharma.integrationwithwearables.features.covid_cases.data.local.data_source

import android.content.Context
import com.evapharma.integrationwithwearables.features.covid_cases.data.local.model.VitalsData
import javax.inject.Inject

class HealthyLocalDataSourceImpl @Inject constructor() : HealthyLocalDataSource{

    override suspend fun readStepsData(interval: Long): List<VitalsData> {
        TODO("Not yet implemented")
    }

    override suspend fun readCaloriesData(interval: Long): List<VitalsData> {
        TODO("Not yet implemented")
    }

    override suspend fun readSleepData(interval: Long): List<VitalsData> {
        TODO("Not yet implemented")
    }
}