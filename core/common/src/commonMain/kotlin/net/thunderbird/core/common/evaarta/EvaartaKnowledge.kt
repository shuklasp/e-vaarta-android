package net.thunderbird.core.common.evaarta
data class EvaartaEvidenceRef(val id:String,val sourceId:String?=null,val anchor:String?=null)
data class EvaartaClaim(val id:String,val text:String,val evidenceIds:List<String> = emptyList())
data class EvaartaGroundedAnswer(val answer:String,val claims:List<EvaartaClaim>,val evidence:List<EvaartaEvidenceRef>){
    val grounded:Boolean get()=claims.all{it.evidenceIds.all{id->evidence.any{it.id==id}}}
}
data class EvaartaTask(val id:String,val title:String,val dependsOn:List<String> = emptyList(),val duration:Int=1,val status:String="todo")
object EvaartaTaskGraph {
    fun isValid(tasks:List<EvaartaTask>):Boolean {
        val byId=tasks.associateBy{it.id}; val visiting=mutableSetOf<String>(); val visited=mutableSetOf<String>()
        fun dfs(id:String):Boolean {
            if(id in visiting)return false; if(id in visited)return true
            val task=byId[id]?:return false; visiting+=id
            if(task.dependsOn.any{!dfs(it)})return false
            visiting-=id; visited+=id; return true
        }
        return tasks.all{dfs(it.id)}
    }
}
