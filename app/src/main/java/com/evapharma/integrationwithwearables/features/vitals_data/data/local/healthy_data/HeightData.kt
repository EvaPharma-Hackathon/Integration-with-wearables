package com.evapharma.integrationwithwearables.features.vitals_data.data.local.healthy_data

import androidx.health.connect.client.HealthConnectClient
import androidx.health.connect.client.records.HeightRecord
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

class HeightData(private val healthConnectClient: HealthConnectClient) : HealthDataReader {
    override suspend fun readDataForInterval(interval: Long): List<VitalsRecord> {
        val startTime = LocalDate.now().atStartOfDay(ZoneId.systemDefault())
        val endTime = LocalDateTime.now().atZone(TimeZone.getDefault().toZoneId()).minusMinutes(1)
            .plusSeconds(59)

        val response = healthConnectClient.readRecords(
            ReadRecordsRequest(
                HeightRecord::class,
                timeRangeFilter = TimeRangeFilter.between(
                    startTime.toLocalDate().atStartOfDay(),
                    endTime.toLocalDateTime()
                )
            )
        )

        val heightData = mutableListOf<VitalsRecord>()

        if (response.records.isNotEmpty()) {
            val averageHeight = response.records.map { it.height.inMeters }.average()

            heightData.add(
                VitalsRecord(
                    metricValue = String.format(Locale.getDefault(),"%.1f", averageHeight*100),
                    dataType = DataType.HEIGHT,
                    toDatetime = endTime.format(dateTimeFormatter),
                    fromDatetime = startTime.format(dateTimeFormatter)
                )
            )
        } else {
            heightData.add(
                VitalsRecord(
                    metricValue = "0",
                    dataType = DataType.HEIGHT,
                    toDatetime = endTime.format(dateTimeFormatter),
                    fromDatetime = startTime.format(dateTimeFormatter)
                )
            )
        }
          return heightData
    }
}
