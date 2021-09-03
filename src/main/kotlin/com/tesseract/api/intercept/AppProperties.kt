package com.tesseract.api.intercept

import org.springframework.beans.factory.annotation.Value
import org.springframework.core.annotation.Order
import org.springframework.stereotype.Component

@Component
@Order(0)
class AppProperties
{
    @Value("\${server.host}")
    var host: String? = null
    @Value("\${server.port}")
    var port: Int? = null

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