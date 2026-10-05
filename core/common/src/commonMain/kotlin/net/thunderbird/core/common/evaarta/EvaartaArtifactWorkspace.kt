package net.thunderbird.core.common.evaarta
data class EvaartaArtifactWorkspace(val view:String="all",val selection:String?=null,val filters:Map<String,String> = emptyMap())
object EvaartaArtifactWorkspace {
 fun filter(w:EvaartaArtifactWorkspace,key:String,value:String)=w.copy(filters=w.filters+(key to value))
 fun select(w:EvaartaArtifactWorkspace,id:String)=w.copy(selection=id)
 fun matches(w:EvaartaArtifactWorkspace,artifacts:List<EvaartaArtifact>):List<EvaartaArtifact>{val type=w.filters["type"];val q=w.filters["query"]?.lowercase();return artifacts.filter{(type==null||it.type==type)&&(q==null||it.name.lowercase().contains(q)||it.contentHash?.contains(q)==true)}}
 fun contract()=listOf("one-identity","no-raw-duplication","relationship-aware","revision-aware","evidence-aware","task-aware","communication-aware","offline-first")
}