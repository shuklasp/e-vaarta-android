# e-Vaarta Android Bootstrap

## Role

This repository is the Android client foundation for e-Vaarta, based on Thunderbird for Android.

## Platform relationship

Android remains a native Kotlin/Android application. e-Vaarta does not require Android to reproduce the desktop implementation.

The shared product model should converge on:

- accounts and identities
- people and conversations
- messages and attachments
- projects and tasks
- calendar events
- documents
- notifications
- AI actions
- permissions and workspace context

## Integration rule

New shared behavior should be introduced behind stable contracts rather than duplicated in unrelated screens.

The Android implementation should use existing Thunderbird/K-9 modular boundaries where they are suitable and add e-Vaarta functionality as isolated features or adapters.

## OAuth and branding

This is a forked application. Before any distributable build, replace upstream OAuth client configuration and redirect URIs with e-Vaarta-specific configuration as required by the upstream forking guidance.

## Upstream synchronization

Keep upstream Thunderbird changes easy to merge. Prefer small, focused e-Vaarta commits.
