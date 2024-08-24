package com.evapharma.integrationwithwearables.features.vitals_data.data.remote.data_source

import com.evapharma.integrationwithwearables.core.models.DataState
import com.evapharma.integrationwithwearables.features.vitals_data.data.remote.model.NewVitalsRequest
import com.evapharma.integrationwithwearables.features.vitals_data.data.remote.model.VitalsCaseResponse

interface VitalsRemoteDataSource {
    suspend fun getVitalsCases(): DataState<VitalsCaseResponse>
    suspend fun addVitals(vitals: NewVitalsRequest) : DataState<Int>
}
