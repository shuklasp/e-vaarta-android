package net.thunderbird.android.evaarta
enum class EvaartaSyncSessionState{IDLE,DISCOVERING,COMPARING,CONFLICT,READY,COMMITTED,FAILED}
data class EvaartaSyncSession(val id:String,val workspaceId:String,val deviceId:String,val state:EvaartaSyncSessionState=EvaartaSyncSessionState.IDLE,val error:String?=null)