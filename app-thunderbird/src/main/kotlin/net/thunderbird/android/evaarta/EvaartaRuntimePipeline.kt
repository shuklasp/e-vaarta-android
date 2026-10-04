package net.thunderbird.android.evaarta

data class EvaartaRuntimeEnvelope(val messageId:String,val type:String,val sessionId:String?,val payload:Any?)
data class EvaartaRetryItem(val messageId:String,val attempts:Int,val nextAttemptAt:Long,val lastError:String?)

class EvaartaRuntimePipeline(
 private val trustStore: EvaartaTrustStore,
 private val manager: EvaartaTransportManager,
 private val queue: EvaartaStoreForwardQueue
) {
 fun assertTrusted(peer: EvaartaTrustedPeer) { check(trustStore.isTrusted(peer.actorId)) { "peer is untrusted or revoked" } }
 suspend fun send(peer: EvaartaTrustedPeer,envelope: EvaartaEnvelope): EvaartaDeliveryReceipt {
  assertTrusted(peer)
  val result=manager.send(envelope)
  return if(result.isSuccess) EvaartaDeliveryReceipt(envelope.messageId,peer.actorId,"accepted",envelope.type)
  else { queue.enqueue(envelope); EvaartaDeliveryReceipt(envelope.messageId,peer.actorId,"queued",envelope.type,result.exceptionOrNull()?.message) }
 }
}

object EvaartaAttachmentResume {
 fun missing(total:Int,received:Set<Int>):List<Int>=(0 until total).filterNot(received::contains)
}
