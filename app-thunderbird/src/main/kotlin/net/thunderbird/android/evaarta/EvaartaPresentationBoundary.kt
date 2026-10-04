package net.thunderbird.android.evaarta
data class EvaartaPresentationBoundary(val workspace:EvaartaWorkspaceState,val reader:EvaartaReaderNavigation?=null,val selectedEvidence:List<String> = emptyList(),val searchQuery:String="")
fun EvaartaPresentationBoundary.withReader(navigation:EvaartaReaderNavigation?)=copy(reader=navigation)