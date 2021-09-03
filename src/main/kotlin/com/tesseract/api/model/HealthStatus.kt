package com.tesseract.api.model

import java.time.Duration
import java.time.LocalDateTime

class HealthStatus
(
    var now: LocalDateTime? = null,

    var apiUpAt: LocalDateTime? = null,

    var uptimeSeconds: Duration? = null,

    var message: String? = null
)