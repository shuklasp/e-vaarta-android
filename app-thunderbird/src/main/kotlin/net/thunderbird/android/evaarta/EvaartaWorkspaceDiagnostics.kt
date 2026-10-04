package net.thunderbird.android.evaarta
data class EvaartaDiagnosticResult(val healthy:Boolean,val errors:Int,val warnings:Int)
object EvaartaWorkspaceDiagnostics{fun inspect(workspace:DocumentWorkspace):EvaartaDiagnosticResult{val ids=workspace.documents.map{it.id};val unique=ids.toSet();val orphaned=workspace.items.count{it.anchor?.documentId?.let{d->d !in unique}==true};return EvaartaDiagnosticResult(ids.size==unique.size,ids.size-unique.size,orphaned)}}
