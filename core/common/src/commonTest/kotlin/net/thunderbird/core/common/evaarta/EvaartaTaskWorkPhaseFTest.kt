package net.thunderbird.core.common.evaarta
import kotlin.test.Test
import kotlin.test.assertEquals
import kotlin.test.assertTrue
class EvaartaTaskWorkPhaseFTest{ @Test fun graphAndImpact(){val a=EvaartaPhaseFTask("a","A");val b=EvaartaPhaseFTask("b","B",dependsOn=listOf("a"));assertTrue(EvaartaTaskWorkPhaseF.validateGraph(listOf(a,b)));assertEquals(setOf("b"),EvaartaTaskWorkPhaseF.impact(listOf(a,b),"a"))} @Test fun scoringAndEvidence(){val t=EvaartaPhaseFTask("t","T",skills=setOf("pdf"));assertTrue(EvaartaTaskWorkPhaseF.score(t,"p",setOf("pdf"),480,0).score>0.9);assertEquals("verified",EvaartaTaskWorkPhaseF.verify(t.copy(evidenceIds=listOf("e"))).status)}}
