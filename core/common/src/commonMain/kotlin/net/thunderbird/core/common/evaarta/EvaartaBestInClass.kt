package net.thunderbird.core.common.evaarta

enum class EvaartaPdfViewMode { PAGE, CONTINUOUS, TWO_PAGE, TWO_PAGE_CONTINUOUS, FIT_WIDTH, FIT_PAGE, READING, PRESENTATION }
enum class EvaartaReadingTheme { LIGHT, DARK, SEPIA, HIGH_CONTRAST, SYSTEM }

data class EvaartaPdfReaderState(
    val documentId: String? = null,
    val pageCount: Int = 0,
    val currentPage: Int = 1,
    val viewMode: EvaartaPdfViewMode = EvaartaPdfViewMode.CONTINUOUS,
    val zoom: Double = 1.0,
    val rotation: Int = 0,
    val theme: EvaartaReadingTheme = EvaartaReadingTheme.SYSTEM,
    val readingOffset: Double = 0.0,
)

data class EvaartaPdfAnchor(
    val documentId: String,
    val revisionId: String? = null,
    val page: Int,
    val pageLabel: String? = null,
    val text: String = "",
    val textStart: Int? = null,
    val textEnd: Int? = null,
    val structurePath: List<String> = emptyList(),
)

data class EvaartaPdfEvidence(
    val anchor: EvaartaPdfAnchor,
    val quote: String,
    val context: String = "",
    val confidence: Double = 1.0,
    val extractionMethod: String = "native",
)

data class EvaartaKnowledgeBlock(
    val id: String,
    val type: String = "paragraph",
    val text: String = "",
    val properties: Map<String, String> = emptyMap(),
)

data class EvaartaAutomation(
    val id: String,
    val trigger: String,
    val conditions: List<String> = emptyList(),
    val action: String,
    val requiredCapabilities: List<String> = emptyList(),
)

data class EvaartaAccessibilityProfile(
    val screenReader: Boolean = true,
    val keyboard: Boolean = true,
    val reflow: Boolean = true,
    val highContrast: Boolean = false,
    val textScale: Double = 1.0,
    val reducedMotion: Boolean = false,
)

object EvaartaBestInClass {
    fun validPdfState(state: EvaartaPdfReaderState): Boolean =
        state.pageCount >= 0 &&
        state.currentPage >= 1 &&
        (state.pageCount == 0 || state.currentPage <= state.pageCount) &&
        state.zoom in 0.25..8.0 &&
        state.rotation in 0..359

    fun groundedEvidence(evidence: EvaartaPdfEvidence): Boolean =
        evidence.anchor.documentId.isNotBlank() &&
        evidence.anchor.page > 0 &&
        evidence.confidence in 0.0..1.0
}
