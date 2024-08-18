package com.evapharma.integrationwithwearables.core.utils

import android.Manifest
import android.os.Build
import androidx.annotation.RequiresApi
import java.time.format.DateTimeFormatter

object Constants {


    const val LOCAL_ERROR_CODE = 0

    const val REQUEST_CODE_PERMISSIONS = 1


    @RequiresApi(Build.VERSION_CODES.TIRAMISU)
    const val READ_EXTERNAL_STORAGE = Manifest.permission.READ_MEDIA_IMAGES
    const val WRITE_EXTERNAL_STORAGE = Manifest.permission.WRITE_EXTERNAL_STORAGE

    //Date format

    const val DAY_MONTH_YEAR_FORMAT = "dd-MM-yyyy"
    const val YEAR_MONTH_DAY_FORMAT = "yyyy-MM-dd"
    const val EMPTY_STRING = ""
    const val BASE_URL = "https://www.hpb.health.gov.lk/"
    const val ONE = "1"
    const val TEST = "Test"
    const val PAGE_SIZE = 20
    const val ACCEPT = "Accept"
    const val APPLICATION_JSON = "application/json"
    const val ACCEPT_LANGUAGE = "Accept-Language"
    const val REFRESH_TOKEN = "refreshToken"
    const val AUTHORIZATION = "Authorization"
    const val BEARER = "Bearer"
    const val DATA = "data"
    const val MESSAGE = "message"

}