package com.evapharma.integrationwithwearables.features.covid_cases.data.local.data_source

import com.evapharma.integrationwithwearables.features.covid_cases.data.local.model.VitalsData
import java.time.ZonedDateTime

interface HealthDataReader {
    suspend fun readDataForInterval(interval: Long): List<VitalsData>
}
