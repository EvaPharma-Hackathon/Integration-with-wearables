package com.evapharma.integrationwithwearables.features.covid_cases.data.local.healthy_data

import android.util.Log
import androidx.health.connect.client.HealthConnectClient
import androidx.health.connect.client.records.StepsRecord
import androidx.health.connect.client.request.AggregateGroupByPeriodRequest
import androidx.health.connect.client.time.TimeRangeFilter
import com.evapharma.integrationwithwearables.core.utils.dateTimeFormatter
import com.evapharma.integrationwithwearables.features.covid_cases.data.local.model.DataType
import com.evapharma.integrationwithwearables.features.covid_cases.data.local.model.VitalsData
import java.time.LocalDate
import java.time.LocalDateTime
import java.time.Period
import java.time.ZoneId
import java.util.TimeZone


class StepsData (private val healthConnectClient: HealthConnectClient) : HealthDataReader{
    override suspend fun readDataForInterval(interval: Long): List<VitalsData> {
        val startTime = LocalDate.now().atStartOfDay(ZoneId.systemDefault())
        val endTime = LocalDateTime.now().atZone(TimeZone.getDefault().toZoneId()).minusMinutes(1)
            .plusSeconds(59)
        Log.i("TAG", "readDataForInterval: $startTime   && $endTime")
        val response = healthConnectClient.aggregateGroupByPeriod(
            AggregateGroupByPeriodRequest(
                metrics = setOf(StepsRecord.COUNT_TOTAL),
                timeRangeFilter = TimeRangeFilter.between(
                    startTime.toLocalDate().atStartOfDay(),
                    endTime.toLocalDateTime()
                ),
                timeRangeSlicer = Period.ofDays(1)
            )
        )
        if (response != null) {
            val stepsData = mutableListOf<VitalsData>()
            val totalSteps = response.firstOrNull()?.result?.get(StepsRecord.COUNT_TOTAL) ?: 0
            stepsData.add(
                VitalsData(
                    metricValue = totalSteps.toString(),
                    dataType = DataType.STEPS,
                    toDatetime = endTime.format(dateTimeFormatter),
                    fromDatetime = startTime.format(dateTimeFormatter)
                )
            )
            Log.d("Data", stepsData.toString())
            return stepsData
        }
        return emptyList()
    }
}