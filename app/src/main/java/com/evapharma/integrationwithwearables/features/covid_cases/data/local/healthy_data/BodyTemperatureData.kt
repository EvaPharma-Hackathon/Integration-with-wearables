package com.evapharma.integrationwithwearables.features.covid_cases.data.local.healthy_data

import android.util.Log
import androidx.health.connect.client.HealthConnectClient
import androidx.health.connect.client.records.BodyTemperatureRecord
import androidx.health.connect.client.request.ReadRecordsRequest
import androidx.health.connect.client.time.TimeRangeFilter
import com.evapharma.integrationwithwearables.core.utils.dateTimeFormatter
import com.evapharma.integrationwithwearables.features.covid_cases.data.local.model.DataType
import com.evapharma.integrationwithwearables.features.covid_cases.data.local.model.VitalsRecord
import java.time.LocalDate
import java.time.LocalDateTime
import java.time.ZoneId
import java.util.Locale
import java.util.TimeZone

class BodyTemperatureData(private val healthConnectClient: HealthConnectClient) : HealthDataReader {
    override suspend fun readDataForInterval(interval: Long): List<VitalsRecord> {
        val startTime = LocalDate.now().atStartOfDay(ZoneId.systemDefault())
        val endTime = LocalDateTime.now().atZone(TimeZone.getDefault().toZoneId()).minusMinutes(1)
            .plusSeconds(59)
        Log.i("TAG", "readDataForInterval: $startTime && $endTime")

        val response = healthConnectClient.readRecords(
            ReadRecordsRequest(
                BodyTemperatureRecord::class,
                timeRangeFilter = TimeRangeFilter.between(
                    startTime.toLocalDate().atStartOfDay(),
                    endTime.toLocalDateTime()
                )
            )
        )

        val temperatureData = mutableListOf<VitalsRecord>()

        if (response.records.isNotEmpty()) {
            val averageTemperature = response.records
                .map { it.temperature.inCelsius }
                .average()

            temperatureData.add(
                VitalsRecord(
                    metricValue = String.format(Locale.getDefault(), "%.1f", averageTemperature),
                    dataType = DataType.TEMPERATURE,
                    toDatetime = endTime.format(dateTimeFormatter),
                    fromDatetime = startTime.format(dateTimeFormatter)
                )
            )
        } else {
            temperatureData.add(
                VitalsRecord(
                    metricValue = "0.0",
                    dataType = DataType.TEMPERATURE,
                    toDatetime = endTime.format(dateTimeFormatter),
                    fromDatetime = startTime.format(dateTimeFormatter)
                )
            )
        }

        Log.d("Data", temperatureData.toString())
        return temperatureData
    }
}
