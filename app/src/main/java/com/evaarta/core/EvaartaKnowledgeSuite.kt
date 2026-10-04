package com.evaarta.core
import java.util.UUID
data class EvaartaAnchor(val documentId:String,val revisionId:String?=null,val page:Int?=null,val startOffset:Int?=null,val endOffset:Int?=null,val quote:String?=null,val selector:String?=null,val rects:List<Float>?=null)
enum class EvaartaEntityKind { DOCUMENT,REVISION,ANNOTATION,EVIDENCE,NOTE,CLAIM,ARGUMENT,FINDING,DECISION,TASK,PERSON,PROJECT,SOURCE }
enum class EvaartaRelation { SUPPORTS,CONTRADICTS,DERIVED_FROM,REFERENCES,RELATES_TO,QUALIFIES,ANSWERS,RAISES,DEPENDS_ON,IMPLEMENTS,RESULTS_IN,ASSIGNED_TO,VERIFIED_BY,SUPERSEDES }
data class EvaartaEntity(val id:String=UUID.randomUUID().toString(),val kind:EvaartaEntityKind,val data:Map<String,Any?>=emptyMap())
data class EvaartaRelationEdge(val id:String=UUID.randomUUID().toString(),val fromId:String,val toId:String,val kind:EvaartaRelation,val confidence:Double?=null)
data class EvaartaEvidence(val id:String=UUID.randomUUID().toString(),val documentId:String,val revisionId:String?,val anchor:EvaartaAnchor,val text:String,val type:String="quote",val status:String="active")
data class EvaartaCanvasNode(val entityId:String,val kind:EvaartaEntityKind,var x:Float=0f,var y:Float=0f,var width:Float=280f,var height:Float=160f)
data class EvaartaCanvas(val id:String=UUID.randomUUID().toString(),val title:String="Evidence Workspace",val nodes:MutableList<EvaartaCanvasNode> = mutableListOf(),val edges:MutableList<EvaartaRelationEdge> = mutableListOf())
data class EvaartaProjectBundle(val version:Int=1,val project:EvaartaEntity,val entities:List<EvaartaEntity>,val relations:List<EvaartaRelationEdge>,val canvases:List<EvaartaCanvas>,val events:List<Map<String,Any?>>=emptyList())
object EvaartaKnowledgeSuite {
 fun evidence(documentId:String,anchor:EvaartaAnchor,text:String,type:String="quote")=EvaartaEvidence(documentId=documentId,revisionId=anchor.revisionId,anchor=anchor,text=text.trim(),type=type)
 fun relation(from:String,to:String,kind:EvaartaRelation)=EvaartaRelationEdge(fromId=from,toId=to,kind=kind)
 fun neighbors(id:String,relations:List<EvaartaRelationEdge>)=relations.filter{it.fromId==id||it.toId==id}
 fun lineage(id:String,relations:List<EvaartaRelationEdge>,upstream:Boolean=true):List<String>{
  val out=mutableListOf<String>();var frontier=listOf(id);val seen=mutableSetOf(id)
  repeat(20){if(frontier.isEmpty())return@repeat;val next=mutableListOf<String>();frontier.forEach{current->relations.forEach{r->val c=if(upstream&&r.toId==current)r.fromId else if(!upstream&&r.fromId==current)r.toId else null;if(c!=null&&seen.add(c)){next+=c;out+=c}}};frontier=next};return out
 }
 fun projectStatus(entities:List<EvaartaEntity>):Map<String,Int>{val t=entities.filter{it.kind==EvaartaEntityKind.TASK};val done=t.count{it.data["status"]=="completed"};return mapOf("total" to t.size,"completed" to done,"open" to t.size-done)}
}
