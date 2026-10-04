package net.thunderbird.core.common.evaarta

import kotlin.test.Test
import kotlin.test.assertContains
import kotlin.test.assertFailsWith
import kotlin.test.assertTrue

class EvaartaProductionRuntimeTest {
    @Test fun exposesAllTiers() {
        assertContains(EvaartaProductionRuntime.tier1, EvaartaRuntimeCapability.PDF)
        assertContains(EvaartaProductionRuntime.tier2, EvaartaRuntimeCapability.RESEARCH)
        assertContains(EvaartaProductionRuntime.tier3, "end-to-end-traceability")
    }

    @Test fun runtimeBoundaryRejectsUnboundOperations() {
        val adapter = EvaartaRuntimeAdapter(EvaartaRuntimeCapability.AI, setOf("embed"))
        assertTrue(adapter.supports("embed"))
        assertFailsWith<IllegalStateException> { adapter.require("chat") }
    }
}
