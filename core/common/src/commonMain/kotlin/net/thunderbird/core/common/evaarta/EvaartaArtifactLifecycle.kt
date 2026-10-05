package net.thunderbird.core.common.evaarta

data class EvaartaArtifact(val id:String,val type:String,val name:String="",val mime:String="application/octet-stream",val contentHash:String?=null,val sourceId:String?=null,val security:String="normal",val state:String="active")
data class EvaartaArtifactRevision(val id:String,val artifactId:String,val contentHash:String,val sourceId:String?=null)
data class EvaartaArtifactLink(val id:String,val artifactId:String,val targetType:String,val targetId:String,val type:String="attached",val revisionId:String?=null)
data class EvaartaArtifactShare(val artifactId:String,val recipientType:String,val decision:String,val reason:String)

object EvaartaArtifactLifecycle {
    const val LINK_ATTACHED="attached"
    const val SHARE_ALLOW="allow"; const val SHARE_WARN="warn"; const val SHARE_BLOCK="block"; const val SHARE_APPROVAL_REQUIRED="approval-required"
    fun link(artifact:EvaartaArtifact,targetType:String,targetId:String,linkId:String,revisionId:String?=null)=EvaartaArtifactLink(linkId,artifact.id,targetType,targetId,LINK_ATTACHED,revisionId)
    fun forwardTaskArtifacts(links:List<EvaartaArtifactLink>,fromTaskId:String,toTaskId:String,artifactIds:Set<String>?=null)=
        links.filter { it.targetType=="task" && it.targetId==fromTaskId && (artifactIds==null || it.artifactId in artifactIds) }
            .map { it.copy(id="forward-"+it.artifactId+"-"+toTaskId,targetId=toTaskId) }.distinctBy { it.artifactId }
    fun shareDecision(artifact:EvaartaArtifact,recipientType:String,allowRestrictedExternal:Boolean=false):EvaartaArtifactShare {
        if(artifact.state=="blocked" || artifact.state=="quarantined") return EvaartaArtifactShare(artifact.id,recipientType,SHARE_BLOCK,"artifact-not-shareable")
        if(artifact.security=="restricted" && recipientType=="external") return EvaartaArtifactShare(artifact.id,recipientType,if(allowRestrictedExternal) SHARE_APPROVAL_REQUIRED else SHARE_BLOCK,"restricted-external")
        if(artifact.security=="confidential" && recipientType=="external") return EvaartaArtifactShare(artifact.id,recipientType,SHARE_WARN,"confidential-external")
        return EvaartaArtifactShare(artifact.id,recipientType,SHARE_ALLOW,"policy-passed")
    }
    fun contract()=listOf("stable-artifact-identity","content-hash","reusable-references","task-propagation","communication-sharing","evidence-linking","revision-aware","provenance","security-policy","offline-first")
}
