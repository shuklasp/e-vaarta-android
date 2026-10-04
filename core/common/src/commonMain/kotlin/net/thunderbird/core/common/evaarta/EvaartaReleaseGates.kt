package net.thunderbird.core.common.evaarta

enum class EvaartaValidationDimension {
    UNIT, INTEGRATION, REAL_DATA, ADVERSARIAL, PERFORMANCE,
    ACCESSIBILITY, DEVICE_OFFLINE, INTEROPERABILITY, HUMAN_BENCHMARK
}

data class EvaartaValidationEvidence(
    val dimension: EvaartaValidationDimension,
    val status: String = "pending",
    val artifact: String? = null,
    val corpusId: String? = null,
    val version: String? = null,
    val hardware: String? = null
)

data class EvaartaReleaseRecord(
    val productClass: String,
    val capability: String,
    val implementationStatus: String = "implemented",
    val evidence: List<EvaartaValidationEvidence> = emptyList()
)

object EvaartaReleaseGates {
    fun evaluate(record: EvaartaReleaseRecord): String {
        if (record.implementationStatus != "integrated") return "integrated"
        val missing = EvaartaValidationDimension.entries.filter { dimension ->
            record.evidence.none { it.dimension == dimension && it.status == "passed" }
        }
        return if (missing.isEmpty()) "validated" else "integrated"
    }
}

data class EvaartaBenchmarkCase(
    val id: String,
    val domain: String,
    val task: String,
    val corpusId: String
)

data class EvaartaBenchmarkResult(
    val caseId: String,
    val product: String,
    val version: String,
    val elapsedMs: Long,
    val completed: Boolean,
    val errors: List<String> = emptyList()
)

data class EvaartaEvidenceActionStage(
    val stage: String,
    val id: String,
    val sourceIds: List<String>
)

object EvaartaEvidenceActionBenchmark {
    val stages = listOf(
        "communication", "document", "evidence", "claim", "finding",
        "decision", "task", "project", "report", "citation", "communication-output"
    )

    fun validate(trace: List<EvaartaEvidenceActionStage>): Boolean =
        stages.all { stage -> trace.any { it.stage == stage && it.id.isNotBlank() && it.sourceIds.isNotEmpty() } }
}
