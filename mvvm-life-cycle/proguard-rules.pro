# Add project specific ProGuard rules here.
# By default, the flags in this file are appended to flags specified
# in /Volumes/iMac/workspace/android/bundle/sdk/tools/proguard/proguard-android.txt
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

# butterknife http://jakewharton.github.io/butterknife/
-keep class butterknife.** { *; }
-dontwarn butterknife.internal.**
-keep class **$$ViewBinder { *; }
-keepclasseswithmembernames class * {
    @butterknife.* <fields>;
}
-keepclasseswithmembernames class * {
    @butterknife.* <methods>;
}

# guava
-dontwarn sun.misc.Unsafe
-dontwarn com.google.common.collect.MinMaxPriorityQueue
-keepclasseswithmembers public class * {
    public static void main(java.lang.String[]);
}

# retrolambda https://github.com/evant/gradle-retrolambda
-dontwarn java.lang.invoke.*

# rxjava
-keep class rx.internal.util.unsafe.** { *; }

# support design
-dontwarn android.support.design.**
-keep interface android.support.design.** { *; }
-keep class android.support.design.** { *; }
-keep class android.support.v7.widget.** { *; }