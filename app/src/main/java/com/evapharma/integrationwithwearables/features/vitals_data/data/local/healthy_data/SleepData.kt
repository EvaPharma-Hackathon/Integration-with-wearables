package com.evapharma.integrationwithwearables.features.vitals_data.data.local.healthy_data

import androidx.health.connect.client.HealthConnectClient
import androidx.health.connect.client.records.SleepSessionRecord
import androidx.health.connect.client.request.ReadRecordsRequest
import androidx.health.connect.client.time.TimeRangeFilter
import com.evapharma.integrationwithwearables.core.utils.dateTimeFormatter
import com.evapharma.integrationwithwearables.features.vitals_data.data.local.model.DataType
import com.evapharma.integrationwithwearables.features.vitals_data.data.local.model.VitalsRecord
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
                val start = response.records[0].startTime
                val end = response.records[0].endTime
                sleepData.add(
                    VitalsRecord(
                        metricValue = Duration.between(start, end).toHours().toString(),
                        dataType = DataType.SLEEP,
                        toDatetime = end.atZone(ZoneId.systemDefault()).format(dateTimeFormatter),
                        fromDatetime = start.atZone(ZoneId.systemDefault()).format(dateTimeFormatter)
                    )
                )
            }

        return sleepData
    }
}
