package com.evapharma.integrationwithwearables.features.covid_cases.data.local.healthy_data

import com.evapharma.integrationwithwearables.features.covid_cases.data.local.model.VitalsData

interface HealthDataReader {
    suspend fun readDataForInterval(interval: Long): List<VitalsData>
}
