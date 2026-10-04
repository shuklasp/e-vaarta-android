package net.thunderbird.android.evaarta

/**
 * Portable semantic model for the e-Vaarta document workspace.
 *
 * Keep this layer independent of Android UI so it can be reused by phone,
 * tablet and future synchronization code.
 */
data class DocumentWorkspace(
    val modelVersion: Int = 1,
    val id: String,
    val name: String,
    val description: String = "",
    val documents: List<Document> = emptyList(),
    val items: List<WorkspaceItem> = emptyList(),
    val links: List<WorkspaceLink> = emptyList(),
)

enum class DocumentKind {
    PDF, WORD, POWERPOINT, IMAGE, WEB, EMAIL, OTHER
}

data class Document(
    val id: String,
    val title: String,
    val kind: DocumentKind,
    val sourceRef: String? = null,
    val mimeType: String? = null,
)

data class SourceAnchor(
    val documentId: String,
    val page: Int? = null,
    val startOffset: Int? = null,
    val endOffset: Int? = null,
    val quote: String? = null,
)

sealed interface WorkspaceItem {
    val id: String
}

data class Excerpt(
    override val id: String,
    val text: String,
    val anchor: SourceAnchor,
    val title: String? = null,
) : WorkspaceItem

data class Note(
    override val id: String,
    val text: String = "",
    val title: String? = null,
) : WorkspaceItem

data class Annotation(
    override val id: String,
    val anchor: SourceAnchor,
    val annotationType: String = "highlight",
    val text: String? = null,
    val color: String? = null,
) : WorkspaceItem

enum class LinkKind {
    RELATES_TO, SUPPORTS, CONTRADICTS, DERIVED_FROM, REFERENCES
}

data class WorkspaceLink(
    val id: String,
    val fromId: String,
    val toId: String,
    val kind: LinkKind = LinkKind.RELATES_TO,
)
