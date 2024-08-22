package com.evapharma.integrationwithwearables.features.vitals_data.data.remote.model

import android.os.Parcelable
import kotlinx.parcelize.Parcelize

@Parcelize
data class DailyPcrTestingData(
    val date: String?,
    val pcr_count: String?
) : Parcelable