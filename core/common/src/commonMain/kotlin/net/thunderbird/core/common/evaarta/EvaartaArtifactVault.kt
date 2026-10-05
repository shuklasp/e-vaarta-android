package net.thunderbird.core.common.evaarta
data class EvaartaVaultObject(val contentHash:String,val size:Long,val mime:String="application/octet-stream",val chunks:List<String> = emptyList(),val state:String="announced")
data class EvaartaVault(val objects:Map<String,EvaartaVaultObject> = emptyMap(),val refs:Map<String,Set<String>> = emptyMap())
object EvaartaArtifactVault {
 const val VERIFIED="verified"; const val TOMBSTONED="tombstoned"
 fun put(v:EvaartaVault,o:EvaartaVaultObject)=v.copy(objects=v.objects+(o.contentHash.lowercase() to o.copy(state="stored")))
 fun verify(v:EvaartaVault,hash:String)=v.objects[hash.lowercase()]?.let{o->v.copy(objects=v.objects+(hash.lowercase() to o.copy(state=VERIFIED)))}?:v
 fun retain(v:EvaartaVault,hash:String,ref:String):EvaartaVault { val k=hash.lowercase();return v.copy(refs=v.refs+(k to ((v.refs[k]?:emptySet())+ref)))}
 fun collectGarbage(v:EvaartaVault,protected:Set<String> = emptySet(),legalHold:Set<String> = emptySet()):EvaartaVault { val keep=protected.map(String::lowercase).toSet()+legalHold.map(String::lowercase).toSet();return v.copy(objects=v.objects.mapValues{(h,o)->if((v.refs[h]?.isEmpty()!=false)&&h !in keep)o.copy(state=TOMBSTONED)else o})}
 fun contract()=listOf("content-addressed","sha256","dedupe","chunking","resumable","offline-first","legal-hold")
}