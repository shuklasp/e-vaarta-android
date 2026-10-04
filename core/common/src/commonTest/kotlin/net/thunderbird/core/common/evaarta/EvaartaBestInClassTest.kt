package net.thunderbird.core.common.evaarta

import kotlin.test.Test
import kotlin.test.assertTrue
import kotlin.test.assertFalse

class EvaartaBestInClassTest {
    @Test fun pdfStateIsValidated() {
        assertTrue(EvaartaBestInClass.validPdfState(EvaartaPdfReaderState(pageCount=100,currentPage=50)))
        assertFalse(EvaartaBestInClass.validPdfState(EvaartaPdfReaderState(pageCount=10,currentPage=11)))
    }

    @Test fun evidenceRequiresStableAnchor() {
        val e = EvaartaPdfEvidence(EvaartaPdfAnchor("doc", page=2), "quote")
        assertTrue(EvaartaBestInClass.groundedEvidence(e))
    }
}
