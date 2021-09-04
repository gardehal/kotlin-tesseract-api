package com.tesseract.api.intercept

import org.springframework.beans.factory.annotation.Autowired
import org.springframework.core.annotation.Order
import org.springframework.stereotype.Component
import java.io.File
import io.github.cdimascio.dotenv.dotenv

@Component
@Order(1)
class DotEnvProperties
{
//    @Autowired
//    lateinit var appProperties: AppProperties
//
//    private val envFile = File(appProperties.envPath)
//    private val dotEnv = if(envFile.exists()) dotenv() else null
//
//    val isTest: String = if(dotEnv != null) dotEnv["IS_TEST"]!! else System.getenv("IS_TEST")
//    val apiKeys: String = if(dotEnv != null) dotEnv["API_KEYS"]!! else System.getenv("API_KEYS")
//    val tempDir: String = if(dotEnv != null) dotEnv["TEMP_DIR"]!! else System.getenv("TEMP_DIR")
//    val maxFileSizeBytes: String = if(dotEnv != null) dotEnv["MAX_FILE_SIZE_BYTES"]!! else System.getenv("MAX_FILE_SIZE_BYTES")
//    val installedLanguages: String = if(dotEnv != null) dotEnv["INSTALLED_LANGUAGES"]!! else System.getenv("INSTALLED_LANGUAGES")
//    val installFullPath: String = if(dotEnv != null) dotEnv["INSTALLED_FULL_PATH"]!! else System.getenv("INSTALLED_FULL_PATH")
}