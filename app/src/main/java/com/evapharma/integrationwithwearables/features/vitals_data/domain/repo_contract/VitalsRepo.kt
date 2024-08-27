package com.evapharma.integrationwithwearables.features.vitals_data.domain.repo_contract

import com.evapharma.integrationwithwearables.core.models.DataState
import com.evapharma.integrationwithwearables.features.vitals_data.data.local.model.VitalsRecord
import com.evapharma.integrationwithwearables.features.vitals_data.data.remote.model.AllVitalsResponse
import com.evapharma.integrationwithwearables.features.vitals_data.data.remote.model.NewVitalsRequest
import com.evapharma.integrationwithwearables.features.vitals_data.data.remote.model.VitalsCaseResponse
import com.evapharma.integrationwithwearables.features.vitals_data.data.remote.model.VitalsData

interface VitalsRepo {

    suspend fun getVitalsData(): VitalsData
    suspend fun addVitals(vitals: NewVitalsRequest) : DataState<Int>

}