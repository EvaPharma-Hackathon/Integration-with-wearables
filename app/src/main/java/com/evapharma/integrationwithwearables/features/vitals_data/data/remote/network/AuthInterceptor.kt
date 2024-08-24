package com.evapharma.integrationwithwearables.features.vitals_data.data.remote.network

import com.chuckerteam.chucker.api.ChuckerInterceptor
import com.evapharma.integrationwithwearables.core.utils.StringLocale
import okhttp3.Interceptor
import okhttp3.OkHttpClient
import retrofit2.Retrofit
import java.util.concurrent.TimeUnit

fun Retrofit.Builder.addAuthInterceptor(token: String, timeout: Long = 60): Retrofit.Builder {
    val authInterceptor = Interceptor { chain ->
        val newRequest = chain.request().newBuilder()
            .header("Authorization","Bearer $token")
            .build()
        chain.proceed(newRequest)
    }

    val chuckerInterceptor =
        ChuckerInterceptor.Builder(StringLocale.instance.appContextProvider.getAppContext())
            .build()

    val client = OkHttpClient.Builder()
        .connectTimeout(timeout, TimeUnit.SECONDS)
        .writeTimeout(timeout, TimeUnit.SECONDS)
        .readTimeout(timeout, TimeUnit.SECONDS)
        .addInterceptor(authInterceptor)
        .addInterceptor(chuckerInterceptor)
        .build()

    return this.client(client)
}
