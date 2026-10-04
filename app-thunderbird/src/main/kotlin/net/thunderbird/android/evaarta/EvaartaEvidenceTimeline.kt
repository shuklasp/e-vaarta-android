package net.thunderbird.android.evaarta
data class EvaartaEvidenceTimelineEntry(val index:Int,val itemId:String,val kind:String,val title:String,val text:String,val page:Int?)
fun DocumentWorkspace.evidenceTimeline(documentId:String):List<EvaartaEvidenceTimelineEntry> =
    items.mapNotNull{item->
        when(item){
            is Excerpt -> if(item.anchor.documentId==documentId) EvaartaEvidenceTimelineEntry(0,item.id,"excerpt",item.title?:"excerpt",item.text,item.anchor.page) else null
            is Annotation -> if(item.anchor.documentId==documentId) EvaartaEvidenceTimelineEntry(0,item.id,"annotation","annotation",item.text?:"",item.anchor.page) else null
            is Note -> null
        }
    }.sortedWith(compareBy({it.page ?: Int.MAX_VALUE},{it.itemId})).mapIndexed{index,it->it.copy(index=index)}
