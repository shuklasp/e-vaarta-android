package net.thunderbird.android.evaarta

data class EvaartaWorkspaceSurfaceState(val selectedDocumentId:String?=null,val selectedItemId:String?=null,val query:String="",val offlineReady:Boolean=true)
data class EvaartaReaderSurfaceState(val documentId:String?,val mode:String="read",val canAnnotate:Boolean=true)
data class EvaartaEvidenceSurfaceState(val documentId:String?,val selectedGroupId:String?=null)
data class EvaartaAnnotationDraft(val documentId:String,val text:String,val start:Int,val end:Int)
data class EvaartaCollectionSurfaceState(val selectedCollectionId:String?=null)
data class EvaartaSearchHit(val type:String,val id:String,val title:String)
data class EvaartaOfflineSurfaceState(val pending:Int,val status:String="offline-ready")
data class EvaartaTransferSurfaceState(val workspaceId:String?,val format:String="evaarta-workspace-json")
data class EvaartaSyncSurfaceState(val status:String="local-only",val networkEnabled:Boolean=false)
data class EvaartaAccessibilitySurface(val landmarks:List<String>=listOf("navigation","main","complementary"),val focusSearch:String="Ctrl+K")
