package com.evapharma.integrationwithwearables.features.vitals_data.data.local.data_source

import androidx.health.connect.client.HealthConnectClient
import androidx.health.connect.client.records.BloodGlucoseRecord
import androidx.health.connect.client.records.BloodPressureRecord
import androidx.health.connect.client.records.BodyTemperatureRecord
import androidx.health.connect.client.records.DistanceRecord
import androidx.health.connect.client.records.HeartRateRecord
import androidx.health.connect.client.records.HeightRecord
import androidx.health.connect.client.records.OxygenSaturationRecord
import androidx.health.connect.client.records.Record
import androidx.health.connect.client.records.RespiratoryRateRecord
import androidx.health.connect.client.records.SleepSessionRecord
import androidx.health.connect.client.records.StepsRecord
import androidx.health.connect.client.records.TotalCaloriesBurnedRecord
import androidx.health.connect.client.records.WeightRecord
import androidx.health.connect.client.request.ReadRecordsRequest
import androidx.health.connect.client.time.TimeRangeFilter
import com.evapharma.integrationwithwearables.features.vitals_data.data.remote.model.VitalsData
import java.time.Duration
import java.time.LocalDate
import java.time.LocalDateTime
import java.time.ZoneId
import java.util.Locale
import java.util.TimeZone
import javax.inject.Inject

class VitalsLocalDataSourceImpl @Inject constructor(
    private val healthConnectClient: HealthConnectClient?
) : VitalsLocalDataSource {

    private val startTime = LocalDate.now().atStartOfDay(ZoneId.systemDefault())
    private val endTime = LocalDateTime.now().atZone(TimeZone.getDefault().toZoneId()).minusMinutes(1).plusSeconds(59)

    private suspend inline fun <reified T : Record> readMetric(
        crossinline metricExtractor: (T) -> Double
    ): Double {
        val response = healthConnectClient?.readRecords(
            ReadRecordsRequest(
                T::class,
                timeRangeFilter = TimeRangeFilter.between(
                    startTime.toLocalDate().atStartOfDay(),
                    endTime.toLocalDateTime()
                )
            )
        )
        return response?.records?.takeIf { it.isNotEmpty() }
            ?.map { metricExtractor(it as T) }
            ?.average()
            ?: 0.0
    }

    override suspend fun getVitalsData(): VitalsData {
        return VitalsData(
            steps = String.format(Locale.getDefault(), "%.1f", readMetric<StepsRecord> { it.count.toDouble() }),
            calories = String.format(Locale.getDefault(), "%.1f", readMetric<TotalCaloriesBurnedRecord> { it.energy.inKilocalories }),
            sleep = String.format(Locale.getDefault(), "%.1f", readMetric<SleepSessionRecord> {
                val start = it.startTime
                val end = it.endTime
                Duration.between(start, end).toHours().toDouble() }),
            distance = String.format(Locale.getDefault(), "%.1f", readMetric<DistanceRecord> { it.distance.inMeters }),
            bloodSugar = String.format(Locale.getDefault(), "%.1f", readMetric<BloodGlucoseRecord> { it.level.inMillimolesPerLiter }),
            oxygenSaturation = String.format(Locale.getDefault(), "%.1f", readMetric<OxygenSaturationRecord> { it.percentage.value }),
            heartRate = String.format(Locale.getDefault(), "%.1f", readMetric<HeartRateRecord> { it.samples.map { sample -> sample.beatsPerMinute }.average() }),
            weight = String.format(Locale.getDefault(), "%.1f", readMetric<WeightRecord> { it.weight.inKilograms }),
            height = String.format(Locale.getDefault(), "%.1f", readMetric<HeightRecord> { it.height.inMeters }),
            temperature = String.format(Locale.getDefault(), "%.1f", readMetric<BodyTemperatureRecord> { it.temperature.inCelsius }),
            bloodPressure = getAverageBloodPressure(),
            respiratoryRate = String.format(Locale.getDefault(), "%.1f", readMetric<RespiratoryRateRecord> { it.rate })
        )
    }

    private suspend fun getAverageBloodPressure(): String {
        val response = healthConnectClient?.readRecords(
            ReadRecordsRequest(
                BloodPressureRecord::class,
                timeRangeFilter = TimeRangeFilter.between(startTime.toLocalDate().atStartOfDay(), endTime.toLocalDateTime()
                )
            )
        )
        val averageSystolic = response?.records?.map { it.systolic.inMillimetersOfMercury }?.average() ?: 0.0
        val averageDiastolic = response?.records?.map { it.diastolic.inMillimetersOfMercury }?.average() ?: 0.0
        return "${averageSystolic.toInt()}/${averageDiastolic.toInt()}"
    }
}
