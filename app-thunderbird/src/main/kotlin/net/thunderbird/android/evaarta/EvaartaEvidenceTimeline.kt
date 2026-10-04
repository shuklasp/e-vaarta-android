package net.thunderbird.android.evaarta
data class EvaartaEvidenceTimelineEntry(val index:Int,val itemId:String,val kind:String,val title:String,val text:String,val page:Int?)
fun DocumentWorkspace.evidenceTimeline(documentId:String):List<EvaartaEvidenceTimelineEntry> =
    items.filter{it.anchor?.documentId==documentId}.sortedWith(compareBy({it.anchor?.page ?: Int.MAX_VALUE},{it.anchor?.startOffset ?: Int.MAX_VALUE},{it.id})).mapIndexed{index,it->EvaartaEvidenceTimelineEntry(index,it.id,it.kind,it.title ?: it.kind,it.text ?: it.anchor?.quote ?: "",it.anchor?.page)}