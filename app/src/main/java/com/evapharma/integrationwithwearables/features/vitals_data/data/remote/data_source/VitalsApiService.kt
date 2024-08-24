package com.evapharma.integrationwithwearables.features.vitals_data.data.remote.data_source


import com.evapharma.integrationwithwearables.Endpoints
import com.evapharma.integrationwithwearables.core.models.BaseResponse
import com.evapharma.integrationwithwearables.features.vitals_data.data.remote.model.NewVitalsRequest
import com.evapharma.integrationwithwearables.features.vitals_data.data.remote.model.VitalsCaseResponse
import retrofit2.Response
import retrofit2.http.Body
import retrofit2.http.GET
import retrofit2.http.POST

interface VitalsApiService {

    @GET(Endpoints.GET_VITALS)
    @JvmSuppressWildcards
    suspend fun getCovidCases(): Response<BaseResponse<VitalsCaseResponse>>

    @POST(Endpoints.ADD_VITALS)
    @JvmSuppressWildcards
   suspend fun addVitals( @Body vitals: NewVitalsRequest): Response<BaseResponse<Int>>
}