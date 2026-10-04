package net.thunderbird.android.evaarta
import kotlin.test.Test
import kotlin.test.assertEquals
import kotlin.test.assertTrue
class EvaartaDistributedArchitectureTest{@Test fun queueWorks(){val q=EvaartaStoreForwardQueue();q.enqueue(EvaartaEnvelope("m","a","b","task.created",null,"sig"));assertEquals(1,q.snapshot().size)}@Test fun transportManagerIsFailClosed(){val m=EvaartaTransportManager(emptyList());assertTrue(m.available().isEmpty())}}
