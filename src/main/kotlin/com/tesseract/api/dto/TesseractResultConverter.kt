package com.tesseract.api.dto

import com.tesseract.api.model.TesseractResult

class TesseractResultConverter
{
        companion object
        {
                fun transform(entity: TesseractResult): TesseractResultDto
                {
                        return TesseractResultDto(
                                contentRaw = entity.contentRaw,
                                contentCleaned = entity.contentCleaned,
                                language = entity.language
                        )
                }

                fun transform(dto: TesseractResultDto): TesseractResult
                {
                        return TesseractResult(
                                contentRaw = dto.contentRaw,
                                contentCleaned = dto.contentCleaned,
                                language = dto.language
                        )
                }

                fun transform(entities: Iterable<TesseractResult>): List<TesseractResultDto> {
                        return entities.map { transform(it) }
                }
        }
}