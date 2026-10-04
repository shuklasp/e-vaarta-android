package net.thunderbird.core.common.evaarta

enum class EvaartaRuntimeCapability {
    PDF, PDF_PRODUCTION, RESEARCH, SEARCH, DOCUMENTS, KNOWLEDGE, CITATIONS,
    AI, PROJECTS, COLLABORATION, MOBILE, ACCESSIBILITY, SECURITY,
    INTEROPERABILITY, AUTOMATION
}

data class EvaartaRuntimeAdapter(
    val capability: EvaartaRuntimeCapability,
    val operations: Set<String>
) {
    fun supports(operation: String): Boolean = operation in operations
    fun require(operation: String) {
        check(supports(operation)) { "Runtime operation not bound: $capability/$operation" }
    }
}

object EvaartaProductionRuntime {
    val tier1 = listOf(
        EvaartaRuntimeCapability.PDF, EvaartaRuntimeCapability.PDF_PRODUCTION,
        EvaartaRuntimeCapability.AI, EvaartaRuntimeCapability.COLLABORATION,
        EvaartaRuntimeCapability.MOBILE, EvaartaRuntimeCapability.SECURITY
    )
    val tier2 = listOf(
        EvaartaRuntimeCapability.RESEARCH, EvaartaRuntimeCapability.SEARCH,
        EvaartaRuntimeCapability.DOCUMENTS, EvaartaRuntimeCapability.KNOWLEDGE,
        EvaartaRuntimeCapability.CITATIONS, EvaartaRuntimeCapability.PROJECTS,
        EvaartaRuntimeCapability.ACCESSIBILITY, EvaartaRuntimeCapability.INTEROPERABILITY,
        EvaartaRuntimeCapability.AUTOMATION
    )
    val tier3 = listOf(
        "evidence-graph", "provenance", "grounded-ai", "evidence-to-decision",
        "decision-to-task", "evidence-to-project", "report-traceability",
        "communication-traceability", "end-to-end-traceability"
    )
}
