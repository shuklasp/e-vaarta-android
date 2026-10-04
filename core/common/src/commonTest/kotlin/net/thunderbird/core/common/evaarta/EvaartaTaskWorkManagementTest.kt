package net.thunderbird.core.common.evaarta
import kotlin.test.*
class EvaartaTaskWorkManagementTest {
 @Test fun lifecycleAndGraph(){val a=EvaartaTask("a","A",estimateMinutes=60);val b=EvaartaTask("b","B",dependsOn=listOf("a"));assertTrue(EvaartaTaskWorkManagement.validateGraph(listOf(a,b)));assertEquals("accepted",EvaartaTaskWorkManagement.accept(EvaartaTaskWorkManagement.assign(a,EvaartaAssignment(assigneeId="u1")) ,true).status)}
 @Test fun monitoringAndCapacity(){val a=EvaartaTask("a","A",assigneeId="u1",remainingMinutes=90);val h=EvaartaTaskWorkManagement.monitor(a,nowEpochMs=100000,updatedEpochMs=0);assertEquals("stalled",h.health);assertTrue(EvaartaTaskWorkManagement.workload(listOf(a),mapOf("u1" to 60))["u1"]!!.overloaded)}
}
