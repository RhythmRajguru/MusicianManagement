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

# Keep Firebase Auth and Google Play Services
-keep class com.google.firebase.** { *; }
-dontwarn com.google.firebase.**
-keep class com.google.android.gms.** { *; }
-dontwarn com.google.android.gms.**

# Keep necessary classes for Firebase
-keep class com.google.firebase.auth.** { *; }
-keep class com.google.android.gms.auth.api.signin.** { *; }
-keep class com.google.android.gms.common.api.** { *; }

# Keep Parcelable classes for RecyclerView and other libraries
-keepclassmembers class * implements android.os.Parcelable {
    public static final android.os.Parcelable$Creator *;
}

# AndroidX support libraries
-keep class androidx.appcompat.** { *; }
-dontwarn androidx.appcompat.**

-keep class androidx.recyclerview.** { *; }
-dontwarn androidx.recyclerview.**

# Material components (optional, but recommended)
-keep class com.google.android.material.** { *; }
-dontwarn com.google.android.material.**

# ConstraintLayout classes
-keep class androidx.constraintlayout.** { *; }
-dontwarn androidx.constraintlayout.**

# Prevent obfuscation of any code related to JUnit (for testing)
-dontwarn org.junit.**
-keep class org.junit.** { *; }
-keepclassmembers class org.junit.** { *; }

# Espresso testing framework (for Android testing)
-dontwarn androidx.test.espresso.**
-keep class androidx.test.espresso.** { *; }
-keepclassmembers class androidx.test.espresso.** { *; }

# Keep all lambda functions (if you are using Java 8 or later)
-keepclassmembers class * {
    public static void lambda$(...);
}
