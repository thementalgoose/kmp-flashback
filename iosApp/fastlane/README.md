fastlane documentation
----

# Installation

Make sure you have the latest version of the Xcode command line tools installed:

```sh
xcode-select --install
```

For _fastlane_ installation instructions, see [Installing _fastlane_](https://docs.fastlane.tools/#installing-fastlane)

# Available Actions

## iOS

### ios certificates

```sh
[bundle exec] fastlane ios certificates
```

Get certificates

### ios load_asc_api_key

```sh
[bundle exec] fastlane ios load_asc_api_key
```

Load ASC API Key information to use in subsequent lanes

### ios update_app_version

```sh
[bundle exec] fastlane ios update_app_version
```

Updates the CFBundleShortVersionString in Info.plist

### ios update_marketing_version

```sh
[bundle exec] fastlane ios update_marketing_version
```

Updates the MARKETTING_VERSION in the project.pbxproj file

### ios fetch_and_increment_build_number

```sh
[bundle exec] fastlane ios fetch_and_increment_build_number
```

Bump build number based on most recent TestFlight build number

### ios build_release

```sh
[bundle exec] fastlane ios build_release
```

Build the iOS app for release

### ios build_upload_testflight

```sh
[bundle exec] fastlane ios build_upload_testflight
```

Build and upload to TestFlight

----

This README.md is auto-generated and will be re-generated every time [_fastlane_](https://fastlane.tools) is run.

More information about _fastlane_ can be found on [fastlane.tools](https://fastlane.tools).

The documentation of _fastlane_ can be found on [docs.fastlane.tools](https://docs.fastlane.tools).
