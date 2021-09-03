package com.tesseract.api

import com.tesseract.api.intercept.AppProperties
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.boot.autoconfigure.EnableAutoConfiguration
import org.springframework.boot.autoconfigure.SpringBootApplication
import org.springframework.boot.runApplication
import org.springframework.context.annotation.Bean
import org.springframework.context.annotation.Configuration
import springfox.documentation.builders.ApiInfoBuilder
import springfox.documentation.builders.ParameterBuilder
import springfox.documentation.builders.PathSelectors
import springfox.documentation.builders.RequestHandlerSelectors
import springfox.documentation.schema.ModelRef
import springfox.documentation.service.ApiInfo
import springfox.documentation.service.Contact
import springfox.documentation.service.Parameter
import springfox.documentation.spi.DocumentationType
import springfox.documentation.spring.web.plugins.Docket
import springfox.documentation.swagger2.annotations.EnableSwagger2

@EnableAutoConfiguration
@SpringBootApplication
@Configuration
@EnableSwagger2
class TesseractApi
{
	@Autowired
	lateinit var appProperties: AppProperties
	//	http://localhost:8080/swagger-ui.html

	@Bean
	fun api(): Docket
	{
		println("Starting TesseractApi on ${appProperties.host}:${appProperties.port}")
		println("http://${appProperties.host}:${appProperties.port}/swagger-ui.html")

		return Docket(DocumentationType.SWAGGER_2)
			.apiInfo(apiInfo())
			.select()
			.apis(RequestHandlerSelectors.basePackage("com.tesseract.api"))
			.paths(PathSelectors.any())
			.build()
			.globalOperationParameters(operationParameters())
	}

	fun apiInfo(): ApiInfo
	{
		val grdAll = Contact("contactName", "projectUrl", "projectEmail")
		return ApiInfoBuilder().title("TesseractApi")
			.description("API for using Tesseract")
			.version("0.0.1")
			.contact(grdAll)
			.build()
	}

	private fun operationParameters(): List<Parameter>
	{
		val headers: MutableList<Parameter> = ArrayList()
		headers.add(ParameterBuilder()
			.name(appProperties.tokenHeaderName)
			.description("API key")
			.modelRef(ModelRef("string")).parameterType("header")
			.required(true).build()
		)

		return headers
	}
}

fun main(args: Array<String>)
{
	runApplication<TesseractApi>(*args)
}