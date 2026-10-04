package net.thunderbird.core.evaarta

/**
 * Platform-neutral e-Vaarta domain model.
 *
 * This package intentionally contains no UI, database, networking, or
 * Thunderbird-specific types. It is the contract used by higher layers.
 */
enum class EvaartaChannel {
    EMAIL,
    SMS,
    RCS,
    WHATSAPP,
    SIGNAL,
    TELEGRAM,
    SLACK,
    TEAMS,
    MATRIX,
    XMPP,
    OTHER,
}

enum class EvaartaMessageState {
    DRAFT,
    SENT,
    DELIVERED,
    READ,
    FAILED,
}

data class EvaartaPerson(
    val id: String,
    val displayName: String,
    val addresses: List<String> = emptyList(),
)

data class EvaartaMessage(
    val id: String,
    val conversationId: String,
    val senderId: String,
    val timestampEpochMillis: Long,
    val channel: EvaartaChannel,
    val body: String,
    val state: EvaartaMessageState = EvaartaMessageState.SENT,
    val attachmentIds: List<String> = emptyList(),
)

data class EvaartaConversation(
    val id: String,
    val title: String?,
    val participantIds: List<String>,
    val channel: EvaartaChannel,
    val messageIds: List<String> = emptyList(),
    val projectId: String? = null,
)

enum class EvaartaTaskStatus {
    TODO,
    IN_PROGRESS,
    BLOCKED,
    DONE,
    CANCELLED,
}

data class EvaartaProject(
    val id: String,
    val name: String,
    val description: String? = null,
    val ownerId: String? = null,
    val taskIds: List<String> = emptyList(),
)

data class EvaartaTask(
    val id: String,
    val title: String,
    val projectId: String?,
    val status: EvaartaTaskStatus = EvaartaTaskStatus.TODO,
    val assigneeId: String? = null,
    val dueAtEpochMillis: Long? = null,
    val sourceConversationId: String? = null,
)

enum class EvaartaAiExecution {
    LOCAL,
    CLOUD,
    HYBRID,
}

data class EvaartaAiContext(
    val execution: EvaartaAiExecution,
    val model: String? = null,
    val userApproved: Boolean = false,
)
