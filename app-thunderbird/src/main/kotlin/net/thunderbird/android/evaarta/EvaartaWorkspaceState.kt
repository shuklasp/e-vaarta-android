package net.thunderbird.android.evaarta
data class EvaartaWorkspaceState(val snapshot:EvaartaWorkspaceSnapshot?=null,val selectedDocumentId:String?=null,val selectedItemId:String?=null,val searchQuery:String="",val offlineReady:Boolean=false,val error:String?=null)
class EvaartaWorkspaceStateStore(initial:EvaartaWorkspaceState=EvaartaWorkspaceState()){var state:EvaartaWorkspaceState=initial;private set
 fun selectDocument(id:String?){state=state.copy(selectedDocumentId=id)};fun selectItem(id:String?){state=state.copy(selectedItemId=id)};fun setSearchQuery(query:String){state=state.copy(searchQuery=query)};fun setOfflineReady(ready:Boolean){state=state.copy(offlineReady=ready)};fun setError(message:String?){state=state.copy(error=message)};fun resetSelection(){state=state.copy(selectedDocumentId=null,selectedItemId=null)}
}
