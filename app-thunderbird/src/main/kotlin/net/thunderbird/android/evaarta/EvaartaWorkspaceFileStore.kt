package net.thunderbird.android.evaarta

import android.content.Context
import java.io.File
import java.io.FileOutputStream
import java.nio.charset.StandardCharsets

/**
 * Crash-safe file store for one e-Vaarta workspace record.
 *
 * The semantic record is encoded by the caller so this storage layer stays
 * independent of JSON/serialization libraries already used by the app.
 */
class EvaartaWorkspaceFileStore(
    context: Context,
    private val codec: Codec,
) {
    interface Codec {
        fun encode(record: EvaartaWorkspaceRecord): String
        fun decode(serialized: String): EvaartaWorkspaceRecord
    }

    data class LoadResult(
        val record: EvaartaWorkspaceRecord,
        val source: Source,
        val recovered: Boolean,
    )

    enum class Source { PRIMARY, BACKUP }

    private val directory = File(context.filesDir, "evaarta/workspaces")

    suspend fun save(record: EvaartaWorkspaceRecord): EvaartaWorkspaceWriteStatus {
        directory.mkdirs()
        val paths = paths(record.workspaceId)
        val current = readValid(paths.primary) ?: readValid(paths.backup)
        val status = when {
            current == null -> EvaartaWorkspaceWriteStatus.CREATE
            record.workspaceId != current.workspaceId ->
                error("Workspace identity mismatch")
            record.revision == current.revision + 1 -> EvaartaWorkspaceWriteStatus.REPLACE
            record.revision <= current.revision -> EvaartaWorkspaceWriteStatus.CONFLICT
            else -> error("Workspace revision has a gap")
        }
        if (status == EvaartaWorkspaceWriteStatus.CONFLICT) {
            error("Workspace write conflicts with the current revision")
        }

        val temp = paths.temp
        writeAndSync(temp, codec.encode(record))

        if (paths.primary.exists()) {
            if (paths.backup.exists()) paths.backup.delete()
            if (!paths.primary.renameTo(paths.backup)) {
                temp.delete()
                error("Unable to preserve the previous workspace snapshot")
            }
        }
        if (!temp.renameTo(paths.primary)) {
            // The previous valid snapshot remains available as backup.
            error("Unable to commit workspace snapshot")
        }
        return status
    }

    fun load(workspaceId: String): LoadResult? {
        val paths = paths(workspaceId)
        readValid(paths.primary)?.let {
            return LoadResult(it, Source.PRIMARY, recovered = false)
        }
        readValid(paths.backup)?.let {
            return LoadResult(it, Source.BACKUP, recovered = true)
        }
        if (!paths.primary.exists() && !paths.backup.exists()) return null
        error("No valid e-Vaarta workspace snapshot is available")
    }

    fun recover(workspaceId: String): LoadResult? {
        val loaded = load(workspaceId) ?: return null
        if (loaded.source == Source.PRIMARY) return loaded
        val paths = paths(workspaceId)
        writeAndSync(paths.temp, codec.encode(loaded.record))
        if (paths.primary.exists()) paths.primary.delete()
        if (!paths.temp.renameTo(paths.primary)) error("Unable to repair workspace snapshot")
        return loaded.copy(source = Source.PRIMARY, recovered = true)
    }

    fun remove(workspaceId: String) {
        val paths = paths(workspaceId)
        paths.primary.delete()
        paths.backup.delete()
        paths.temp.delete()
    }

    private fun readValid(file: File): EvaartaWorkspaceRecord? {
        if (!file.exists()) return null
        return try {
            codec.decode(file.readText(StandardCharsets.UTF_8))
        } catch (_: Exception) {
            null
        }
    }

    private fun writeAndSync(file: File, content: String) {
        FileOutputStream(file).use { output ->
            output.write(content.toByteArray(StandardCharsets.UTF_8))
            output.fd.sync()
        }
    }

    private data class Paths(
        val primary: File,
        val backup: File,
        val temp: File,
    )

    private fun paths(workspaceId: String): Paths {
        val safeId = workspaceId.replace(Regex("[^A-Za-z0-9._-]"), "_")
        val primary = File(directory, "$safeId.json")
        return Paths(
            primary = primary,
            backup = File(directory, "$safeId.json.bak"),
            temp = File(directory, "$safeId.json.tmp"),
        )
    }
}
