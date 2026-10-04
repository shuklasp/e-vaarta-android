package net.thunderbird.core.evaarta

import kotlin.test.Test
import kotlin.test.assertEquals

class EvaartaModelTest {
    @Test
    fun conversationCanLinkToProject() {
        val conversation = EvaartaConversation(
            id = "conversation-1",
            title = "Project kickoff",
            participantIds = listOf("person-1"),
            channel = EvaartaChannel.EMAIL,
            projectId = "project-1",
        )

        assertEquals("project-1", conversation.projectId)
    }

    @Test
    fun taskCanPreserveConversationOrigin() {
        val task = EvaartaTask(
            id = "task-1",
            title = "Prepare project brief",
            projectId = "project-1",
            sourceConversationId = "conversation-1",
        )

        assertEquals("conversation-1", task.sourceConversationId)
    }
}
