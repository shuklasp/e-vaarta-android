package net.thunderbird.android.evaarta
enum class EvaartaSourceOpenMode{OFFLINE,EXTERNAL,UNAVAILABLE}
data class EvaartaSourceOpenResult(val mode:EvaartaSourceOpenMode,val reference:String?=null)
fun EvaartaSourceOpenPolicy(document:Document,offlineAvailable:Boolean):EvaartaSourceOpenResult =
    if(offlineAvailable && document.vault?.relativePath != null) EvaartaSourceOpenResult(EvaartaSourceOpenMode.OFFLINE,document.vault.relativePath)
    else document.sourceRef?.let{EvaartaSourceOpenResult(EvaartaSourceOpenMode.EXTERNAL,it)} ?: EvaartaSourceOpenResult(EvaartaSourceOpenMode.UNAVAILABLE)