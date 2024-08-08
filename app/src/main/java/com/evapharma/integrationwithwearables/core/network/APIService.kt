package com.evapharma.integrationwithwearables.core.network

import com.evapharma.integrationwithwearables.core.models.BaseResponse
import com.google.gson.JsonElement
import okhttp3.MultipartBody
import okhttp3.RequestBody
import retrofit2.Response
import retrofit2.http.*

interface APIService {

    @GET
    @JvmSuppressWildcards
    suspend fun getRequest(
        @Url api: String,
        @HeaderMap headers: Map<String, String>?,
        @QueryMap param: Map<String, Any>?
    ): Response<JsonElement>

    @GET
    @JvmSuppressWildcards
    suspend fun <T> getRequestV2(
        @Url api: String,
        @HeaderMap headers: Map<String, String>?,
        @QueryMap param: Map<String, Any>?
    ): Response<BaseResponse<T>>

    @POST
    @JvmSuppressWildcards
    suspend fun  postRequest(
        @Url api: String,
        @HeaderMap headers: Map<String, String>?,
        @Body body: Map<String, Any>?
    ): Response<JsonElement>



    @POST
    @JvmSuppressWildcards
    suspend fun <T> postRequestV2(
        @Url api: String,
        @HeaderMap headers: Map<String, String>?,
        @Body body: Map<String, Any>?
    ): Response<BaseResponse<T>>



    @PUT
    @JvmSuppressWildcards
    suspend fun putRequest(
        @Url api: String,
        @HeaderMap headers: Map<String, String>?,
        @Body body: Map<String, Any>?
    ): Response<JsonElement>

    @PUT
    @JvmSuppressWildcards
    suspend fun <T> putRequestV2(
        @Url api: String,
        @HeaderMap headers: Map<String, String>?,
        @Body body: Map<String, Any>?
    ): Response<BaseResponse<T>>

    /**TODO
     * Put request function that take query map instead of body
     * need to investigate more what is the best practice
     * is it correct that put request can have query parameter or not
     */
    @PUT
    @JvmSuppressWildcards
    suspend fun putRequestWithQuery(
        @Url api: String,
        @HeaderMap headers: Map<String, String>?,
        @QueryMap param: Map<String, Any>?,
    ): Response<JsonElement>

    @PUT
    @JvmSuppressWildcards
    suspend fun <T> putRequestWithQueryV2(
        @Url api: String,
        @HeaderMap headers: Map<String, String>?,
        @QueryMap param: Map<String, Any>?,
    ): Response<BaseResponse<T>>

    @DELETE
    @JvmSuppressWildcards
    suspend fun deleteRequest(
        @Url api: String,
        @HeaderMap headers: Map<String, String>?,
        @QueryMap param: Map<String, Any>?
    ): Response<JsonElement>

    @DELETE
    @JvmSuppressWildcards
    suspend fun <T> deleteRequestV2(
        @Url api: String,
        @HeaderMap headers: Map<String, String>?,
        @QueryMap param: Map<String, Any>?
    ): Response<BaseResponse<T>>

    @Multipart
    @POST
    @JvmSuppressWildcards
    suspend fun postMultiPart(
        @Url api: String,
        @HeaderMap headers: Map<String, String>?,
        @PartMap body: Map<String, Any>?,
        @Part file: MultipartBody.Part?
    ): Response<JsonElement>



    @Multipart
    @POST
    @JvmSuppressWildcards
    suspend fun <T> postMultiPartV2(
        @Url api: String,
        @HeaderMap headers: Map<String, String>?,
        @PartMap body: Map<String, Any>?,
        @Part file: MultipartBody.Part?
    ): Response<BaseResponse<T>>

    @Multipart
    @POST
    @JvmSuppressWildcards
    suspend fun postMultiPart(
        @Url api: String,
        @HeaderMap headers: Map<String, String>?,
        @PartMap body: Map<String, RequestBody?>?,
        @Part multipleFiles: List<MultipartBody.Part?>,
        @Part singleFile: MultipartBody.Part?
    ): Response<JsonElement>

    @Multipart
    @POST
    @JvmSuppressWildcards
    suspend fun <T> postMultiPartV2(
        @Url api: String,
        @HeaderMap headers: Map<String, String>?,
        @PartMap body: Map<String, RequestBody?>?,
        @Part multipleFiles: List<MultipartBody.Part?>,
        @Part singleFile: MultipartBody.Part?
    ): Response<BaseResponse<T>>

    @Multipart
    @POST
    @JvmSuppressWildcards
    suspend fun postMultiPart(
        @Url api: String,
        @HeaderMap headers: Map<String, String>?,
        @PartMap body: Map<String, RequestBody?>?,
        @Part multipleFiles: List<MultipartBody.Part?>?,
        @Part singleFile: MultipartBody.Part?,
        @Part singleFileTwo: MultipartBody.Part?
    ): Response<JsonElement>

    @Multipart
    @POST
    @JvmSuppressWildcards
    suspend fun <T> postMultiPartV2(
        @Url api: String,
        @HeaderMap headers: Map<String, String>?,
        @PartMap body: Map<String, RequestBody?>?,
        @Part multipleFiles: List<MultipartBody.Part?>?,
        @Part singleFile: MultipartBody.Part?,
        @Part singleFileTwo: MultipartBody.Part?
    ): Response<BaseResponse<T>>

    @Multipart
    @PUT
    @JvmSuppressWildcards
    suspend fun putMultiPart(
        @Url api: String,
        @HeaderMap headers: Map<String, String>?,
        @PartMap body: Map<String, Any>?,
        @Part file: MultipartBody.Part?
    ): Response<JsonElement>

    @Multipart
    @PUT
    @JvmSuppressWildcards
    suspend fun <T> putMultiPartV2(
        @Url api: String,
        @HeaderMap headers: Map<String, String>?,
        @PartMap body: Map<String, Any>?,
        @Part file: MultipartBody.Part?
    ): Response<BaseResponse<T>>


    @Multipart
    @POST
    @JvmSuppressWildcards
    suspend fun postMultiPart(
        @Url api: String,
        @HeaderMap headers: Map<String, String>?,
        @PartMap body: Map<String, RequestBody?>?,
        @Part multipleFiles: List<MultipartBody.Part?>,
    ): Response<JsonElement>

    @Multipart
    @POST
    @JvmSuppressWildcards
    suspend fun <T> postMultiPartV2(
        @Url api: String,
        @HeaderMap headers: Map<String, String>?,
        @PartMap body: Map<String, RequestBody?>?,
        @Part multipleFiles: List<MultipartBody.Part?>,
    ): Response<BaseResponse<T>>

    @Multipart
    @PUT
    @JvmSuppressWildcards
    suspend fun putMultiPart(
        @Url api: String,
        @HeaderMap headers: Map<String, String>?,
        @PartMap body: Map<String, RequestBody?>?,
        @Part multipleFiles: List<MultipartBody.Part?>,
    ): Response<JsonElement>

    @Multipart
    @PUT
    @JvmSuppressWildcards
    suspend fun <T> putMultiPartV2(
        @Url api: String,
        @HeaderMap headers: Map<String, String>?,
        @PartMap body: Map<String, RequestBody?>?,
        @Part multipleFiles: List<MultipartBody.Part?>,
    ): Response<BaseResponse<T>>

}