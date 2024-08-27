package com.evapharma.integrationwithwearables.features.vitals_data.data.remote.data_source

import com.evapharma.integrationwithwearables.core.models.DataState
import com.evapharma.integrationwithwearables.features.vitals_data.data.remote.model.LoginRequest
import com.evapharma.integrationwithwearables.features.vitals_data.data.remote.model.NewVitalsRequest

interface VitalsRemoteDataSource {
    suspend fun addVitals(vitals: NewVitalsRequest) : DataState<Int>
    suspend fun login(phone: LoginRequest) : DataState<String>
}
