package com.evapharma.integrationwithwearables.features.vitals_data.data.remote.data_source

import com.evapharma.integrationwithwearables.core.models.DataState
import com.evapharma.integrationwithwearables.core.models.handleException
import com.evapharma.integrationwithwearables.core.models.handleResponse
import com.evapharma.integrationwithwearables.core.network.BaseURLFactory
import com.evapharma.integrationwithwearables.features.vitals_data.data.remote.model.VitalsCaseResponse
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext
import javax.inject.Inject

class VitalsDataSourceImpl @Inject constructor() : VitalsRemoteDataSource {

    override suspend fun getVitalsCases(): DataState<VitalsCaseResponse> {
        return withContext(Dispatchers.IO){
            try {

                BaseURLFactory.retrofit.create(
                    VitalsApiService::class.java
                ).getCovidCases().handleResponse()
            }catch (t: Throwable){
                t.handleException()
            }
        }
    }

}