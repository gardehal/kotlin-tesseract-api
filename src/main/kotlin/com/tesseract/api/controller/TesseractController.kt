package com.tesseract.api.controller

import com.tesseract.api.dto.TesseractResultConverter
import com.tesseract.api.dto.TesseractResultDto
import com.tesseract.api.intercept.AppProperties
import com.tesseract.api.model.*
import com.tesseract.api.service.TesseactService
import com.tesseract.api.service.UtilService
import com.tesseract.api.service.base64FileSize
import com.tesseract.api.service.containsValidBase64
import io.swagger.annotations.Api
import io.swagger.annotations.ApiOperation
import io.swagger.annotations.ApiParam
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.core.annotation.AliasFor
import org.springframework.http.MediaType
import org.springframework.http.ResponseEntity
import org.springframework.web.bind.annotation.*

@Api(value = "/tesseract", description = "Using Tesseract to read text from images")
@RequestMapping("/tesseract")
@RestController
class TesseractController(tesseactService: TesseactService? = null, utilService: UtilService? = null)
{
    @Autowired
    lateinit var appProperties: AppProperties

    val tesseactService = tesseactService?: TesseactService()
    val utilService = utilService?: UtilService()

    @ApiOperation("Get languages supported in this API")
    @GetMapping(
        path = ["/ping", "/health"],
        produces = [(MediaType.APPLICATION_JSON_VALUE)])
    fun getHealth(): ResponseEntity<WrappedResponse<HealthStatus>>
    {
        return try
        {
            val health = utilService.getHealthStatus()

            return ResponseEntity.status(200).body(
                WrappedResponse(code = 200, data = health).validated())
        }
        catch (e: Exception)
        {
            println(" ---- TesseractController, getHealth error ---- ")
            e.printStackTrace()

            ResponseEntity.status(500).body(
                WrappedResponse<HealthStatus>(code = 500, message = "Internal error.").validated())
        }
    }

    @ApiOperation("Get languages supported in this API")
    @GetMapping(
        path = ["/languages"],
        produces = [(MediaType.APPLICATION_JSON_VALUE)])
    fun getLanguages(): ResponseEntity<WrappedResponse<List<TesseractLanguage>>>
    {
        return try
        {
            val list = tesseactService.getInstalledLanguages()

            return ResponseEntity.status(200).body(
                WrappedResponse(code = 200, data = list).validated())
        }
        catch (e: Exception)
        {
            println(" ---- TesseractController, getLanguages error ---- ")
            e.printStackTrace()

            ResponseEntity.status(500).body(
                WrappedResponse<List<TesseractLanguage>>(code = 500, message = "Internal error.").validated())
        }
    }

    @ApiOperation("Upload and scan an image for text using integers for parameters")
    @PostMapping(
        path = ["/scanImageBase64IntParams"],
        produces = [(MediaType.APPLICATION_JSON_VALUE)])
    fun scanImage(@ApiParam("Three letter language key")
                  @RequestParam(value = "languageKey", required = false, defaultValue = "eng")
                  languageKey: String,
                  @ApiParam("base64 encoded string from image")
                  @RequestBody
                  base64: String,
                  @ApiParam("Page segmentation of image")
                  @RequestParam(value = "pageSegmentationMode", required = false, defaultValue = "3")
                  pageSegmentationMode: Int?,
                  @ApiParam("Image scan engine mode")
                  @RequestParam(value = "engineMode", required = false, defaultValue = "3")
                  engineMode: Int?): ResponseEntity<WrappedResponse<TesseractResultDto>>
    {
        return scanImage(languageKey, base64, PageSegmentationMode.values()[pageSegmentationMode!!], OcrEngineMode.values()[engineMode!!])
    }

    @ApiOperation("Upload and scan an image for text")
    @PostMapping(
        path = ["/scanImageBase64"],
        produces = [(MediaType.APPLICATION_JSON_VALUE)])
    fun scanImage(@ApiParam("Three letter language key")
                  @RequestParam(value = "languageKey", required = false, defaultValue = "eng")
                  languageKey: String,
                  @ApiParam("base64 encoded string from image")
                  @RequestBody
                  base64: String,
                  @ApiParam("Page segmentation of image")
                  @RequestParam(value = "pageSegmentationMode", required = false, defaultValue = "FULLY_AUTOMATIC_PAGE_SEG")
                  pageSegmentationMode: PageSegmentationMode,
                  @ApiParam("Image scan engine mode")
                  @RequestParam(value = "engineMode", required = false, defaultValue = "DEFAULT")
                  engineMode: OcrEngineMode): ResponseEntity<WrappedResponse<TesseractResultDto>>
    {
        return try
        {
            if(!base64.containsValidBase64())
                return ResponseEntity.status(400).body(
                    WrappedResponse<TesseractResultDto>(code = 400, message = "base64 string was not readable as valid base64. Include metadata prefix or see https://en.wikipedia.org/wiki/Base64").validated())

            if(base64.toByteArray().size >= (appProperties.maxFileSizeBytes * 1.33) || base64.base64FileSize() >= appProperties.maxFileSizeBytes)
                return ResponseEntity.status(400).body(
                    WrappedResponse<TesseractResultDto>(code = 400, message = "base64 string (file) was too large, max ${appProperties.maxFileSizeBytes/1000} kb.").validated())

            val lang = tesseactService.getTesseractLanguage(languageKey)
                ?: return ResponseEntity.status(400).body(
                    WrappedResponse<TesseractResultDto>(code = 400, message = "languageKey \"$languageKey\" does not exist.").validated())

            if(!utilService.isLanguagePackInstalled(lang))
                return ResponseEntity.status(400).body(
                    WrappedResponse<TesseractResultDto>(code = 400, message = "The language pack for ${lang.name} is not installed, see /languages for a list.").validated())

            val res = tesseactService.processImage(
                base64 = base64, lang = lang,
                pageSeg = pageSegmentationMode,
                engineMode = engineMode)

            ResponseEntity.status(200).body(
                WrappedResponse(code = 200, data = TesseractResultConverter.transform(res)).validated())
        }
        catch (e: Exception)
        {
            println(" ---- TesseractController, scanImage error ---- ")
            e.printStackTrace()

            ResponseEntity.status(500).body(
                WrappedResponse<TesseractResultDto>(code = 500, message = "Internal error.").validated())
        }
    }
}