package com.evapharma.integrationwithwearables.features.vitals_data.data.remote.data_source


import com.evapharma.integrationwithwearables.Endpoints
import com.evapharma.integrationwithwearables.core.models.BaseResponse
import com.evapharma.integrationwithwearables.features.vitals_data.data.remote.model.LoginRequest
import com.evapharma.integrationwithwearables.features.vitals_data.data.remote.model.NewVitalsRequest
import retrofit2.Response
import retrofit2.http.Body
import retrofit2.http.POST

interface VitalsApiService {

    @POST(Endpoints.ADD_VITALS)
    @JvmSuppressWildcards
   suspend fun addVitals( @Body vitals: NewVitalsRequest): Response<BaseResponse<Int>>

   @POST(Endpoints.LOGIN)
   @JvmSuppressWildcards
   suspend fun login(@Body phone: LoginRequest): Response<BaseResponse<String>>

}