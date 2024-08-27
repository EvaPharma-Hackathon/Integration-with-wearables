package com.evapharma.integrationwithwearables.features.vitals_data.data.remote.data_source

import com.evapharma.integrationwithwearables.DataStoreManager
import com.evapharma.integrationwithwearables.core.models.DataState
import com.evapharma.integrationwithwearables.core.models.handleException
import com.evapharma.integrationwithwearables.core.models.handleResponse
import com.evapharma.integrationwithwearables.core.network.BaseURLFactory
import com.evapharma.integrationwithwearables.core.utils.Constants.USER_ID
import com.evapharma.integrationwithwearables.features.vitals_data.data.remote.model.LoginRequest
import com.evapharma.integrationwithwearables.features.vitals_data.data.remote.model.NewVitalsRequest
import com.evapharma.integrationwithwearables.features.vitals_data.data.remote.network.addAuthInterceptor
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.withContext
import javax.inject.Inject

class VitalsRemoteDataSourceImpl @Inject constructor(private val dataStoreManager: DataStoreManager) : VitalsRemoteDataSource {

    override suspend fun addVitals(vitals: NewVitalsRequest): DataState<Int> {
        return withContext(Dispatchers.IO) {
            try {
                val userId = dataStoreManager.userId.first() ?: ""
                val retrofit = BaseURLFactory.retrofit.newBuilder()
                    .addAuthInterceptor(userId)
                    .build()
                val apiService = retrofit.create(VitalsApiService::class.java)
                val response = apiService.addVitals(vitals)
                response.handleResponse()
            } catch (t: Throwable) {
                t.handleException()
            }
        }
    }

    override  suspend fun login(phone: LoginRequest) : DataState<String> {
        return withContext(Dispatchers.IO){
            try {
                val retrofit = BaseURLFactory.retrofit.newBuilder()
                    .build()
                val apiService = retrofit.create(VitalsApiService::class.java)
                val response = apiService.login(phone)
                response.handleResponse()
            } catch (t: Throwable) {
                t.handleException()
            }
        }
    }

}