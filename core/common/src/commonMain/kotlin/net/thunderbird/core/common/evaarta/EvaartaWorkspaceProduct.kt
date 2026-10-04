package net.thunderbird.core.common.evaarta

data class EvaartaWorkspaceShell(val route:String="inbox",val selection:String?=null,val offline:Boolean=true)
data class EvaartaIdentity(val id:String,val personId:String,val type:String,val value:String,val verified:Boolean=false)
data class EvaartaAttachment(val id:String,val hash:String,val name:String,val mime:String,val size:Long)
data class EvaartaSyncEvent(val id:String,val entityId:String,val type:String,val version:Long,val deviceId:String)
data class EvaartaAiRequest(val modelId:String,val input:String,val sourceIds:List<String>,val permissions:Set<String>)
data class EvaartaReleaseManifest(val version:String,val commit:String,val platforms:List<String>,val artifacts:List<String>,val validation:List<String>)
object EvaartaWorkspaceProduct {
    val routes=listOf("inbox","conversation","person","document","evidence","meeting","decision","task","project","report","citation")
    val semanticFlow=listOf("communication","document","evidence","claim","finding","decision","task","project","report","citation","communication")
    fun navigate(shell:EvaartaWorkspaceShell,route:String,selection:String?=null)=require(route in routes).let{shell.copy(route=route,selection=selection)}
    fun identityMatch(a:EvaartaIdentity,b:EvaartaIdentity)=a.type.equals(b.type,true)&&a.value.equals(b.value,true)
    fun dedupeAttachments(items:List<EvaartaAttachment>)=items.distinctBy{it.hash}
    fun reconcile(local:List<EvaartaSyncEvent>,remote:List<EvaartaSyncEvent>):Pair<List<EvaartaSyncEvent>,List<String>> {
        val byId=linkedMapOf<String,EvaartaSyncEvent>(); local.forEach{byId[it.id]=it}; val conflicts=mutableListOf<String>()
        remote.forEach{e->val old=byId[e.id]; if(old!=null&&old!=e)conflicts+=e.id else byId[e.id]=e}; return byId.values.toList() to conflicts
    }
    fun aiGrounded(request:EvaartaAiRequest,citations:Set<String>)=request.sourceIds.isEmpty()||request.sourceIds.any{it in citations}
    fun releaseReady(required:Set<String>,evidence:Set<String>)=required.all{it in evidence}
}