package net.thunderbird.core.common.evaarta

data class EvaartaTask(
    val id:String,val title:String,val status:String="inbox",val priority:String="medium",
    val assigneeId:String?=null,val teamId:String?=null,val dependsOn:List<String>=emptyList(),
    val estimateMinutes:Int=0,val actualMinutes:Int=0,val remainingMinutes:Int=estimateMinutes,
    val progress:Int=0,val projectId:String?=null,val parentId:String?=null,
    val evidenceIds:List<String>=emptyList(),val decisionIds:List<String>=emptyList(),
    val sourceIds:List<String>=emptyList(),val skills:List<String>=emptyList()
)
data class EvaartaAssignment(val mode:String="direct",val assigneeId:String?=null,val teamId:String?=null,val rationale:String?=null,val state:String="pending")
data class EvaartaCapacity(val capacityMinutes:Int,val committedMinutes:Int){
    val availableMinutes:Int get()=maxOf(0,capacityMinutes-committedMinutes)
    val utilization:Double get()=if(capacityMinutes>0) committedMinutes.toDouble()/capacityMinutes else 0.0
    val overloaded:Boolean get()=committedMinutes>capacityMinutes
}
data class EvaartaTaskHealth(val health:String,val overdue:Boolean,val stalled:Boolean,val unassigned:Boolean,val unaccepted:Boolean)
object EvaartaTaskWorkManagement {
    val statuses=setOf("inbox","planned","ready","assigned","accepted","in-progress","waiting","blocked","review","approved","done","cancelled","deferred","rejected","duplicate")
    fun assign(t:EvaartaTask,a:EvaartaAssignment)=t.copy(assigneeId=a.assigneeId,teamId=a.teamId,status=if(a.assigneeId!=null||a.teamId!=null)"assigned" else t.status)
    fun accept(t:EvaartaTask,accepted:Boolean)=t.copy(status=if(accepted)"accepted" else "rejected")
    fun validateGraph(tasks:List<EvaartaTask>):Boolean { val ids=tasks.map{it.id}.toSet(); val visiting=mutableSetOf<String>(); val visited=mutableSetOf<String>()
        fun dfs(id:String):Boolean { if(id in visiting)return false; if(id in visited)return true; val t=tasks.firstOrNull{it.id==id}?:return false; visiting+=id; if(t.dependsOn.any{!dfs(it)})return false; visiting-=id; visited+=id; return true }; return tasks.all{dfs(it.id)}
    }
    fun monitor(t:EvaartaTask,nowEpochMs:Long=System.currentTimeMillis(),updatedEpochMs:Long=nowEpochMs,dueEpochMs:Long?=null,staleHours:Long=72):EvaartaTaskHealth {
        val un=t.assigneeId==null&&t.teamId==null; val stale=nowEpochMs-updatedEpochMs>=staleHours*3600000
        val overdue=dueEpochMs!=null&&nowEpochMs>dueEpochMs&&t.status!="done"
        val h=when { un->"unassigned";t.status=="blocked"->"blocked";stale->"stalled";overdue->"late";else->"on-track" }
        return EvaartaTaskHealth(h,overdue,stale,un,false)
    }
    fun workload(tasks:List<EvaartaTask>,capacity:Map<String,Int>)=capacity.mapValues{(id,cap)->val c=tasks.filter{it.assigneeId==id}.sumOf{it.remainingMinutes};EvaartaCapacity(cap,c)}
}
