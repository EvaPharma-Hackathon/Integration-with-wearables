package com.evapharma.integrationwithwearables.features.vitals_data.data.local.data_source

import com.evapharma.integrationwithwearables.features.vitals_data.data.remote.model.VitalsData

interface VitalsLocalDataSource {
    suspend fun getVitalsData(): VitalsData
}