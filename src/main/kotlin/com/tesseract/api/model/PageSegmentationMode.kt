package com.tesseract.api.model

enum class PageSegmentationMode(id: Int)
{
    ORIENTATION_AND_SCRIPT_DETECTION(0),        // 0 Orientation and script detection (OSD) only.
    AUTOMATIC_PAGE_SEG_OSD(1),                  // 1 Automatic page segmentation with OSD.
    AUTOMATIC_PAGE_SEG(2),                      // 2 Automatic page segmentation, but no OSD, or OCR.
    FULLY_AUTOMATIC_PAGE_SEG(3),                // 3 Fully automatic page segmentation, but no OSD. (Default)
    ASSUME_SINGLE_COLUMN_MULTIPLE_SIZES(4),     // 4 Assume a single column of text of variable sizes.
    ASSUME_SINGLE_UNIFORM_BLOCK_VERTICAL(5),    // 5 Assume a single uniform block of vertically aligned text.
    ASSUME_SINGLE_UNIFORM_BLOCK(6),             // 6 Assume a single uniform block of text.
    TREAT_AS_SINGLE_TEXT_LINE(7),               // 7 Treat the image as a single text line.
    TREAT_AS_SINGLE_WORD(8),                    // 8 Treat the image as a single word.
    TREAT_AS_SINGLE_WORD_IN_CIRCLE(9),          // 9 Treat the image as a single word in a circle.
    TREAT_AS_SINGLE_CHAR(10),                   // 10 Treat the image as a single character.
    SPARSE_TEXT(11),                            // 11 Sparse text. Find as much text as possible in no particular order.
    SPARSE_TEXT_OSD(12),                        // 12 Sparse text with OSD.
    RAW_LINE(13)                                // 13 Raw line. Treat the image as a single text line, bypassing hacks that are Tesseract-specific.
}