package com.evapharma.integrationwithwearables.core.utils

import java.time.format.DateTimeFormatter

val dateTimeFormatter: DateTimeFormatter =
    DateTimeFormatter.ofPattern("yyyy-MM-dd'T'HH:mm:ssXXX")