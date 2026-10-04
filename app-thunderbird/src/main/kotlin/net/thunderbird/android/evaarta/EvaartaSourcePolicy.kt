package net.thunderbird.android.evaarta
enum class EvaartaSourceOpenMode{OFFLINE,EXTERNAL,UNAVAILABLE}
data class EvaartaSourceOpenResult(val mode:EvaartaSourceOpenMode,val reference:String?=null)
fun evaartaSourceOpenPolicy(document:Document,offlineReference:String?=null):EvaartaSourceOpenResult =
    if(offlineReference != null) EvaartaSourceOpenResult(EvaartaSourceOpenMode.OFFLINE,offlineReference)
    else document.sourceRef?.let{EvaartaSourceOpenResult(EvaartaSourceOpenMode.EXTERNAL,it)} ?: EvaartaSourceOpenResult(EvaartaSourceOpenMode.UNAVAILABLE)
