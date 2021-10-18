package com.tesseract.api.model

enum class OcrEngineMode
{
    LEGACY,                 // 0 Legacy engine only.
    NEURAL_NETS_LSTM,       // 1 Neural nets LSTM engine only.
    LEGACY_AND_LSTM,        // 2 Legacy + LSTM engines.
    DEFAULT                 // 3 Default, based on what is available.
}