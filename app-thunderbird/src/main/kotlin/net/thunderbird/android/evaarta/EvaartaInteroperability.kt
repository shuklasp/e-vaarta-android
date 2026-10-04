package net.thunderbird.android.evaarta

import android.util.Base64
import java.nio.charset.StandardCharsets
import org.json.JSONObject

object EvaartaWireCodec {
 fun encode(fields:Map<String,Any?>):ByteArray=JSONObject(fields).toString().toByteArray(StandardCharsets.UTF_8)
 fun decode(bytes:ByteArray):JSONObject=JSONObject(String(bytes,StandardCharsets.UTF_8))
 fun b64(bytes:ByteArray)=Base64.encodeToString(bytes,Base64.NO_WRAP)
 fun unb64(value:String)=Base64.decode(value,Base64.NO_WRAP)
}

data class EvaartaAttachmentAck(val attachmentId:String,val received:Set<Int>) {
 fun missing(total:Int)=(0 until total).filterNot(received::contains)
}

class EvaartaInteroperabilityReceiver(
 private val trust:EvaartaTrustStore,
 private val queue:EvaartaStoreForwardQueue
) {
 fun assertPeer(peer:EvaartaTrustedPeer){check(trust.isTrusted(peer.actorId)){"peer is untrusted or revoked"}}
 fun acceptEnvelope(peer:EvaartaTrustedPeer,envelope:EvaartaEnvelope):Boolean {
  assertPeer(peer)
  queue.enqueue(envelope)
  return true
 }
}
