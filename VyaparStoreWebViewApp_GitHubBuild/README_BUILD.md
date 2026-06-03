# Vyapar Store WebView Android APK

This Android project opens the following URL immediately when the app is launched:

https://api1.vyaparapp.in/store/sindigarenterprises

## Build in Android Studio

1. Install Android Studio.
2. Install Android SDK Platform 36 from SDK Manager.
3. Open this folder in Android Studio.
4. Let Gradle sync download Android Gradle Plugin 8.13.0.
5. Build debug APK: Build > Build App Bundle(s) / APK(s) > Build APK(s).
6. Debug APK output is usually:
   app/build/outputs/apk/debug/app-debug.apk

## Release APK for sharing or publishing

1. Android Studio: Build > Generate Signed App Bundle / APK.
2. Choose APK.
3. Create or select your keystore.
4. Build release.
5. Output is usually:
   app/build/outputs/apk/release/app-release.apk

## Important files

- `app/src/main/java/in/sindigarenterprises/store/MainActivity.java` contains the WebView logic.
- `app/src/main/AndroidManifest.xml` contains Internet permission and the launcher activity.
- `app/build.gradle.kts` contains the application ID, min SDK, and target SDK.

## Change app name

Edit this value in `AndroidManifest.xml`:

```xml
android:label="Sindigar Enterprises"
```

## Change URL

Edit this line in `MainActivity.java`:

```java
private static final String STORE_URL = "https://api1.vyaparapp.in/store/sindigarenterprises";
```
