package net.thunderbird.android.evaarta

data class EvaartaPeer(val peerId:String,val transport:String,val identityHint:String?=null)
interface EvaartaPeerDiscoveryProvider { fun isAvailable():Boolean; suspend fun discover():List<EvaartaPeer> }
class EvaartaPeerDiscovery(private val providers:List<EvaartaPeerDiscoveryProvider>){suspend fun discover():List<EvaartaPeer>{val out=mutableListOf<EvaartaPeer>();for(p in providers)if(p.isAvailable())out.addAll(p.discover());return out.distinctBy{it.peerId+it.transport}}}
