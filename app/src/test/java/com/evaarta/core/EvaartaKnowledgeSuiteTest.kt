package com.evaarta.core
import org.junit.Assert.assertEquals
import org.junit.Test
class EvaartaKnowledgeSuiteTest {
 @Test fun evidenceAndLineage(){val a=EvaartaKnowledgeSuite.evidence("doc",EvaartaAnchor("doc",page=2,quote="solar"),"solar");val c=EvaartaEntity("claim",EvaartaEntityKind.CLAIM);val r=EvaartaKnowledgeSuite.relation(a.id,c.id,EvaartaRelation.SUPPORTS);assertEquals(listOf(a.id),EvaartaKnowledgeSuite.lineage(c.id,listOf(r)))}
 @Test fun projectStatus(){val a=EvaartaEntity("a",EvaartaEntityKind.TASK,mapOf("status" to "completed"));val b=EvaartaEntity("b",EvaartaEntityKind.TASK,mapOf("status" to "open"));assertEquals(mapOf("total" to 2,"completed" to 1,"open" to 1),EvaartaKnowledgeSuite.projectStatus(listOf(a,b)))}
}
