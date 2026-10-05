package net.thunderbird.core.common.evaarta

import kotlin.test.Test
import kotlin.test.assertEquals
import kotlin.test.assertTrue

class EvaartaArtifactLifecycleTest {
    @Test fun forwardsArtifactsBetweenTasks() {
        val a=EvaartaArtifact("a","photo",contentHash="sha")
        val link=EvaartaArtifactLifecycle.link(a,"task","one","l1")
        val forwarded=EvaartaArtifactLifecycle.forwardTaskArtifacts(listOf(link),"one","two")
        assertEquals("two",forwarded.single().targetId)
        assertEquals("a",forwarded.single().artifactId)
    }
    @Test fun blocksRestrictedExternalSharing() {
        val a=EvaartaArtifact("a","document",security="restricted")
        assertEquals(EvaartaArtifactLifecycle.SHARE_BLOCK,EvaartaArtifactLifecycle.shareDecision(a,"external").decision)
    }
    @Test fun warnsConfidentialExternalSharing() {
        val a=EvaartaArtifact("a","photo",security="confidential")
        assertEquals(EvaartaArtifactLifecycle.SHARE_WARN,EvaartaArtifactLifecycle.shareDecision(a,"external").decision)
    }
    @Test fun contractIsOfflineFirst() { assertTrue("offline-first" in EvaartaArtifactLifecycle.contract()) }
}
