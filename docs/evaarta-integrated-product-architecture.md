# e-Vaarta Android Integrated Product Architecture

Android uses the same semantic workspace vocabulary as desktop: communication → document → evidence → claim → finding → decision → task → project → report → citation → communication.

The common layer supplies workspace routing, identity matching, attachment deduplication, grounded-AI checks and semantic product contracts. UI implementations should project this model rather than create divergent Android-only semantics.

Offline operation is the default. Synchronization must remain event-based and conflict-visible. Provider credentials remain outside semantic objects.

Source-level parity does not constitute Android build, lifecycle, background execution, notification, storage, encryption, accessibility or physical-device validation.
