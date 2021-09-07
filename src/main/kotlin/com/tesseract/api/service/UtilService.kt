package com.tesseract.api.service

import com.tesseract.api.model.HealthStatus
import org.springframework.stereotype.Service
import java.io.BufferedInputStream
import java.io.ByteArrayInputStream
import java.net.URLConnection
import java.time.*
import java.time.format.DateTimeFormatter
import java.util.*
import javax.annotation.PostConstruct

@Service
class UtilService
{
    lateinit var apiUpDateTimeString: String

    @PostConstruct
    fun init()
    {
        apiUpDateTimeString = getDate(true)
    }

    /**
     * Get current health status
     * @param none
     * @return HealthStatus current API status
     * @throws none
     **/
    fun getHealthStatus(): HealthStatus
    {
        val nowDateTime = stringToDatetime(getDate(true))
        val apiUpDateTime = stringToDatetime(apiUpDateTimeString)
        val uptime = Duration.between(apiUpDateTime, nowDateTime)
        val message = "Up from: $apiUpDateTimeString to now: $nowDateTime, uptime in seconds: $uptime"

        return HealthStatus(nowDateTime, apiUpDateTime, uptime, message)
    }

    /**
     * Get current UTC date with or without time as yyyy-MM-dd'T'HH:mm:ss.SSS'Z'
     * @param includeTime Include time
     * @return String of UTC date or date time
     * @throws none
     **/
    fun getDate(includeTime: Boolean = false): String
    {
        val instant = Instant.now().atZone(ZoneOffset.UTC)

        if(includeTime)
            return DateTimeFormatter.ofPattern("yyyy-MM-dd'T'HH:mm:ss.SSS'Z'").format(instant)

        return DateTimeFormatter.ofPattern("yyyy-MM-dd").format(instant)
    }

    /**
     * Convert yyyy-MM-dd'T'HH:mm:ss.SSS'Z' String to Instant
     * @param datetimeString String datetime, either yyyy-MM-dd or yyyy-MM-dd'T'HH:mm:ss.SSS'Z'
     * @return LocalDateTime of string
     * @throws none
     **/
    fun stringToDatetime(datetimeString: String): LocalDateTime?
    {
        return try
        {
            var adjustedDatetimeString: String = datetimeString
            if(datetimeString.length < 11) // Only date, add midnight time
                adjustedDatetimeString += "T00:00:00.000Z"

            val format = DateTimeFormatter.ofPattern("yyyy-MM-dd'T'HH:mm:ss.SSS'Z'", Locale.ENGLISH)
            val temporalAccessor = format.parse(adjustedDatetimeString)
            LocalDateTime.from(temporalAccessor)
        }
        catch (e: Exception)
        {
            null
        }
    }
}

/**
 * Clean up text
 * @param this String to clean
 * @return String
 * @throws none
 **/
fun String.cleanString(): String
{
    return this.replace(Regex("""\h+\v+"""), " ").replace(Regex("""\n+"""), "\n")
}

/**
 * Get file content from base64 string
 * @param this String get content from
 * @return String
 * @throws none
 **/
fun String.base64FileContent(): String
{
    return if(this.contains(",")) this.split(",")[1].split("/")[1] else this
}

/**
 * Get filetype extension content from base64 string
 * @param this String get content from
 * @return String
 * @throws none
 **/
fun String.base64FileExtension(): String?
{
    return if(this.contains(";")) this.split(";")[0].split("/")[1] else null
}

fun ByteArray.guessMimeType(): String
{
    val stream = BufferedInputStream(ByteArrayInputStream(this))
    return URLConnection.guessContentTypeFromStream(stream)
}

/**
 * Get size of file in base64 string as bytes
 * @param this String with file
 * @return Long
 * @throws none
 **/
fun String.base64FileSize(): Long
{
    return (this.length.toLong() * 3)/4 - (Regex("=").findAll(this).count() - 2)
}

