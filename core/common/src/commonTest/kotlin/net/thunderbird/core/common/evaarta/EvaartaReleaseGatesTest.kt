package net.thunderbird.core.common.evaarta

import kotlin.test.Test
import kotlin.test.assertEquals
import kotlin.test.assertTrue

class EvaartaReleaseGatesTest {
    @Test fun integratedRequiresAllEvidence() {
        val evidence = EvaartaValidationDimension.entries.map {
            EvaartaValidationEvidence(it, "passed")
        }
        val record = EvaartaReleaseRecord("pdf", "rendering", "integrated", evidence)
        assertEquals("validated", EvaartaReleaseGates.evaluate(record))
    }

    @Test fun evidenceActionRequiresCompleteTrace() {
        val trace = EvaartaEvidenceActionBenchmark.stages.mapIndexed {
            i, stage -> EvaartaEvidenceActionStage(stage, "n$i", listOf("s$i"))
        }
        assertTrue(EvaartaEvidenceActionBenchmark.validate(trace))
    }
}
