package com.tesseract.api.intercept

import org.springframework.beans.factory.annotation.Value
import org.springframework.core.annotation.Order
import org.springframework.stereotype.Component

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
    @Value("\${server.apiKeys}")
    var apiKeys: String? = null
    @Value("\${server.tempDir}")
    var tempDir: String? = null
    @Value("\${server.maxFileSizeBytes}")
    var maxFileSizeBytes: Long? = null

    @Value("\${tesseract.installedLanguages}")
    var installedLanguages: String? = null
    @Value("\${tesseract.languagesFile}")
    var languagesFile: String? = null
    @Value("\${tesseract.installFullPath}")
    var installFullPath: String? = null
    @Value("\${tesseract.userDefinedDpi}")
    var userDefinedDpi: Int? = null
}