package net.thunderbird.android.evaarta

data class EvaartaEmailEnvelope(val messageId:String,val contentType:String="application/evaarta+json",val body:String)
interface EvaartaEmailGateway {
 suspend fun send(destination:String,envelope:EvaartaEmailEnvelope):Result<Unit>
 suspend fun receive():List<EvaartaEmailEnvelope>
}
