package com.evapharma.integrationwithwearables.features.covid_cases.data.remote.data_source


import com.evapharma.integrationwithwearables.Endpoints
import com.evapharma.integrationwithwearables.core.models.BaseResponse
import com.evapharma.integrationwithwearables.features.covid_cases.data.remote.model.CovidCasesResponse
import retrofit2.Response
import retrofit2.http.GET

interface CovidCasesApiService {

    @GET(Endpoints.GET_COVID_CASES)
    @JvmSuppressWildcards
    suspend fun getCovidCases(): Response<BaseResponse<CovidCasesResponse>>


}