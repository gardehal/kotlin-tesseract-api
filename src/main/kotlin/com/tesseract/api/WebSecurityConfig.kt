package com.tesseract.api;

import com.tesseract.api.intercept.*
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.context.annotation.Configuration
import org.springframework.core.annotation.Order
import org.springframework.security.authentication.BadCredentialsException
import org.springframework.security.config.annotation.web.builders.HttpSecurity
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter
import org.springframework.security.config.http.SessionCreationPolicy

@Configuration
@EnableWebSecurity
@Order(1)
class WebSecurityConfig: WebSecurityConfigurerAdapter()
{
    val log by injectLogger()

    @Autowired
    lateinit var appProperties: AppProperties

    override fun configure(http: HttpSecurity)
    {
        val filter = ApiKeyFilter(appProperties.tokenHeaderName)
        val keys = appProperties.apiKeys
        filter.setAuthenticationManager { authentication ->
            val userKey = authentication.principal.toString()
            if(userKey.length != 32 || userKey.contains(";") || !keys.contains(userKey))
            {
                if(appProperties.logDebug)
                    log.warning("Authentication failed with key '$userKey'.")

                throw BadCredentialsException("The API key was not found.")
            }

            if(appProperties.logDebug)
                log.info("Authentication ok with key '$userKey'.")
            authentication.isAuthenticated = true
            authentication
        }

        http
            .authorizeRequests()
                // Swagger UI
                .antMatchers("/swagger-ui.html").permitAll()
                .antMatchers("/swagger-resources/**").permitAll()
                .antMatchers("/webjars/**").permitAll()
                .antMatchers("/v2/api-docs").permitAll()
            // Require authentication (token in this case) on other endpoints
            .and()
                .addFilter(filter)
                .authorizeRequests()
                .anyRequest()
                .authenticated()
            .and()
                .sessionManagement()
                .sessionCreationPolicy(SessionCreationPolicy.STATELESS)
            .and()
                .csrf().disable() // Enabled by default
                .cors().disable() // Do not want to use CORS as it is an extra security risk
    }
}