package com.evapharma.integrationwithwearables.features.vitals_data.data.remote.data_source

import com.evapharma.integrationwithwearables.core.models.DataState
import com.evapharma.integrationwithwearables.features.vitals_data.data.remote.model.VitalsCaseResponse

interface VitalsRemoteDataSource {
    suspend fun getVitalsCases(): DataState<VitalsCaseResponse>
}
