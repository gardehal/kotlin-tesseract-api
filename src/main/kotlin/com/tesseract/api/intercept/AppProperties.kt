package com.tesseract.api.intercept

import io.github.cdimascio.dotenv.dotenv
import org.springframework.boot.context.properties.ConfigurationProperties
import org.springframework.context.annotation.Configuration
import java.io.File

@Configuration
@ConfigurationProperties(prefix = "app")
class AppProperties
{
    lateinit var basePackage: String
    lateinit var creator: String
    lateinit var url: String
    lateinit var email: String
    lateinit var title: String
    lateinit var desc: String
    lateinit var version: String

    lateinit var host: String
    lateinit var port: String
    lateinit var resourceDir: String
    lateinit var envPath: String
    lateinit var tokenHeaderName: String
    lateinit var languagesFile: String

    // .env, which enables Heroku to set the save values when the .env file is not present (i.e. don't push .env to Git)
    private val envFile = File("""src\\main\\resources\\.env""")
    private var dotEnv = if(envFile.exists()) dotenv() else null

    val isProduction: Boolean = if(dotEnv != null) dotEnv!!["IS_PRODUCTION"]!!.toBoolean() else System.getenv("IS_PRODUCTION")!!.toBoolean()
    val logDebug: Boolean = if(dotEnv != null) dotEnv!!["LOG_DEBUG"]!!.toBoolean() else System.getenv("LOG_DEBUG")!!.toBoolean()
    val apiKeys: String = if(dotEnv != null) dotEnv!!["API_KEYS"]!! else System.getenv("API_KEYS")
    val tempDir: String = if(dotEnv != null) dotEnv!!["TEMP_DIR"]!! else System.getenv("TEMP_DIR")
    val maxFileSizeBytes: Long = if(dotEnv != null) dotEnv!!["MAX_FILE_SIZE_BYTES"]!!.toLong() else System.getenv("MAX_FILE_SIZE_BYTES").toLong()
    val installedLanguages: String = if(dotEnv != null) dotEnv!!["INSTALLED_LANGUAGES"]!! else System.getenv("INSTALLED_LANGUAGES")
    val installFullPath: String = if(dotEnv != null) dotEnv!!["INSTALLED_FULL_PATH"]!! else System.getenv("INSTALLED_FULL_PATH")
}