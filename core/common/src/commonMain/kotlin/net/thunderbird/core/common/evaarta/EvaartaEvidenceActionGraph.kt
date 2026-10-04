package net.thunderbird.core.common.evaarta

enum class EvaartaSemanticNodeType {
    COMMUNICATION, DOCUMENT, EVIDENCE, CLAIM, FINDING, DECISION, TASK, PROJECT, REPORT, CITATION
}

enum class EvaartaSemanticEdgeType {
    DERIVED_FROM, SUPPORTS, CONTRADICTS, REFERENCES, RESULTS_IN, ASSIGNED_TO, REPORTS, COMMUNICATES
}

data class EvaartaSemanticNode(
    val id: String,
    val type: EvaartaSemanticNodeType,
    val title: String = "",
    val sourceIds: List<String> = emptyList(),
    val revisionId: String? = null,
    val properties: Map<String, String> = emptyMap(),
)

data class EvaartaSemanticEdge(
    val from: String,
    val to: String,
    val type: EvaartaSemanticEdgeType,
    val evidenceIds: List<String> = emptyList(),
)

data class EvaartaSemanticGraph(
    val nodes: List<EvaartaSemanticNode>,
    val edges: List<EvaartaSemanticEdge>,
)

object EvaartaEvidenceActionGraph {
    fun validate(graph: EvaartaSemanticGraph): Boolean {
        val ids = graph.nodes.map { it.id }
        if (ids.any { it.isBlank() } || ids.size != ids.toSet().size) return false
        return graph.edges.all {
            it.from != it.to && it.from in ids && it.to in ids
        }
    }

    fun nodesOfType(graph: EvaartaSemanticGraph, type: EvaartaSemanticNodeType): List<EvaartaSemanticNode> =
        graph.nodes.filter { it.type == type }

    fun linkedNodes(graph: EvaartaSemanticGraph, nodeId: String, edgeType: EvaartaSemanticEdgeType? = null): List<EvaartaSemanticNode> {
        val ids = graph.edges
            .filter { edgeType == null || it.type == edgeType }
            .flatMap { edge ->
                buildList {
                    if (edge.from == nodeId) add(edge.to)
                    if (edge.to == nodeId) add(edge.from)
                }
            }.toSet()
        return graph.nodes.filter { it.id in ids }
    }
}
