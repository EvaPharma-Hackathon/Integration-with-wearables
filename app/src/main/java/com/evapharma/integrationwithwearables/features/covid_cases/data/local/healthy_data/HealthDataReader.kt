package com.evapharma.integrationwithwearables.features.covid_cases.data.local.healthy_data

import com.evapharma.integrationwithwearables.features.covid_cases.data.local.model.VitalsRecord

interface HealthDataReader {
    suspend fun readDataForInterval(interval: Long): List<VitalsRecord>
}
