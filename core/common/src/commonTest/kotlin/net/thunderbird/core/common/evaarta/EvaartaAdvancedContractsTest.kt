package net.thunderbird.core.common.evaarta

import kotlin.test.Test
import kotlin.test.assertTrue

class EvaartaAdvancedContractsTest {
    @Test fun contractsValidate() {
        assertTrue(EvaartaAdvancedContracts.validProduction(EvaartaPdfProductionRequest("redact")))
        assertTrue(EvaartaAdvancedContracts.validResearch(EvaartaResearchCard("c","d","p1")))
        assertTrue(EvaartaAdvancedContracts.validCitation(EvaartaScholarlyRecord("c")))
        assertTrue(EvaartaAdvancedContracts.validMarkdown(EvaartaMarkdownDocument("d","d.md")))
        assertTrue(EvaartaAdvancedContracts.validAiRequest(EvaartaGroundedAiRequest("q")))
        assertTrue(EvaartaAdvancedContracts.validEvent(EvaartaSemanticEvent("e","a",1,"d","create")))
        assertTrue(EvaartaAdvancedContracts.validCapture(EvaartaCapture("c",EvaartaCaptureType.SCAN,"file://scan")))
    }
}
