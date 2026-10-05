package net.thunderbird.core.common.evaarta
import kotlin.test.Test
import kotlin.test.assertEquals
class EvaartaArtifactVaultSyncWorkspaceTest {
 @Test fun vaultDedupesByHash(){val v=EvaartaArtifactVault.put(EvaartaVault(),EvaartaVaultObject("ABC",1));assertEquals(1,v.objects.size)}
 @Test fun transferIsResumable(){var t=EvaartaTransfer("t","h",2);t=EvaartaArtifactSync.accept(t,1);assertEquals(listOf(0),EvaartaArtifactSync.missing(t));t=EvaartaArtifactSync.accept(t,0);assertEquals("complete",t.state)}
 @Test fun workspaceFilters(){val w=EvaartaArtifactWorkspace.filter(EvaartaArtifactWorkspace(),"type","document");assertEquals(1,EvaartaArtifactWorkspace.matches(w,listOf(EvaartaArtifact("a","document","A"),EvaartaArtifact("b","photo","B"))).size)}
}