package com.evapharma.integrationwithwearables.core.utils

import androidx.health.connect.client.permission.HealthPermission
import androidx.health.connect.client.records.*

val requiredHealthPermission = setOf(
    HealthPermission.getReadPermission(StepsRecord::class),
    HealthPermission.getReadPermission(SleepSessionRecord::class),
    HealthPermission.getReadPermission(DistanceRecord::class),
    HealthPermission.getReadPermission(ExerciseSessionRecord::class),
    HealthPermission.getReadPermission(TotalCaloriesBurnedRecord::class),
    HealthPermission.getReadPermission(BloodGlucoseRecord::class),
    HealthPermission.getWritePermission(StepsRecord::class),
    HealthPermission.getWritePermission(SleepSessionRecord::class),
    HealthPermission.getWritePermission(DistanceRecord::class),
    HealthPermission.getWritePermission(ExerciseSessionRecord::class),
    HealthPermission.getWritePermission(TotalCaloriesBurnedRecord::class),
    HealthPermission.getWritePermission(BloodGlucoseRecord::class)

)

