package net.thunderbird.android.evaarta
fun normalizeEvaartaSelection(ids:Collection<String>,knownIds:Set<String>):List<String> = ids.distinct().filter{it in knownIds}
fun toggleEvaartaSelection(ids:Collection<String>,id:String,knownIds:Set<String>):List<String>{val s=normalizeEvaartaSelection(ids,knownIds).toMutableSet();if(!s.add(id))s.remove(id);return s.toList()}