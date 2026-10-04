package net.thunderbird.core.common.evaarta

data class EvaartaPerson(val id:String,val name:String="",val addresses:List<String> = emptyList(),val phones:List<String> = emptyList())
data class EvaartaConversation(val id:String,val title:String="",val participantIds:List<String> = emptyList(),val channel:String="chat",val projectId:String?=null)
data class EvaartaMessage(val id:String,val conversationId:String,val senderId:String,val body:String="",val channel:String="chat",val recipientIds:List<String> = emptyList(),val threadId:String?=null,val state:String="sent",val evidenceIds:List<String> = emptyList(),val timestamp:String="")
data class EvaartaCommunicationEvent(val id:String,val idempotencyKey:String,val entityId:String,val baseVersion:Int?=null,val currentVersion:Int?=null)
data class EvaartaCommunicationPolicy(val retentionDays:Int?=null,val legalHold:Boolean=false,val encryptionRequired:Boolean=false,val dlpRules:List<String> = emptyList())
object EvaartaCommunicationSuite {
    fun identityMatch(people:List<EvaartaPerson>,addresses:List<String>,phones:List<String>):List<EvaartaPerson> =
        people.filter { p -> p.addresses.any{addresses.contains(it)} || p.phones.any{phones.contains(it)} }
    fun unifiedInbox(conversations:List<EvaartaConversation>,messages:List<EvaartaMessage>):List<EvaartaConversation> =
        conversations.sortedByDescending { c -> messages.filter{it.conversationId==c.id}.maxOfOrNull{it.timestamp} ?: "" }
    fun search(messages:List<EvaartaMessage>,query:String):List<EvaartaMessage> =
        messages.filter{it.body.contains(query,ignoreCase=true)}
    fun reconcile(events:List<EvaartaCommunicationEvent>):Pair<List<EvaartaCommunicationEvent>,List<EvaartaCommunicationEvent>> {
        val seen=mutableSetOf<String>(); val accepted=mutableListOf<EvaartaCommunicationEvent>(); val conflicts=mutableListOf<EvaartaCommunicationEvent>()
        events.forEach { e -> if(!seen.add(e.idempotencyKey)) return@forEach; if(e.baseVersion!=null && e.currentVersion!=null && e.baseVersion!=e.currentVersion) conflicts+=e else accepted+=e }
        return accepted to conflicts
    }
    fun policy(message:EvaartaMessage,policy:EvaartaCommunicationPolicy):Boolean =
        !policy.encryptionRequired || message.channel=="email" || message.channel=="matrix"
    fun contract():List<String> = listOf("universal-message-model","conversation-store","people-identity-resolution","provider-adapters","unified-inbox","offline-send-queue","calendar-engine","meeting-workspace","external-channel-adapters","communication-ai","workflow-automation","encryption-boundary","policy-dlp","retention","legal-hold","audit","interoperability")
}
