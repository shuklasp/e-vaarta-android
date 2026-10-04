package net.thunderbird.android.evaarta
data class EvaartaDeepLink(val workspaceId:String,val documentId:String?=null,val itemId:String?=null)
object EvaartaDeepLinks{fun parse(uri:android.net.Uri):EvaartaDeepLink?{if(uri.scheme!="evaarta"||uri.host!="workspace")return null;val workspaceId=uri.pathSegments.firstOrNull()?:return null;return EvaartaDeepLink(workspaceId,uri.getQueryParameter("document"),uri.getQueryParameter("item"))}}
