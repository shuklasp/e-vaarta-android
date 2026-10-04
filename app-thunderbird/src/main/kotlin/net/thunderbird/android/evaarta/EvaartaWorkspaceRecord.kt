package net.thunderbird.android.evaarta

/**
 * Storage-neutral metadata for an offline workspace snapshot.
 *
 * The persistence implementation may use Room, SQLite, files, or another
 * local store without changing the semantic DocumentWorkspace model.
 */
data class EvaartaWorkspaceRecord(
    val persistenceVersion: Int = 1,
    val workspaceId: String,
    val revision: Long,
    val writerId: String = "local",
    val writtenAt: String,
    val checksum: String,
    val workspace: DocumentWorkspace,
)

enum class EvaartaWorkspaceWriteStatus {
    CREATE,
    REPLACE,
    CONFLICT,
}
