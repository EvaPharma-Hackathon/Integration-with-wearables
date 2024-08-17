package com.evapharma.integrationwithwearables.features.covid_cases.data.local.healthy_data

import androidx.health.connect.client.HealthConnectClient
import androidx.health.connect.client.records.TotalCaloriesBurnedRecord
import androidx.health.connect.client.request.AggregateGroupByPeriodRequest
import androidx.health.connect.client.time.TimeRangeFilter
import com.evapharma.integrationwithwearables.core.utils.dateTimeFormatter
import com.evapharma.integrationwithwearables.features.covid_cases.data.local.model.DataType
import com.evapharma.integrationwithwearables.features.covid_cases.data.local.model.VitalsData
import java.time.Duration
import java.time.LocalDateTime
import java.time.LocalTime
import java.time.Period
import java.time.ZoneId


class CaloriesData  (private val healthConnectClient: HealthConnectClient) : HealthDataReader{
    override suspend fun readDataForInterval(interval: Long): List<VitalsData> {
        val today = LocalDateTime.now().toLocalDate()
        val startTime = today.atStartOfDay(ZoneId.systemDefault())
        val endTime = today.atTime(LocalTime.MAX).atZone(ZoneId.systemDefault())
        val response = healthConnectClient.aggregateGroupByPeriod(
            AggregateGroupByPeriodRequest(
                metrics = setOf(TotalCaloriesBurnedRecord.ENERGY_TOTAL),
                timeRangeFilter = TimeRangeFilter.between(
                    startTime.toLocalDateTime(),
                    endTime.toLocalDateTime()
                ),
                timeRangeSlicer = Period.ofDays(1)
            )
        )
        val caloriesData = mutableListOf<VitalsData>()
        response.let {
            it.sortedBy { record -> record.startTime }
            var trackTime = startTime.toLocalDate().atStartOfDay()
            for (dailyResult in it) {
                if (dailyResult.startTime.isAfter(trackTime)) {
                    while (trackTime.isBefore(dailyResult.startTime)) {
                        caloriesData.add(
                            VitalsData(
                                metricValue = "0",
                                dataType = DataType.CALORIES,
                                toDatetime = trackTime.toLocalDate().atTime(LocalTime.MAX)
                                    .atZone(ZoneId.systemDefault()).format(dateTimeFormatter),
                                fromDatetime = if (trackTime.toLocalDate().isEqual(startTime.toLocalDate()))
                                    startTime.format(dateTimeFormatter)
                                else trackTime.atZone(ZoneId.systemDefault()).format(dateTimeFormatter)
                            )
                        )
                        trackTime = trackTime.plusDays(1).toLocalDate().atStartOfDay()
                    }
                }
                val totalKilojoules = dailyResult.result[TotalCaloriesBurnedRecord.ENERGY_TOTAL]?.inKilojoules?.toInt()
                caloriesData.add(
                    VitalsData(
                        metricValue = (totalKilojoules ?: 0).toString(),
                        dataType = DataType.CALORIES,
                        toDatetime = dailyResult.endTime.atZone(ZoneId.systemDefault())
                            .minusSeconds(1).format(dateTimeFormatter),
                        fromDatetime = if (dailyResult.startTime.toLocalDate().isEqual(startTime.toLocalDate()))
                            startTime.format(dateTimeFormatter)
                        else dailyResult.startTime.atZone(ZoneId.systemDefault()).format(dateTimeFormatter)
                    )
                )
                trackTime = dailyResult.endTime
            }
            while (trackTime.isBefore(endTime.toLocalDateTime()) && Duration.between(trackTime, endTime).toMinutes() > 1) {
                caloriesData.add(
                    VitalsData(
                        metricValue = "0",
                        dataType = DataType.CALORIES,
                        toDatetime = if (trackTime.toLocalDate().isEqual(endTime.toLocalDate()))
                            endTime.format(dateTimeFormatter)
                        else trackTime.toLocalDate().atTime(LocalTime.MAX).atZone(ZoneId.systemDefault()).format(dateTimeFormatter),
                        fromDatetime = if (trackTime.toLocalDate().isEqual(startTime.toLocalDate()))
                            startTime.format(dateTimeFormatter)
                        else trackTime.atZone(ZoneId.systemDefault()).format(dateTimeFormatter)
                    )
                )
                trackTime = trackTime.plusDays(1)
            }
        }
        return caloriesData
    }

}