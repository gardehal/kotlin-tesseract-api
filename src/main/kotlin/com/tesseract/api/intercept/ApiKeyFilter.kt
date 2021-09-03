package com.tesseract.api.intercept

import org.springframework.security.web.authentication.preauth.AbstractPreAuthenticatedProcessingFilter
import javax.servlet.http.HttpServletRequest

class ApiKeyFilter(private val principalRequestHeader: String): AbstractPreAuthenticatedProcessingFilter()
{
    override fun getPreAuthenticatedPrincipal(request: HttpServletRequest): Any?
    {
        return request.getHeader(principalRequestHeader)
    }

    override fun getPreAuthenticatedCredentials(request: HttpServletRequest): Any
    {
        return "N/A"
    }
}