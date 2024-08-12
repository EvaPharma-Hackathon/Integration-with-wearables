package com.evapharma.integrationwithwearables.features.covid_cases.data.remote.model

import com.evapharma.integrationwithwearables.features.covid_cases.data.remote.model.DailyAntigenTestingData
import com.evapharma.integrationwithwearables.features.covid_cases.data.remote.model.DailyPcrTestingData


data class Data(
    val daily_antigen_testing_data: List<DailyAntigenTestingData?>?,
    val daily_pcr_testing_data: List<DailyPcrTestingData?>?,
    val global_deaths: Int?,
    val global_new_cases: Int?,
    val global_new_deaths: Int?,
    val global_recovered: Int?,
    val global_total_cases: Int?,
    val hospital_data: List<Any?>?,
    val local_active_cases: Int?,
    val local_deaths: Int?,
    val local_new_cases: Int?,
    val local_new_deaths: Int?,
    val local_recovered: Int?,
    val local_total_cases: Int?,
    val local_total_number_of_individuals_in_hospitals: Int?,
    val total_antigen_testing_count: Int?,
    val total_pcr_testing_count: Int?,
    val update_date_time: String?
)