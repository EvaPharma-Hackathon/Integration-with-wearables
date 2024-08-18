package com.evapharma.integrationwithwearables.features.covid_cases.data.local.healthy_data

import android.util.Log
import androidx.health.connect.client.HealthConnectClient
import androidx.health.connect.client.records.SleepSessionRecord
import androidx.health.connect.client.request.ReadRecordsRequest
import androidx.health.connect.client.time.TimeRangeFilter
import com.evapharma.integrationwithwearables.core.utils.dateTimeFormatter
import com.evapharma.integrationwithwearables.features.covid_cases.data.local.model.DataType
import com.evapharma.integrationwithwearables.features.covid_cases.data.local.model.VitalsRecord
import java.time.Duration
import java.time.LocalDate
import java.time.ZoneId

class SleepData(private val healthConnectClient: HealthConnectClient) : HealthDataReader {

    override suspend fun readDataForInterval(interval: Long): List<VitalsRecord> {
        val startOfDay = LocalDate.now().atStartOfDay(ZoneId.systemDefault())
        val endOfDay = startOfDay.plusDays(1).minusNanos(1)
        val sleepData = mutableListOf<VitalsRecord>()
        val response = healthConnectClient.readRecords(
            ReadRecordsRequest(
                SleepSessionRecord::class,
                timeRangeFilter = TimeRangeFilter.between(
                    startOfDay.toLocalDateTime(),
                    endOfDay.toLocalDateTime()
                )
            )
        )
        if (response != null) {
            if (response.records.isEmpty()) {
                sleepData.add(
                    VitalsRecord(
                        metricValue = "0",
                        dataType = DataType.SLEEP,
                        toDatetime = endOfDay.format(dateTimeFormatter),
                        fromDatetime = startOfDay.format(dateTimeFormatter)
                    )
                )
            } else {
                var start = response.records[0].startTime
                var end = response.records[0].endTime
                for (index in 1 until response.records.size) {
                    if (response.records[index].startTime > end) {
                        sleepData.add(
                            VitalsRecord(
                                metricValue = Duration.between(start, end).toHours().toString(),
                                dataType = DataType.SLEEP,
                                toDatetime = end.atZone(ZoneId.systemDefault()).format(dateTimeFormatter),
                                fromDatetime = start.atZone(ZoneId.systemDefault()).format(dateTimeFormatter)
                            )
                        )
                        start = response.records[index].startTime
                        end = response.records[index].endTime
                    } else if (response.records[index].endTime >= end) {
                        end = response.records[index].endTime
                    }
                }
                sleepData.add(
                    VitalsRecord(
                        metricValue = Duration.between(start, end).toHours().toString(),
                        dataType = DataType.SLEEP,
                        toDatetime = end.atZone(ZoneId.systemDefault()).format(dateTimeFormatter),
                        fromDatetime = start.atZone(ZoneId.systemDefault()).format(dateTimeFormatter)
                    )
                )
            }
        }
        Log.d("data", sleepData.toString())
        return sleepData
    }
}
