package com.tesseract.api.intercept

import io.github.cdimascio.dotenv.Dotenv
import io.github.cdimascio.dotenv.dotenv
import org.springframework.beans.factory.annotation.Value
import org.springframework.core.annotation.Order
import org.springframework.stereotype.Component
import java.io.File

@Component
@Order(0)
class AppProperties
{
    @Value("\${project.basePackage}")
    var projectBasePackage: String? = null
    @Value("\${project.creator}")
    var projectCreator: String? = null
    @Value("\${project.url}")
    var projectUrl: String? = null
    @Value("\${project.email}")
    var projectEmail: String? = null
    @Value("\${project.title}")
    var projectTitle: String? = null
    @Value("\${project.version}")
    var projectVersion: String? = null
    @Value("\${project.isTestProject}")
    var projectIsTestProject: String? = null

    @Value("\${server.host}")
    var host: String? = null
    @Value("\${server.port}")
    var port: Int? = null
    @Value("\${server.resourceDir}")
    var resourceDir: String? = null
    @Value("\${server.envPath}")
    var envPath: String? = null

    @Value("\${server.tokenHeaderName}")
    var tokenHeaderName: String? = null
//    @Value("\${server.apiKeys}")
//    var apiKeys: String? = null
//    @Value("\${server.tempDir}")
//    var tempDir: String? = null
//    @Value("\${server.maxFileSizeBytes}")
//    var maxFileSizeBytes: Long? = null

//    @Value("\${tesseract.installedLanguages}")
//    var installedLanguages: String? = null
    @Value("\${tesseract.languagesFile}")
    var languagesFile: String? = null
//    @Value("\${tesseract.installFullPath}")
//    var installFullPath: String? = null
    @Value("\${tesseract.userDefinedDpi}")
    var userDefinedDpi: Int? = null

    // .env, which enables Heroku to set the save values when the .env file is not present (i.e. don't push .env to Git)
    private val envFile = File("src\\\\main\\\\resources\\\\.env")
    private var dotEnv = if(envFile.exists()) dotenv() else null

    val isProduction: String = if(dotEnv != null) dotEnv!!["IS_PRODUCTION"]!! else System.getenv("IS_PRODUCTION")
    val apiKeys: String = if(dotEnv != null) dotEnv!!["API_KEYS"]!! else System.getenv("API_KEYS")
    val tempDir: String = if(dotEnv != null) dotEnv!!["TEMP_DIR"]!! else System.getenv("TEMP_DIR")
    val maxFileSizeBytes: Long = if(dotEnv != null) dotEnv!!["MAX_FILE_SIZE_BYTES"]!!.toLong() else System.getenv("MAX_FILE_SIZE_BYTES").toLong()
    val installedLanguages: String = if(dotEnv != null) dotEnv!!["INSTALLED_LANGUAGES"]!! else System.getenv("INSTALLED_LANGUAGES")
    val installFullPath: String = if(dotEnv != null) dotEnv!!["INSTALLED_FULL_PATH"]!! else System.getenv("INSTALLED_FULL_PATH")
}