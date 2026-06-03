# Build the APK without Android Studio

This project can be built without installing Android Studio. The easiest option is GitHub Actions, which builds the APK in the cloud.

## Option A: Build APK using GitHub Actions

1. Create a new GitHub repository.
2. Upload all files from this project folder to that repository.
3. Open the repository on GitHub.
4. Go to **Actions**.
5. Select **Build Android APK**.
6. Click **Run workflow**.
7. After the workflow finishes, open the completed run.
8. Download the artifact named **Sindigar-Enterprises-debug-apk**.
9. Extract the artifact ZIP; inside you will find the APK.

The generated debug APK is suitable for testing and direct installation on Android devices after allowing installation from unknown sources. It is not suitable for publishing on Google Play.

## Option B: Build locally on macOS using only command-line tools

Install command-line dependencies:

```bash
brew install --cask temurin@17
brew install gradle
brew install --cask android-commandlinetools
```

Add Android tools to your shell path. For zsh, edit `~/.zshrc` and add a path similar to the one shown by Homebrew after installing `android-commandlinetools`.

Then install the Android SDK packages and build:

```bash
sdkmanager "platform-tools" "platforms;android-36" "build-tools;36.0.0"
yes | sdkmanager --licenses
gradle assembleDebug --no-daemon
```

The APK will be created at:

```text
app/build/outputs/apk/debug/app-debug.apk
```

## Release APK

For customer distribution or Play Store upload, create a release signing key and build a signed release APK or AAB. The debug APK is for testing only.
