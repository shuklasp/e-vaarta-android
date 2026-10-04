package net.thunderbird.core.common.evaarta
import kotlin.test.Test
import kotlin.test.assertFalse
import kotlin.test.assertTrue
class EvaartaKnowledgeTest {
 @Test fun groundedAnswerRequiresExistingEvidence(){val a=EvaartaGroundedAnswer("x",listOf(EvaartaClaim("c","x",listOf("e"))),listOf(EvaartaEvidenceRef("e")));assertTrue(a.grounded)}
 @Test fun taskGraphRejectsCycles(){val tasks=listOf(EvaartaTask("a","A",listOf("b")),EvaartaTask("b","B",listOf("a")));assertFalse(EvaartaTaskGraph.isValid(tasks))}
}
