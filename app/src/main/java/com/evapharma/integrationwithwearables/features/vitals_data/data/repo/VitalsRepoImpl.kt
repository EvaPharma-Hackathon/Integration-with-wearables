package com.evapharma.integrationwithwearables.features.vitals_data.data.repo

import com.evapharma.integrationwithwearables.core.models.DataState
import com.evapharma.integrationwithwearables.features.vitals_data.data.local.data_source.VitalsLocalDataSource
import com.evapharma.integrationwithwearables.features.vitals_data.data.local.model.VitalsRecord
import com.evapharma.integrationwithwearables.features.vitals_data.data.remote.data_source.VitalsRemoteDataSource
import com.evapharma.integrationwithwearables.features.vitals_data.data.remote.model.AllVitalsResponse
import com.evapharma.integrationwithwearables.features.vitals_data.data.remote.model.NewVitalsRequest
import com.evapharma.integrationwithwearables.features.vitals_data.data.remote.model.VitalsCaseResponse
import com.evapharma.integrationwithwearables.features.vitals_data.data.remote.model.VitalsData
import com.evapharma.integrationwithwearables.features.vitals_data.domain.repo_contract.VitalsRepo
import javax.inject.Inject

class VitalsRepoImpl @Inject constructor(private val vitalsRemoteDataSourceImpl: VitalsRemoteDataSource, private val vitalsLocalDataSourceImpl: VitalsLocalDataSource):
    VitalsRepo {
    override suspend fun getVitalsCases(): DataState<VitalsCaseResponse> {
        return vitalsRemoteDataSourceImpl.getVitalsCases()
    }

    override suspend fun getVitalsData(): VitalsData {
        return vitalsLocalDataSourceImpl.getVitalsData()
    }

    override suspend fun addVitals(vitals: NewVitalsRequest): DataState<Int> {
        return vitalsRemoteDataSourceImpl.addVitals(vitals)
    }

    override suspend fun getAllVitals(): DataState<List<AllVitalsResponse>> {
        return vitalsRemoteDataSourceImpl.getAllVitals()
    }


}