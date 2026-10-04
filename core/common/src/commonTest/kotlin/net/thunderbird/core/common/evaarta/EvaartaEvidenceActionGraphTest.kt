package net.thunderbird.core.common.evaarta

import kotlin.test.Test
import kotlin.test.assertEquals
import kotlin.test.assertFalse
import kotlin.test.assertTrue

class EvaartaEvidenceActionGraphTest {
    @Test fun validatesCanonicalChain() {
        val evidence = EvaartaSemanticNode("e", EvaartaSemanticNodeType.EVIDENCE, sourceIds=listOf("doc"))
        val claim = EvaartaSemanticNode("c", EvaartaSemanticNodeType.CLAIM, sourceIds=listOf("e"))
        val decision = EvaartaSemanticNode("d", EvaartaSemanticNodeType.DECISION, sourceIds=listOf("c"))
        val task = EvaartaSemanticNode("t", EvaartaSemanticNodeType.TASK, sourceIds=listOf("d"))
        val graph = EvaartaSemanticGraph(
            listOf(evidence, claim, decision, task),
            listOf(
                EvaartaSemanticEdge("e", "c", EvaartaSemanticEdgeType.SUPPORTS, listOf("e")),
                EvaartaSemanticEdge("c", "d", EvaartaSemanticEdgeType.RESULTS_IN),
                EvaartaSemanticEdge("d", "t", EvaartaSemanticEdgeType.RESULTS_IN),
            ),
        )
        assertTrue(EvaartaEvidenceActionGraph.validate(graph))
        assertEquals(1, EvaartaEvidenceActionGraph.nodesOfType(graph, EvaartaSemanticNodeType.EVIDENCE).size)
        assertEquals(2, EvaartaEvidenceActionGraph.linkedNodes(graph, "c").size)
    }

    @Test fun rejectsBrokenGraph() {
        val graph = EvaartaSemanticGraph(
            listOf(EvaartaSemanticNode("e", EvaartaSemanticNodeType.EVIDENCE)),
            listOf(EvaartaSemanticEdge("e", "missing", EvaartaSemanticEdgeType.SUPPORTS)),
        )
        assertFalse(EvaartaEvidenceActionGraph.validate(graph))
    }
}
