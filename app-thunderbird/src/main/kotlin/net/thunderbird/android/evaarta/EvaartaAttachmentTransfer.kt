package net.thunderbird.android.evaarta

data class EvaartaAttachmentChunk(
    val attachmentId: String,
    val index: Int,
    val total: Int,
    val data: ByteArray
)

object EvaartaAttachmentTransfer {
    const val DEFAULT_CHUNK_SIZE = 256 * 1024

    fun chunk(attachmentId: String, data: ByteArray, chunkSize: Int = DEFAULT_CHUNK_SIZE): List<EvaartaAttachmentChunk> {
        require(chunkSize > 0)
        val total = (data.size + chunkSize - 1) / chunkSize
        return (0 until total).map { index ->
            val start = index * chunkSize
            val end = minOf(start + chunkSize, data.size)
            EvaartaAttachmentChunk(attachmentId, index, total, data.copyOfRange(start, end))
        }
    }

    fun assemble(chunks: Collection<EvaartaAttachmentChunk>): ByteArray {
        require(chunks.isNotEmpty())
        val ordered = chunks.sortedBy { it.index }
        require(ordered.indices.all { ordered[it].index == it })
        val total = ordered.first().total
        require(ordered.all { it.total == total })
        return ordered.fold(ByteArray(0)) { acc, chunk -> acc + chunk.data }
    }
}
