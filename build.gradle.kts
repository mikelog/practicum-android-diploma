// Top-level build file where you can add configuration options common to all sub-projects/modules.
plugins {
    id("com.android.application") version "8.12.0" apply false
    id("org.jetbrains.kotlin.android") version "2.3.10" apply false
    id("org.jetbrains.kotlin.plugin.compose") version "2.3.10" apply false
    id("com.google.devtools.ksp") version "2.3.11" apply false
    // Держать в синхроне с versions.navigation в gradle/libs.versions.toml
    id("androidx.navigation.safeargs.kotlin") version "2.8.5" apply false
    id("convention.detekt")
}
