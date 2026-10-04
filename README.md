# e-Vaarta for Android

e-Vaarta for Android is the mobile email client in the e-Vaarta family. It is based on the Thunderbird for Android codebase, which in turn builds on the long-running K-9 Mail technology stack.

e-Vaarta for Android is intended to provide a fast, privacy-focused email experience with strong support for multiple accounts and a unified inbox.

## Project direction

The Android application shares the e-Vaarta product identity with the desktop and iOS clients while remaining a native Android application.

Our product work will focus on:

- a coherent e-Vaarta visual identity;
- privacy-respecting account and mail handling;
- a consistent cross-platform information architecture;
- mobile-first performance, accessibility, and usability.

## Development

This repository retains the underlying Thunderbird/K-9 engineering architecture where it provides a strong foundation. Thunderbird and K-9 documentation therefore remain useful when working on platform internals.

For project-specific work, use GitHub Issues and Pull Requests in this repository.

## Forking and OAuth

e-Vaarta uses its own application identity and OAuth configuration. Any production OAuth clients and redirect URIs must be unique to e-Vaarta and must not conflict with Thunderbird's application identifiers.

## License

e-Vaarta for Android contains substantial code originating from Thunderbird and K-9 Mail. Please see the repository's license files for the applicable licensing terms.
