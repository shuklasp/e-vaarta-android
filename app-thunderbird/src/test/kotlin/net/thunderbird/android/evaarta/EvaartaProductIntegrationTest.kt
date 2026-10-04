package net.thunderbird.android.evaarta
import kotlin.test.Test
import kotlin.test.assertEquals
class EvaartaProductIntegrationTest {
 @Test fun timelineAndSelectionAreDeterministic(){val ws=DocumentWorkspace("ws","x","",listOf(),listOf(),listOf(),listOf(),"2026-01-01T00:00:00Z");assertEquals(0,ws.evidenceTimeline("missing").size)}
 @Test fun queueRetries(){val q=EvaartaOfflineQueue();q.enqueue("save","x");q.retryHead("offline");assertEquals(1,q.snapshot().first().attempts)}
}