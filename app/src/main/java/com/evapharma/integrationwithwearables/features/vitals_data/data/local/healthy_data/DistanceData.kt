package com.evapharma.integrationwithwearables.features.vitals_data.data.local.healthy_data

import androidx.health.connect.client.HealthConnectClient
import androidx.health.connect.client.records.DistanceRecord
import androidx.health.connect.client.request.ReadRecordsRequest
import androidx.health.connect.client.time.TimeRangeFilter
import com.evapharma.integrationwithwearables.core.utils.dateTimeFormatter
import com.evapharma.integrationwithwearables.features.vitals_data.data.local.model.DataType
import com.evapharma.integrationwithwearables.features.vitals_data.data.local.model.VitalsRecord
import java.time.LocalDate
import java.time.LocalDateTime
import java.time.ZoneId
import java.util.Locale
import java.util.TimeZone

class DistanceData(private val healthConnectClient: HealthConnectClient) : HealthDataReader {
    override suspend fun readDataForInterval(interval: Long): List<VitalsRecord> {
        val startTime = LocalDate.now().atStartOfDay(ZoneId.systemDefault())
        val endTime = LocalDateTime.now().atZone(TimeZone.getDefault().toZoneId()).minusMinutes(1)
            .plusSeconds(59)

        val response = healthConnectClient.readRecords(
            ReadRecordsRequest(
                DistanceRecord::class,
                timeRangeFilter = TimeRangeFilter.between(
                    startTime.toLocalDate().atStartOfDay(),
                    endTime.toLocalDateTime()
                )
            )
        )

        val distanceData = mutableListOf<VitalsRecord>()

        if (response.records.isNotEmpty()) {
            val totalDistance = response.records.sumOf { it.distance.inMeters }

            distanceData.add(
                VitalsRecord(
                    metricValue = String.format(Locale.getDefault(),"%.1f", totalDistance),
                    dataType = DataType.DISTANCE,
                    toDatetime = endTime.format(dateTimeFormatter),
                    fromDatetime = startTime.format(dateTimeFormatter)
                )
            )
        } else {
            distanceData.add(
                VitalsRecord(
                    metricValue = "0.0",
                    dataType = DataType.DISTANCE,
                    toDatetime = endTime.format(dateTimeFormatter),
                    fromDatetime = startTime.format(dateTimeFormatter)
                )
            )
        }

        return distanceData
    }
}
