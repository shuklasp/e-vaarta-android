package net.thunderbird.android.evaarta

/**
 * UI-independent source evidence queries.
 *
 * Results are derived from the workspace on demand; no reverse index is
 * persisted. Ordering matches the desktop e-Vaarta evidence navigator:
 * page, source offset, creation time, then stable ID.
 */
object EvaartaEvidenceQueries {
    data class Summary(
        val documentId: String,
        val evidenceCount: Int,
        val excerptCount: Int,
        val annotationCount: Int,
        val groupCount: Int,
        val pages: List<Int>,
    )

    fun forDocument(
        workspace: DocumentWorkspace,
        documentId: String,
        kind: WorkspaceItemKind? = null,
        page: Int? = null,
        groupItemIds: Set<String>? = null,
    ): List<WorkspaceItem> {
        return workspace.items
            .filter { item ->
                val anchor = when (item) {
                    is Excerpt -> item.anchor
                    is Annotation -> item.anchor
                    is Note -> null
                }
                anchor?.documentId == documentId &&
                    (kind == null || item.kind() == kind) &&
                    (page == null || anchor.page == page) &&
                    (groupItemIds == null || groupItemIds.contains(item.id))
            }
            .sortedWith(compareBy<WorkspaceItem>(
                { it.anchorPage() ?: Int.MAX_VALUE },
                { it.anchorOffset() ?: Int.MAX_VALUE },
                { it.id },
            ))
    }

    fun summary(workspace: DocumentWorkspace, documentId: String): Summary {
        val items = forDocument(workspace, documentId)
        return Summary(
            documentId = documentId,
            evidenceCount = items.size,
            excerptCount = items.count { it is Excerpt },
            annotationCount = items.count { it is Annotation },
            groupCount = workspace.evidenceGroups.count { group ->
                group.documentId == documentId || group.documentId == null && group.itemIds.any { itemId ->
                    items.any { it.id == itemId }
                }
            },
            pages = items.mapNotNull { it.anchorPage() }.distinct().sorted(),
        )
    }

    private fun WorkspaceItem.kind(): WorkspaceItemKind = when (this) {
        is Excerpt -> WorkspaceItemKind.EXCERPT
        is Note -> WorkspaceItemKind.NOTE
        is Annotation -> WorkspaceItemKind.ANNOTATION
    }

    private fun WorkspaceItem.anchorPage(): Int? = when (this) {
        is Excerpt -> anchor.page
        is Annotation -> anchor.page
        is Note -> null
    }

    private fun WorkspaceItem.anchorOffset(): Int? = when (this) {
        is Excerpt -> anchor.startOffset
        is Annotation -> anchor.startOffset
        is Note -> null
    }
}
