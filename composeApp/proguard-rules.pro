# Add project specific ProGuard rules here.
# You can control the set of applied configuration files using the
# proguardFiles setting in build.gradle.
#
# For more details, see
#   http://developer.android.com/guide/developing/tools/proguard.html

# If your project uses WebView with JS, uncomment the following
# and specify the fully qualified class name to the JavaScript interface
# class:
#-keepclassmembers class fqcn.of.javascript.interface.for.webview {
#   public *;
#}

# Uncomment this to preserve the line number information for
# debugging stack traces.
#-keepattributes SourceFile,LineNumberTable

# If you keep the line number information, uncomment this to
# hide the original source file name.
#-renamesourcefileattribute SourceFile

-dontwarn flashback.presentation.localisation.generated.resources.Res$string
-dontwarn flashback.presentation.localisation.generated.resources.**

-keep class tmg.flashback.formula1.** { *; }

-dontwarn tmg.flashback.data.repo.repository.OverviewRepository

-dontwarn tmg.flashback.infrastructure.datetime.FormattersKt
-dontwarn tmg.flashback.infrastructure.datetime.LocalDateKt
-dontwarn tmg.flashback.infrastructure.extensions.IntExtensionsKt
-dontwarn tmg.flashback.infrastructure.device.Device
-keep class tmg.flashback.infrastructure.device.Device { *; }

-dontwarn tmg.flashback.crashlytics.manager.CrashlyticsManager
-keep class tmg.flashback.crashlytics.manager.**

-dontwarn okhttp3.internal.Util

-dontwarn java.lang.invoke.StringConcatFactory

-dontwarn androidx.appcompat.view.ContextThemeWrapper
-dontwarn javax.xml.stream.XMLStreamException

-keepattributes Exceptions, Signature, InnerClasses, LineNumberTable