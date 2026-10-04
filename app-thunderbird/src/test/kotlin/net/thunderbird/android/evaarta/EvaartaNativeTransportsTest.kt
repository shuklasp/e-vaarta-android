package net.thunderbird.android.evaarta

import org.junit.Assert.assertArrayEquals
import org.junit.Assert.assertEquals
import org.junit.Test

class EvaartaNativeTransportsTest {
    @Test fun attachmentRoundTrip() {
        val source = ByteArray(700000) { (it % 251).toByte() }
        val chunks = EvaartaAttachmentTransfer.chunk("a1", source)
        assertEquals(3, chunks.size)
        assertArrayEquals(source, EvaartaAttachmentTransfer.assemble(chunks))
    }
}
