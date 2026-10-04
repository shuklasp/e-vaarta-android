package net.thunderbird.core.common.evaarta

enum class EvaartaCaptureType { CAMERA, SCAN, VOICE, PHOTO }
enum class EvaartaAiLocality { LOCAL, REMOTE, HYBRID }

data class EvaartaPdfProductionRequest(val operation:String, val payload:Map<String,String> = emptyMap())
data class EvaartaResearchCard(val id:String,val sourceId:String,val anchor:String,val text:String="",val note:String="")
data class EvaartaScholarlyRecord(val id:String,val title:String="",val doi:String?=null,val authors:List<String> = emptyList(),val year:Int?=null)
data class EvaartaMarkdownDocument(val id:String,val path:String,val markdown:String="",val properties:Map<String,String> = emptyMap(),val revisionId:String?=null)
data class EvaartaAiProvider(val id:String,val locality:EvaartaAiLocality,val capabilities:List<String> = emptyList(),val model:String?=null)
data class EvaartaGroundedAiRequest(val query:String,val sourceIds:List<String> = emptyList(),val evidenceIds:List<String> = emptyList(),val action:String?=null)
data class EvaartaSemanticEvent(val id:String,val actorId:String,val lamport:Long,val objectId:String,val type:String,val payload:Map<String,String> = emptyMap(),val baseRevision:String?=null)
data class EvaartaConflict(val objectId:String,val baseRevision:String?,val local:String,val remote:String,val reason:String="concurrent-update",val resolution:String?=null)
data class EvaartaCapture(val id:String,val type:EvaartaCaptureType,val uri:String,val sourceId:String?=null,val transcript:String?=null,val ocrText:String?=null)

object EvaartaAdvancedContracts {
    fun validProduction(request:EvaartaPdfProductionRequest) = request.operation.isNotBlank()
    fun validResearch(card:EvaartaResearchCard) = card.id.isNotBlank() && card.sourceId.isNotBlank() && card.anchor.isNotBlank()
    fun validCitation(record:EvaartaScholarlyRecord) = record.id.isNotBlank()
    fun validMarkdown(document:EvaartaMarkdownDocument) = document.id.isNotBlank() && document.path.isNotBlank()
    fun validAiRequest(request:EvaartaGroundedAiRequest) = request.query.isNotBlank()
    fun validEvent(event:EvaartaSemanticEvent) = event.id.isNotBlank() && event.actorId.isNotBlank() && event.objectId.isNotBlank() && event.lamport >= 0
    fun validCapture(capture:EvaartaCapture) = capture.id.isNotBlank() && capture.uri.isNotBlank()
}
