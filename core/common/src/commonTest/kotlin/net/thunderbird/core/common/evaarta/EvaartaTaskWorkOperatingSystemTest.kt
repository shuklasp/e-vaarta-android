package net.thunderbird.core.common.evaarta
import kotlin.test.Test
import kotlin.test.assertEquals
import kotlin.test.assertTrue
class EvaartaTaskWorkOperatingSystemTest{
 @Test fun phasesAtoE(){val t=EvaartaWorkTask("t1","Report",projectId="p1",skills=listOf("research"),estimateMinutes=120);val a=EvaartaTaskWorkOperatingSystem.assign(t,EvaartaCandidate("u1",setOf("research"),480,120,true,setOf("p1")));assertEquals("offered",a.status);assertEquals("u1",EvaartaTaskWorkOperatingSystem.recommend(a,listOf(EvaartaCandidate("u1",setOf("research"),480,120,true,setOf("p1")))).first().first);val h=EvaartaTaskWorkOperatingSystem.monitor(a);assertEquals("unaccepted",h.health);val t2=EvaartaWorkTask("t2","Review",dependsOn=listOf("t1"));assertTrue(EvaartaTaskWorkOperatingSystem.validateGraph(listOf(a,t2)));assertEquals(setOf("t2"),EvaartaTaskWorkOperatingSystem.impact(listOf(a,t2),"t1"));assertEquals("at-risk",EvaartaTaskWorkOperatingSystem.projectHealth(listOf(t2)));assertEquals("verified",EvaartaTaskWorkOperatingSystem.verify(t.copy(evidenceIds=listOf("e1"))).status)}}
