package net.thunderbird.core.common.evaarta

data class EvaartaCredentialReference(val provider:String,val accountId:String,val secretRef:String?=null)
data class EvaartaCapabilityToken(val subject:String,val capability:String,val resource:String,val expiresAt:String?=null,val nonce:String)
data class EvaartaIntegrationState(val id:String,val type:String,val status:String,val capabilities:List<String> = emptyList(),val lastSync:String?=null,val error:String?=null)
object EvaartaProductionIntegration {
    const val STATUS_NOT_CONFIGURED="not-configured"; const val STATUS_CONNECTED="connected"; const val STATUS_DEGRADED="degraded"
    fun authorize(token:EvaartaCapabilityToken,required:String,resource:String):Boolean = token.capability==required && token.resource==resource
    fun credential(provider:String,accountId:String,secretRef:String?=null)=EvaartaCredentialReference(provider,accountId,secretRef)
    fun contract():List<String> = listOf("offline-first","ui-projections","provider-adapters","calendar-adapters","credential-isolation","capability-authorization","audit","release-validation")
}
