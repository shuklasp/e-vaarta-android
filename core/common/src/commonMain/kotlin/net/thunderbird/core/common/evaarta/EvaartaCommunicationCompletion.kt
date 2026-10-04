package net.thunderbird.core.common.evaarta
data class EvaartaProviderBoundary(val provider:String,val operations:Set<String>,val secretRef:String?=null)
data class EvaartaNotificationRule(val id:String,val channel:String,val priority:Int,val batchMinutes:Int=0,val dedupeKey:String?=null)
data class EvaartaMeetingWorkspace(val meetingId:String,val sourceIds:List<String>,val decisions:List<String>,val actions:List<String>)
data class EvaartaDeviceKey(val deviceId:String,val keyId:String,val state:String="active")
data class EvaartaMigrationReport(val source:String,val total:Int,val warnings:List<String>,val lossless:Boolean)
object EvaartaCommunicationCompletion {
 val providers=listOf("imap","smtp","jmap","google","microsoft","matrix","slack","teams","whatsapp","telegram","zoom","webex","ringcentral","mattermost","signal","rcs")
 val accessibility=listOf("keyboard","screen-reader","reflow","text-scale","high-contrast","reduced-motion","touch","switch")
 fun aiGrounded(sourceIds:List<String>,citations:Set<String>)=sourceIds.isEmpty()||sourceIds.any{it in citations}
 fun rotate(keys:List<EvaartaDeviceKey>,newKey:EvaartaDeviceKey)=keys.map{if(it.state=="active")it.copy(state="revoked")else it}+newKey.copy(state="active")
}