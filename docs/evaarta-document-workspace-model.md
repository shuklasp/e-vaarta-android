# e-Vaarta Document Workspace Model — Android

Android will implement the same semantic workspace contract used by the desktop client.

## Core entities

- Workspace
- Document
- SourceAnchor
- Excerpt
- Note
- Annotation
- Link

A document can originate from local storage, an email attachment, a message, a web page or a future synchronized document provider.

## Source integrity

Every excerpt or annotation that represents evidence from a document must retain:

- document ID
- optional page number
- optional text start/end offsets
- optional quoted source text

This makes workspace notes navigable back to their source instead of becoming disconnected copied text.

## Planned Android UI

The first Android workspace will use a reading-first layout:

- document reader
- workspace sheet/pane
- excerpt action
- note action
- source navigation
- relationship list

On phones, the workspace can be presented as a bottom sheet or secondary screen. On tablets/foldables, it can become a persistent split pane.

## Shared interchange

The canonical interchange format is versioned JSON. Android should preserve unknown fields when practical so newer desktop/iOS clients can add metadata without breaking older workspaces.
