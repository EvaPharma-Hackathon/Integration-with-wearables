package com.evapharma.integrationwithwearables.features.covid_cases.data.local.healthy_data


import android.util.Log
import androidx.health.connect.client.HealthConnectClient
import androidx.health.connect.client.records.BloodPressureRecord
import androidx.health.connect.client.request.ReadRecordsRequest
import androidx.health.connect.client.time.TimeRangeFilter
import com.evapharma.integrationwithwearables.core.utils.dateTimeFormatter
import com.evapharma.integrationwithwearables.features.covid_cases.data.local.model.DataType
import com.evapharma.integrationwithwearables.features.covid_cases.data.local.model.VitalsRecord
import java.time.LocalDate
import java.time.LocalDateTime
import java.time.ZoneId
import java.util.TimeZone

class BloodPressureData(private val healthConnectClient: HealthConnectClient) : HealthDataReader {

    override suspend fun readDataForInterval(interval: Long): List<VitalsRecord> {
        val startTime = LocalDate.now().atStartOfDay(ZoneId.systemDefault())
        val endTime = LocalDateTime.now().atZone(TimeZone.getDefault().toZoneId()).minusMinutes(1)
            .plusSeconds(59)

        Log.i("TAG", "readDataForInterval: StartTime: $startTime   EndTime: $endTime")

        return try {
            val response = healthConnectClient.readRecords(
                ReadRecordsRequest(
                    BloodPressureRecord::class,
                    timeRangeFilter = TimeRangeFilter.between(
                        startTime.toLocalDate().atStartOfDay(),
                        endTime.toLocalDateTime()
                    )
                )
            )

            val bloodPressureData = mutableListOf<VitalsRecord>()

            if (response.records.isNotEmpty()) {
                val averageSystolic = response.records
                    .map { it.systolic.inMillimetersOfMercury }
                    .average()
                val averageDiastolic = response.records
                    .map { it.diastolic.inMillimetersOfMercury }
                    .average()

                bloodPressureData.add(
                    VitalsRecord(
                        metricValue = "Systolic: ${averageSystolic.toString()}, Diastolic: ${averageDiastolic.toString()}",
                        dataType = DataType.BLOOD_PRESSURE,
                        toDatetime = endTime.format(dateTimeFormatter),
                        fromDatetime = startTime.format(dateTimeFormatter)
                    )
                )
            } else {
                bloodPressureData.add(
                    VitalsRecord(
                        metricValue = "0.0",
                        dataType = DataType.BLOOD_PRESSURE,
                        toDatetime = endTime.format(dateTimeFormatter),
                        fromDatetime = startTime.format(dateTimeFormatter)
                    )
                )
            }

            Log.d("Data", bloodPressureData.toString())
            bloodPressureData

        } catch (e: Exception) {
            Log.e("Error", "Failed to read blood pressure data", e)
            listOf(
                VitalsRecord(
                    metricValue = "0.0",
                    dataType = DataType.BLOOD_PRESSURE,
                    toDatetime = endTime.format(dateTimeFormatter),
                    fromDatetime = startTime.format(dateTimeFormatter)
                )
            )
        }
    }
}
