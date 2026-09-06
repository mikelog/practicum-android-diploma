import org.jetbrains.kotlin.gradle.dsl.JvmTarget

plugins {
    id("com.android.application")
    id("org.jetbrains.kotlin.android")
    id("org.jetbrains.kotlin.plugin.compose")
    id("com.google.devtools.ksp")
    id("ru.practicum.android.diploma.plugins.developproperties")
}

android {
    namespace = "ru.practicum.android.diploma"
    compileSdk = libs.versions.compileSdk.get().toInt()

    defaultConfig {
        applicationId = "ru.practicum.android.diploma"
        minSdk = libs.versions.minSdk.get().toInt()
        targetSdk = libs.versions.targetSdk.get().toInt()
        versionCode = 1
        versionName = "1.0"

        testInstrumentationRunner = "androidx.test.runner.AndroidJUnitRunner"

        buildConfigField(type = "String", name = "API_ACCESS_TOKEN", value = "\"${developProperties.apiAccessToken}\"")
    }

    buildTypes {
        release {
            isMinifyEnabled = false
            proguardFiles(getDefaultProguardFile("proguard-android-optimize.txt"), "proguard-rules.pro")
        }
    }
    compileOptions {
        sourceCompatibility = JavaVersion.VERSION_17
        targetCompatibility = JavaVersion.VERSION_17
    }
    buildFeatures {
        buildConfig = true
        compose = true
    }
}

kotlin {
    compilerOptions {
        jvmTarget = JvmTarget.JVM_17
    }
}

dependencies {
    implementation(libs.core.ktx)
    implementation(libs.appcompat)

    // UI layer libraries
    implementation(libs.material)
    implementation(libs.constraintlayout)

    // Compose
    implementation(platform(libs.compose.bom))
    implementation(libs.bundles.compose)
    debugImplementation(libs.compose.uiTooling)

    // Networking
    implementation(libs.bundles.network)
    implementation(libs.network.gson)

    // Database
    implementation(libs.bundles.database)
    ksp(libs.database.roomCompiler)

    // DI
    implementation(libs.di.koinCore)
    implementation(libs.di.koinAndroid)
    implementation(libs.di.koinCompose)

    // Images
    implementation(libs.images.coilCompose)

    // Navigation
    implementation(libs.navigation.fragment)
    implementation(libs.navigation.ui)

    // Coroutines
    implementation(libs.bundles.coroutines)

    // Lifecycle
    implementation(libs.bundles.lifecycle)
    implementation(libs.fragment.ktx)

    testImplementation(libs.junit4)
    androidTestImplementation(libs.junit.ext)
    androidTestImplementation(libs.espresso.core)
}
