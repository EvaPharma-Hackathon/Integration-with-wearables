package com.evapharma.integrationwithwearables.features.vitals_data.data.remote.data_source


import com.evapharma.integrationwithwearables.Endpoints
import com.evapharma.integrationwithwearables.core.models.BaseResponse
import com.evapharma.integrationwithwearables.features.vitals_data.data.remote.model.VitalsCaseResponse
import retrofit2.Response
import retrofit2.http.GET

interface VitalsApiService {

    @GET(Endpoints.GET_VITALS)
    @JvmSuppressWildcards
    suspend fun getCovidCases(): Response<BaseResponse<VitalsCaseResponse>>


}