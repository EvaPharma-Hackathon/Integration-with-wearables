package com.evapharma.integrationwithwearables.features.vitals_data.data.remote.model

import android.os.Parcelable
import kotlinx.parcelize.Parcelize

@Parcelize
data class DailyAntigenTestingData(
    val antigen_count: String?,
    val date: String?
):Parcelable