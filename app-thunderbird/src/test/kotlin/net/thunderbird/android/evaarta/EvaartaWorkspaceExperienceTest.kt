package net.thunderbird.android.evaarta
import kotlin.test.Test
import kotlin.test.assertEquals
import kotlin.test.assertTrue
class EvaartaWorkspaceExperienceTest {
 @Test fun stateStoreTracksSelection(){val store=EvaartaWorkspaceStateStore();store.selectDocument("doc-1");store.setSearchQuery("policy");assertEquals("doc-1",store.state.selectedDocumentId);assertEquals("policy",store.state.searchQuery)}
 @Test fun deepLinkParses(){val link=android.net.Uri.parse("evaarta://workspace/ws-1?document=doc-1&item=item-1");val parsed=EvaartaDeepLinks.parse(link);assertEquals("ws-1",parsed?.workspaceId);assertEquals("doc-1",parsed?.documentId)}
}
