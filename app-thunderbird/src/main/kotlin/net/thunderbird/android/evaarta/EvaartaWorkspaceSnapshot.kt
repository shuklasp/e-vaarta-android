package net.thunderbird.android.evaarta
data class EvaartaWorkspaceSnapshot(val workspaceId:String,val revision:Long,val name:String,val documentCount:Int,val itemCount:Int,val evidenceGroupCount:Int,val updatedAt:String)
fun DocumentWorkspace.snapshot(revision:Long=0L)=EvaartaWorkspaceSnapshot(id,revision,name,documents.size,items.size,evidenceGroups.size,updatedAt)
