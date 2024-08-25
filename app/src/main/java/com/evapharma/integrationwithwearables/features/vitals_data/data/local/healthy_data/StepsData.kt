package com.evapharma.integrationwithwearables.features.vitals_data.data.local.healthy_data

import androidx.health.connect.client.HealthConnectClient
import androidx.health.connect.client.records.StepsRecord
import androidx.health.connect.client.request.AggregateGroupByPeriodRequest
import androidx.health.connect.client.time.TimeRangeFilter
import com.evapharma.integrationwithwearables.core.utils.dateTimeFormatter
import com.evapharma.integrationwithwearables.features.vitals_data.data.local.model.DataType
import com.evapharma.integrationwithwearables.features.vitals_data.data.local.model.VitalsRecord
import java.time.LocalDate
import java.time.LocalDateTime
import java.time.Period
import java.time.ZoneId
import java.util.TimeZone


class StepsData (private val healthConnectClient: HealthConnectClient) : HealthDataReader{
    override suspend fun readDataForInterval(interval: Long): List<VitalsRecord> {
        val startTime = LocalDate.now().atStartOfDay(ZoneId.systemDefault())
        val endTime = LocalDateTime.now().atZone(TimeZone.getDefault().toZoneId()).minusMinutes(1)
            .plusSeconds(59)
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
        val stepsData = mutableListOf<VitalsRecord>()
        if (response.isNotEmpty() ) {
            val totalSteps = response.firstOrNull()?.result?.get(StepsRecord.COUNT_TOTAL) ?: 0
            stepsData.add(
                VitalsRecord(
                    metricValue = totalSteps.toString(),
                    dataType = DataType.STEPS,
                    toDatetime = endTime.format(dateTimeFormatter),
                    fromDatetime = startTime.format(dateTimeFormatter)
                )
            )
        } else
        {
            stepsData.add(
                VitalsRecord(
                    metricValue = "",
                    dataType = DataType.STEPS,
                    toDatetime = endTime.format(dateTimeFormatter),
                    fromDatetime = startTime.format(dateTimeFormatter)
                )
            )
        }
       return stepsData
    }
}