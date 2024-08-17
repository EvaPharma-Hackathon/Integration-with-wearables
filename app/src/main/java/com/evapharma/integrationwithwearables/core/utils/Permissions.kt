package com.evapharma.integrationwithwearables.core.utils

import androidx.health.connect.client.permission.HealthPermission
import androidx.health.connect.client.records.*

val requiredHealthPermission = setOf(
    // Body Measurements
    HealthPermission.getReadPermission(WeightRecord::class),
    HealthPermission.getWritePermission(WeightRecord::class),

    // Heart Rate and Cardiovascular
    HealthPermission.getReadPermission(HeartRateRecord::class),
    HealthPermission.getWritePermission(HeartRateRecord::class),
    HealthPermission.getReadPermission(BloodPressureRecord::class),
    HealthPermission.getWritePermission(BloodPressureRecord::class),
    HealthPermission.getReadPermission(BloodGlucoseRecord::class),
    HealthPermission.getWritePermission(BloodGlucoseRecord::class),
    HealthPermission.getReadPermission(OxygenSaturationRecord::class),
    HealthPermission.getWritePermission(OxygenSaturationRecord::class),
    HealthPermission.getReadPermission(BodyTemperatureRecord::class),
    HealthPermission.getWritePermission(BodyTemperatureRecord::class),

    // Activity and Fitness
    HealthPermission.getReadPermission(StepsRecord::class),
    HealthPermission.getWritePermission(StepsRecord::class),
    HealthPermission.getReadPermission(TotalCaloriesBurnedRecord::class),
    HealthPermission.getWritePermission(TotalCaloriesBurnedRecord::class),
    HealthPermission.getReadPermission(DistanceRecord::class),
    HealthPermission.getWritePermission(DistanceRecord::class),
    HealthPermission.getReadPermission(SleepSessionRecord::class),
    HealthPermission.getWritePermission(SleepSessionRecord::class),
)

