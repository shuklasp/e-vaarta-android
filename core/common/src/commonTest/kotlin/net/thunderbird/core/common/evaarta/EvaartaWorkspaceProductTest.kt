package net.thunderbird.core.common.evaarta
import kotlin.test.Test
import kotlin.test.assertEquals
import kotlin.test.assertTrue
class EvaartaWorkspaceProductTest {
 @Test fun navigation(){assertEquals("project",EvaartaWorkspaceProduct.navigate(EvaartaWorkspaceShell(),"project").route)}
 @Test fun identity(){assertTrue(EvaartaWorkspaceProduct.identityMatch(EvaartaIdentity("a","p","email","A@x"),EvaartaIdentity("b","q","email","a@x")))}
 @Test fun dedupe(){assertEquals(1,EvaartaWorkspaceProduct.dedupeAttachments(listOf(EvaartaAttachment("1","h","a","text/plain",1),EvaartaAttachment("2","h","b","text/plain",1))).size)}
}