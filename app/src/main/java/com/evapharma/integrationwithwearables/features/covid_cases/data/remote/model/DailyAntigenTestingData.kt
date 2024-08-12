package com.evapharma.integrationwithwearables.features.covid_cases.data.remote.model

import android.os.Parcelable
import kotlinx.parcelize.Parcelize

@Parcelize
data class DailyAntigenTestingData(
    val antigen_count: String?,
    val date: String?
):Parcelable