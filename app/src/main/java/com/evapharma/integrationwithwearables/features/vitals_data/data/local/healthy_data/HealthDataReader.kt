package com.evapharma.integrationwithwearables.features.vitals_data.data.local.healthy_data

import com.evapharma.integrationwithwearables.features.vitals_data.data.local.model.VitalsRecord

interface HealthDataReader {
    suspend fun readDataForInterval(interval: Long): List<VitalsRecord>
}
