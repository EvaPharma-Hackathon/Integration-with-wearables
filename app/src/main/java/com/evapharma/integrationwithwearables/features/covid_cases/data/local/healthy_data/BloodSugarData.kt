package com.evapharma.integrationwithwearables.features.covid_cases.data.local.healthy_data

import android.util.Log
import androidx.health.connect.client.HealthConnectClient
import androidx.health.connect.client.records.BloodGlucoseRecord
import androidx.health.connect.client.request.ReadRecordsRequest
import androidx.health.connect.client.time.TimeRangeFilter
import com.evapharma.integrationwithwearables.core.utils.dateTimeFormatter
import com.evapharma.integrationwithwearables.features.covid_cases.data.local.model.DataType
import com.evapharma.integrationwithwearables.features.covid_cases.data.local.model.VitalsRecord
import java.time.LocalDate
import java.time.LocalDateTime
import java.time.ZoneId
import java.util.TimeZone

class BloodSugarData(private val healthConnectClient: HealthConnectClient) : HealthDataReader {
    override suspend fun readDataForInterval(interval: Long): List<VitalsRecord> {
        val startTime = LocalDate.now().atStartOfDay(ZoneId.systemDefault())
        val endTime = LocalDateTime.now().atZone(TimeZone.getDefault().toZoneId()).minusMinutes(1)
            .plusSeconds(59)
        Log.i("TAG", "readDataForInterval: $startTime   && $endTime")

        val response = healthConnectClient.readRecords(
            ReadRecordsRequest(
                BloodGlucoseRecord::class,
                timeRangeFilter = TimeRangeFilter.between(
                    startTime.toLocalDate().atStartOfDay(),
                    endTime.toLocalDateTime()
                )
            )
        )

        val bloodSugarData = mutableListOf<VitalsRecord>()

        if (response.records.isNotEmpty()) {
            val averageBloodSugar = response.records
                .map { it.level.inMillimolesPerLiter }
                .average()

            bloodSugarData.add(
                VitalsRecord(
                    metricValue = averageBloodSugar.toString(),
                    dataType = DataType.BLOOD_SUGAR,
                    toDatetime = endTime.format(dateTimeFormatter),
                    fromDatetime = startTime.format(dateTimeFormatter)
                )
            )
        } else {
            bloodSugarData.add(
                VitalsRecord(
                    metricValue = "0.0",
                    dataType = DataType.BLOOD_SUGAR,
                    toDatetime = endTime.format(dateTimeFormatter),
                    fromDatetime = startTime.format(dateTimeFormatter)
                )
            )
        }

        Log.d("Data", bloodSugarData.toString())
        return bloodSugarData
    }
}
