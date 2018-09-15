# Add project specific ProGuard rules here.
# By default, the flags in this file are appended to flags specified
# in D:\Softwares\adt-bundle-windows\sdk/tools/proguard/proguard-android.txt
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
-keep class okhttp3.** { *; }

# keep everything in this package from being renamed only
-keepnames class okhttp3.** { *; }
# keep everything in this package from being removed or renamed
-keep class java.** { *; }

# keep everything in this package from being renamed only
-keepnames class java.**
# keep everything in this package from being removed or renamed
-keep class org.** { *; }

# keep everything in this package from being renamed only
-keepnames class org.** { *; }
# keep everything in this package from being removed or renamed
-keep class org.apache.** { *; }

# keep everything in this package from being renamed only
-keepnames class org.apache.** { *; }
# keep everything in this package from being removed or renamed
-keep class retrofit2.** { *; }

# keep everything in this package from being renamed only
-keepnames class retrofit2.** { *; }