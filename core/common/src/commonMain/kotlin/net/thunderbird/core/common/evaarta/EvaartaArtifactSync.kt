package net.thunderbird.core.common.evaarta
data class EvaartaTransfer(val id:String,val contentHash:String,val totalChunks:Int,val received:Set<Int> = emptySet(),val state:String="queued")
object EvaartaArtifactSync {
 const val COMPLETE="complete"
 fun accept(t:EvaartaTransfer,index:Int):EvaartaTransfer { require(index in 0 until t.totalChunks);val r=t.received+index;return t.copy(received=r,state=if(r.size==t.totalChunks)COMPLETE else "transferring")}
 fun missing(t:EvaartaTransfer)=(0 until t.totalChunks).filterNot{it in t.received}
 fun plan(local:Set<String>,peer:Set<String>)=mapOf("request" to (local-peer),"offer" to (peer-local))
 fun reconcile(events:List<String>)=events.distinct().sorted()
 fun contract()=listOf("offline-queue","resumable","missing-chunks","deterministic-reconciliation","store-and-forward","device-to-device")
}