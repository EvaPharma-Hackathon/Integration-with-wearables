package com.evapharma.integrationwithwearables.features.vitals_data.data.repo

import com.evapharma.integrationwithwearables.core.models.DataState
import com.evapharma.integrationwithwearables.features.vitals_data.data.local.data_source.VitalsLocalDataSource
import com.evapharma.integrationwithwearables.features.vitals_data.data.remote.data_source.VitalsRemoteDataSource
import com.evapharma.integrationwithwearables.features.vitals_data.data.remote.model.NewVitalsRequest
import com.evapharma.integrationwithwearables.features.vitals_data.data.remote.model.VitalsData
import com.evapharma.integrationwithwearables.features.vitals_data.domain.repo_contract.VitalsRepo
import javax.inject.Inject

class VitalsRepoImpl @Inject constructor(private val vitalsRemoteDataSourceImpl: VitalsRemoteDataSource, private val vitalsLocalDataSourceImpl: VitalsLocalDataSource):
    VitalsRepo {

    override suspend fun getVitalsData(): VitalsData {
        return vitalsLocalDataSourceImpl.getVitalsData()
    }

    override suspend fun addVitals(vitals: NewVitalsRequest): DataState<Int> {
        return vitalsRemoteDataSourceImpl.addVitals(vitals)
    }


}