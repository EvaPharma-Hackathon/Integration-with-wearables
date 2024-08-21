package com.evapharma.integrationwithwearables.features.covid_cases.data.local.healthy_data

import android.util.Log
import androidx.health.connect.client.HealthConnectClient
import androidx.health.connect.client.records.RespiratoryRateRecord
import androidx.health.connect.client.request.ReadRecordsRequest
import androidx.health.connect.client.time.TimeRangeFilter
import com.evapharma.integrationwithwearables.core.utils.dateTimeFormatter
import com.evapharma.integrationwithwearables.features.covid_cases.data.local.model.DataType
import com.evapharma.integrationwithwearables.features.covid_cases.data.local.model.VitalsRecord
import java.time.LocalDate
import java.time.LocalDateTime
import java.time.ZoneId
import java.util.TimeZone

class RespiratoryRateData(private val healthConnectClient: HealthConnectClient) : HealthDataReader {

    override suspend fun readDataForInterval(interval: Long): List<VitalsRecord> {
        val startTime = LocalDate.now().atStartOfDay(ZoneId.systemDefault())
        val endTime = LocalDateTime.now().atZone(TimeZone.getDefault().toZoneId()).minusMinutes(1)
            .plusSeconds(59)

            val response = healthConnectClient.readRecords(
                ReadRecordsRequest(
                    RespiratoryRateRecord::class,
                    timeRangeFilter = TimeRangeFilter.between(
                        startTime.toLocalDate().atStartOfDay(),
                        endTime.toLocalDateTime()
                    )
                )
            )

            val respiratoryRateData = mutableListOf<VitalsRecord>()

            if (response.records.isNotEmpty()) {
                val averageRate = response.records.map { it.rate }.average()

                respiratoryRateData.add(
                    VitalsRecord(
                        metricValue = averageRate.toString(),
                        dataType = DataType.RESPIRATORY_RATE,
                        toDatetime = endTime.format(dateTimeFormatter),
                        fromDatetime = startTime.format(dateTimeFormatter)
                    )
                )
            } else {
                respiratoryRateData.add(
                    VitalsRecord(
                        metricValue = "0.0",
                        dataType = DataType.RESPIRATORY_RATE,
                        toDatetime = endTime.format(dateTimeFormatter),
                        fromDatetime = startTime.format(dateTimeFormatter)
                    )
                )
            }
           return  respiratoryRateData
    }
}
