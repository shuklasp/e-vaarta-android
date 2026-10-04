package net.thunderbird.core.common.evaarta
import kotlin.test.Test
import kotlin.test.assertEquals
import kotlin.test.assertTrue
class EvaartaCommunicationSuiteTest{
 @Test fun identitySearchAndInbox(){val p=EvaartaPerson("p","A",listOf("a@x"),listOf("1"));val c=EvaartaConversation("c","C",listOf("p"),"email");val m=EvaartaMessage("m","c","p","Please complete task by Friday","email",timestamp="2026-10-05");assertEquals(listOf(p),EvaartaCommunicationSuite.identityMatch(listOf(p),listOf("a@x"),emptyList()));assertEquals(listOf(m),EvaartaCommunicationSuite.search(listOf(m),"task"));assertEquals(listOf(c),EvaartaCommunicationSuite.unifiedInbox(listOf(c),listOf(m)))}
 @Test fun offlineReconcile(){val e1=EvaartaCommunicationEvent("1","k","m");val e2=EvaartaCommunicationEvent("2","k","m");val e3=EvaartaCommunicationEvent("3","z","m",1,2);val r=EvaartaCommunicationSuite.reconcile(listOf(e1,e2,e3));assertEquals(1,r.first.size);assertEquals(1,r.second.size)}
 @Test fun contract(){assertTrue(EvaartaCommunicationSuiteContract.phases.count()==7);assertTrue(EvaartaCommunicationSuiteContract.capabilities.contains("communication-to-task"))}
}
