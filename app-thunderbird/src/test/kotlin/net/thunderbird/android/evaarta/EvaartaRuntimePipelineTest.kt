package net.thunderbird.android.evaarta

import org.junit.Assert.assertEquals
import org.junit.Test

class EvaartaRuntimePipelineTest {
 @Test fun attachmentResumeFindsOnlyMissingChunks() {
  assertEquals(listOf(1,3), EvaartaAttachmentResume.missing(4,setOf(0,2)))
 }
}
