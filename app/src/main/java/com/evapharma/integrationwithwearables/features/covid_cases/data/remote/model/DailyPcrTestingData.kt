package com.evapharma.integrationwithwearables.features.covid_cases.data.remote.model

import android.os.Parcelable
import kotlinx.parcelize.Parcelize

@Parcelize
data class DailyPcrTestingData(
    val date: String?,
    val pcr_count: String?
) : Parcelable