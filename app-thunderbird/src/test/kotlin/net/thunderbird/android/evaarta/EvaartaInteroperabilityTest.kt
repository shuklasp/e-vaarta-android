package net.thunderbird.android.evaarta

import org.junit.Assert.assertEquals
import org.junit.Test

class EvaartaInteroperabilityTest {
 @Test fun attachmentResume() {
  assertEquals(listOf(1,3), EvaartaAttachmentAck("a",setOf(0,2)).missing(4))
 }
}
