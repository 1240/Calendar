# Add project specific ProGuard rules here.
# By default, the flags in this file are appended to flags specified
# in /Users/pisarenkomb/Library/Android/sdk.GDQEhf/tools/proguard/proguard-android.txt
# You can edit the include path and order by changing the proguardFiles
# directive in build.gradle.
#
# For more details, see
#   http://developer.android.com/guide/developing/tools/proguard.html

# Add any project specific keep options here:

# If your project uses WebView with JS, uncomment the following
# and specify the fully qualified class name to the JavaScript interface
# class:
#-keepclassmembers class fqcn.of.javascript.interface.for.webview {
#   public *;
#}
-dontwarn org.xmlpull.v1.**
-keep class org.xmlpull.v1.** { *; }

-dontwarn com.bea.xml.stream.**
-dontwarn org.simpleframework.xml.stream.**
-keep class org.simpleframework.xml.**{ *; }
-keepclassmembers,allowobfuscation class * {
    @org.simpleframework.xml.* <fields>;
    @org.simpleframework.xml.* <init>(...);
}

-keepattributes *Annotation*
-keepclassmembers enum androidx.lifecycle.Lifecycle$Event {
    <fields>;
}
-keep class * implements androidx.lifecycle.LifecycleObserver {
}
-keep class * implements androidx.lifecycle.GeneratedAdapter {
    <init>(...);
}
-keepclassmembers class ** {
    @androidx.lifecycle.OnLifecycleEvent *;
}
-keep interface org.simpleframework.xml.core.Label {
   public *;
}
-keep class * implements org.simpleframework.xml.core.Label {
   public *;
}
-keep interface org.simpleframework.xml.core.Parameter {
   public *;
}
-keep class * implements org.simpleframework.xml.core.Parameter {
   public *;
}
-keep interface org.simpleframework.xml.core.Extractor {
   public *;
}
-keep class * implements org.simpleframework.xml.core.Extractor {
   public *;
}
-keep class * implements org.simpleframework.xml.core.Extractor {
   public *;
}

-keep class com.l24o.workcalendar.data.rest.** { *; }
-keep class kotlinx.** { *; }
-keepnames class kotlinx.** { *; }
-keep class kotlin.** { *; }
-keepnames class kotlin.** { *; }
-keep class io.reactivex.** { *; }
-keepnames class io.reactivex.** { *; }