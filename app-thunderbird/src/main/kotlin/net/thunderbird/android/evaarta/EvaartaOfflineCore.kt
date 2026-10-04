package net.thunderbird.android.evaarta

data class EvaartaSyncManifest(
    val manifestVersion: Int = 1,
    val workspaceId: String,
    val revision: Long,
    val checksum: String,
    val writerId: String,
    val deviceId: String,
    val updatedAt: String,
)

enum class EvaartaSyncComparison { EQUAL, LOCAL_NEWER, REMOTE_NEWER, CONFLICT }

object EvaartaSyncManifests {
    fun compare(local: EvaartaSyncManifest, remote: EvaartaSyncManifest): EvaartaSyncComparison {
        require(local.workspaceId == remote.workspaceId) { "Workspace identity mismatch" }
        if (local.checksum == remote.checksum) return EvaartaSyncComparison.EQUAL
        return when {
            local.revision > remote.revision -> EvaartaSyncComparison.LOCAL_NEWER
            local.revision < remote.revision -> EvaartaSyncComparison.REMOTE_NEWER
            else -> EvaartaSyncComparison.CONFLICT
        }
    }
}

class EvaartaWorkspaceRepository(private val store: EvaartaWorkspaceFileStore) {
    fun load(workspaceId: String): EvaartaWorkspaceRecord? = store.load(workspaceId)?.record
    suspend fun save(record: EvaartaWorkspaceRecord): EvaartaWorkspaceWriteStatus = store.save(record)
    fun recover(workspaceId: String): EvaartaWorkspaceRecord? = store.recover(workspaceId)?.record
    fun delete(workspaceId: String) = store.remove(workspaceId)
}
