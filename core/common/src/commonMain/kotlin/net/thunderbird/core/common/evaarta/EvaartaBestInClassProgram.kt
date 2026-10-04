package net.thunderbird.core.common.evaarta
enum class EvaartaProductClass(val id:String){COMMUNICATION("communication"),PDF_READER("pdf-reader"),PDF_PRODUCTION("pdf-production"),DOCUMENTS("documents"),RESEARCH("research"),KNOWLEDGE("knowledge"),CITATIONS("citations"),SEARCH("search"),OCR("ocr"),AI("ai"),PROJECTS("projects"),TASKS("tasks"),MEETINGS("meetings"),CALENDAR("calendar"),AUTOMATION("automation"),COLLABORATION("collaboration"),EVIDENCE("evidence"),ACCESSIBILITY("accessibility"),OFFLINE("offline"),SECURITY("security"),PRIVACY("privacy"),INTEROPERABILITY("interoperability"),MOBILE("mobile"),GOVERNANCE("governance"),REPORTING("reporting")}
object EvaartaBestInClassProgram{
 val principles=listOf("one-semantic-graph","evidence-before-assertion","revision-aware-provenance","offline-first","local-first-privacy","explicit-permissions","auditable-automation","loss-report","accessibility-gate","benchmark-before-claim")
 val classes=EvaartaProductClass.entries.map{it.id}
 fun requiresHumanAndAdversarial(evidenceCount:Int,human:Boolean,adversarial:Boolean)=evidenceCount>0&&human&&adversarial
}