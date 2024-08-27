package com.evapharma.integrationwithwearables.features.vitals_data.data.remote.data_source

import com.evapharma.integrationwithwearables.core.models.DataState
import com.evapharma.integrationwithwearables.core.models.handleException
import com.evapharma.integrationwithwearables.core.models.handleResponse
import com.evapharma.integrationwithwearables.core.network.BaseURLFactory
import com.evapharma.integrationwithwearables.core.utils.Constants.USER_ID
import com.evapharma.integrationwithwearables.features.vitals_data.data.remote.model.AllVitalsResponse
import com.evapharma.integrationwithwearables.features.vitals_data.data.remote.model.NewVitalsRequest
import com.evapharma.integrationwithwearables.features.vitals_data.data.remote.model.VitalsCaseResponse
import com.evapharma.integrationwithwearables.features.vitals_data.data.remote.network.addAuthInterceptor
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext
import javax.inject.Inject

class VitalsRemoteDataSourceImpl @Inject constructor() : VitalsRemoteDataSource {

    override suspend fun addVitals(vitals: NewVitalsRequest): DataState<Int> {
        return withContext(Dispatchers.IO) {
            try {
                val retrofit = BaseURLFactory.retrofit.newBuilder()
                    .addAuthInterceptor(USER_ID)
                    .build()
                val apiService = retrofit.create(VitalsApiService::class.java)
                val response = apiService.addVitals(vitals)
                response.handleResponse()
            } catch (t: Throwable) {
                t.handleException()
            }
        }
    }

}