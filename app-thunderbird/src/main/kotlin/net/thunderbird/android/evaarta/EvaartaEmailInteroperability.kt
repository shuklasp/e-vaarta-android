package net.thunderbird.android.evaarta

data class EvaartaMailPayload(val contentType:String="application/evaarta+json",val body:String)
interface EvaartaMailInteroperability {
 suspend fun export(destination:String,payload:EvaartaMailPayload):Result<Unit>
 suspend fun import():List<EvaartaMailPayload>
}
