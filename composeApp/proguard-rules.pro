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
-keep public class com.google.android.gms.* { public *; }
-keep public class com.google.android.play.core.* { public *; }
-dontwarn com.google.android.gms.**

# Keep `Companion` object fields of serializable classes.
# This avoids serializer lookup through `getDeclaredClasses` as done for named companion objects.
-if @kotlinx.serialization.Serializable class **
-keepclassmembers class <1> {
    static <1>$Companion Companion;
}

# Keep `serializer()` on companion objects (both default and named) of serializable classes.
-if @kotlinx.serialization.Serializable class ** {
    static **$* *;
}
-keepclassmembers class <1>$<3> {
    kotlinx.serialization.KSerializer serializer(...);
}

# Keep `INSTANCE.serializer()` of serializable objects.
-if @kotlinx.serialization.Serializable class ** {
    public static ** INSTANCE;
}
-keepclassmembers class <1> {
    public static <1> INSTANCE;
    kotlinx.serialization.KSerializer serializer(...);
}

# @Keep @Serializable and @Polymorphic are used at runtime for polymorphic serialization.
-keepattributes RuntimeVisibleAnnotations,AnnotationDefault

-keep class tmg.flashback.ui.base.BaseActivity
-dontwarn tmg.flashback.crashlytics.manager.CrashlyticsManager

-dontwarn java.lang.invoke.StringConcatFactory

# Serializer for classes with named companion objects are retrieved using `getDeclaredClasses`.
# If you have any, uncomment and replace classes with those containing named companion objects.
#-keepattributes InnerClasses # Needed for `getDeclaredClasses`.
#-if @kotlinx.serialization.Serializable class
#com.example.myapplication.HasNamedCompanion, # <-- List serializable classes with named companions.
#com.example.myapplication.HasNamedCompanion2gi
#{
#    static **$* *;
#}
#-keepnames class <1>$$serializer { # -keepnames suffices; class is kept when serializer() is kept.
#    static <1>$$serializer INSTANCE;
#}

-keep class tmg.flashback.flashbackapi.api.models.**

-keep class tmg.flashback.persistence.flashback.models.**

-keep class tmg.flashback.data.repo.json.*
-keep class tmg.flashback.data.repo.models.*

-keep class tmg.flashback.rss.network.apis.model.*
-keep class tmg.flashback.rss.network.apis.*
-keep class tmg.flashback.rss.network.shared.*
-keep class tmg.flashback.rss.network.**
-keep class tmg.flashback.rss.repo.json.*
-keep class tmg.flashback.rss.repo.model.*

-keep class tmg.flashback.formula1.model.*
-keep class tmg.flashback.formula1.enums.*
-keep class tmg.flashback.formula1.constants.*

-keep class androidx.core.widget.NestedScrollView { *; }
-keep class androidx.constraintlayout.motion.widget.MotionLayout { *; }

# SimpleXML
-keep public class org.simpleframework.**{ *; }
-keep class org.simpleframework.xml.**{ *; }
-keep class org.simpleframework.xml.core.**{ *; }
-keep class org.simpleframework.xml.util.**{ *; }
-keep class retrofit2.converter.simplexml.** { *; }
# SimpleXML Annotations
-keepattributes *Annotation*
-keepattributes Signature

-keep class tmg.flashback.results.receivers.** { *; }

-dontwarn org.xmlpull.v1.**
-dontnote org.xmlpull.v1.**
-keep class org.xmlpull.** { *; }
-keepclassmembers class org.xmlpull.** { *; }

-keepattributes *Annotation*
-keepattributes SourceFile,LineNumberTable
-keep public class * extends java.lang.Exception

-dontwarn androidx.appcompat.view.ContextThemeWrapper
-dontwarn javax.xml.stream.XMLStreamException

-keepattributes Exceptions, Signature, InnerClasses, LineNumberTable