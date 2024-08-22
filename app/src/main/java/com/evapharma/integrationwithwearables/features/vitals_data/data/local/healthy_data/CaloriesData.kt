package com.evapharma.integrationwithwearables.features.vitals_data.data.local.healthy_data

import androidx.health.connect.client.records.TotalCaloriesBurnedRecord
import com.evapharma.integrationwithwearables.core.utils.dateTimeFormatter
import androidx.health.connect.client.HealthConnectClient
import androidx.health.connect.client.request.ReadRecordsRequest
import androidx.health.connect.client.time.TimeRangeFilter
import com.evapharma.integrationwithwearables.features.vitals_data.data.local.model.DataType
import com.evapharma.integrationwithwearables.features.vitals_data.data.local.model.VitalsRecord
import java.time.LocalDate
import java.time.LocalDateTime
import java.time.ZoneId
import java.util.TimeZone

class CaloriesData(private val healthConnectClient: HealthConnectClient) : HealthDataReader {
    override suspend fun readDataForInterval(interval: Long): List<VitalsRecord> {
        val startTime = LocalDate.now().atStartOfDay(ZoneId.systemDefault())
        val endTime = LocalDateTime.now().atZone(TimeZone.getDefault().toZoneId()).minusMinutes(1)
            .plusSeconds(59)

        val response = healthConnectClient.readRecords(
            ReadRecordsRequest(
                TotalCaloriesBurnedRecord::class,
                timeRangeFilter = TimeRangeFilter.between(  startTime.toLocalDate().atStartOfDay(),
                    endTime.toLocalDateTime())
            )
        )

        val caloriesData = mutableListOf<VitalsRecord>()

        if (response.records.isNotEmpty()) {
            val totalCalories = response.records.sumOf { it.energy.inKilocalories }.toInt()

            caloriesData.add(
                VitalsRecord(
                    metricValue = totalCalories.toString(),
                    dataType = DataType.CALORIES,
                    toDatetime = endTime.format(dateTimeFormatter),
                    fromDatetime = startTime.format(dateTimeFormatter)
                )
            )
        } else {
            caloriesData.add(
                VitalsRecord(
                    metricValue = "0",
                    dataType = DataType.CALORIES,
                    toDatetime = endTime.format(dateTimeFormatter),
                    fromDatetime = startTime.format(dateTimeFormatter)
                )
            )
        }

        return caloriesData
    }
}
