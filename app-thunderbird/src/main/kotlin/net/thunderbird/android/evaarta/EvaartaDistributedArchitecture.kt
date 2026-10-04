package net.thunderbird.android.evaarta

data class EvaartaIdentity(val actorId:String,val publicKey:String,val fingerprint:String)
data class EvaartaCapability(val id:String,val projectId:String,val subject:String,val actions:Set<String>,val expiresAt:String?=null)
data class EvaartaEnvelope(val messageId:String,val source:String,val destination:String,val type:String,val ciphertext:String?,val signature:String?,val hopLimit:Int=8)
data class EvaartaProjectEvent(val eventId:String,val projectId:String,val actorId:String,val type:String,val payload:String,val parentIds:List<String>,val sequence:Long)
data class EvaartaTransportStatus(val id:String,val available:Boolean,val reason:String?=null)
interface EvaartaTransport { val id:String; fun isAvailable():Boolean; suspend fun send(envelope:EvaartaEnvelope):Result<Unit> }
interface EvaartaCryptoProvider { suspend fun sign(data:ByteArray,identity:EvaartaIdentity):String; suspend fun verify(data:ByteArray,signature:String,identity:EvaartaIdentity):Boolean; suspend fun encrypt(data:ByteArray,recipient:EvaartaIdentity):ByteArray; suspend fun decrypt(data:ByteArray,identity:EvaartaIdentity):ByteArray }
class EvaartaTransportManager(private val transports:List<EvaartaTransport>){fun available():List<EvaartaTransportStatus> = transports.map{EvaartaTransportStatus(it.id,it.isAvailable())};suspend fun send(envelope:EvaartaEnvelope):Result<Unit>{val t=transports.firstOrNull{it.isAvailable()}?:return Result.failure(IllegalStateException("no transport available"));return t.send(envelope)}}
class EvaartaStoreForwardQueue{private val items=mutableListOf<EvaartaEnvelope>();fun enqueue(e:EvaartaEnvelope){items.add(e)};fun snapshot():List<EvaartaEnvelope> = items.toList();fun remove(e:EvaartaEnvelope){items.remove(e)}}
