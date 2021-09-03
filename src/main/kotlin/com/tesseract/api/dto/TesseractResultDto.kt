package com.tesseract.api.dto

import com.tesseract.api.model.TesseractLanguage
import io.swagger.annotations.ApiModelProperty

class TesseractResultDto
(
        @ApiModelProperty("Raw output of content in image")
        var contentRaw: String? = null,

        @ApiModelProperty("Cleaned output of content in image")
        var contentCleaned: String? = null,

        @ApiModelProperty("Language in image from user (default eng/english)")
        var language: TesseractLanguage? = null

)