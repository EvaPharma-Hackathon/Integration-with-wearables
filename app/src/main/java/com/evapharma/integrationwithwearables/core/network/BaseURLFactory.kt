package com.evapharma.integrationwithwearables.core.network

import com.chuckerteam.chucker.api.ChuckerInterceptor
import com.evapharma.integrationwithwearables.core.utils.Constants
import com.evapharma.integrationwithwearables.core.utils.StringLocale
import com.google.gson.GsonBuilder
import okhttp3.OkHttpClient
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import java.util.concurrent.TimeUnit


class BaseURLFactory {

    companion object {

        val gson = GsonBuilder().setLenient().create()

        var retrofit: Retrofit = provideRetrofit()

        fun resetBaseURL(url: String) {
            retrofit = Retrofit.Builder()
                .baseUrl(url)
                .client(provideOkHttpClient())
                .addConverterFactory(GsonConverterFactory.create())
                .build()
        }


        fun provideRetrofit(timeout: Long = 30): Retrofit =
            Retrofit.Builder()
                .baseUrl(Constants.BASE_URL)
                .client(provideOkHttpClient(timeout))
                .addConverterFactory(GsonConverterFactory.create(gson))
                .build()


        private fun provideOkHttpClient(timeout: Long = 30): OkHttpClient {
            val chuckerInterceptor =
                ChuckerInterceptor.Builder(StringLocale.instance.appContextProvider.getAppContext())
                    .build()
            val okHttpClient = OkHttpClient.Builder()
                .connectTimeout(timeout, TimeUnit.SECONDS)
                .writeTimeout(timeout, TimeUnit.SECONDS)
                .readTimeout(timeout, TimeUnit.SECONDS)
                .addInterceptor(chuckerInterceptor)

            return okHttpClient.build()
        }

    }

}

