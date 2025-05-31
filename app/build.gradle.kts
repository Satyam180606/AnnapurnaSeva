plugins {
    id("com.android.application")
    id("org.jetbrains.kotlin.android")
    id("org.jetbrains.kotlin.kapt")
    id("com.google.gms.google-services")
    id("kotlin-kapt")
}
android {
    namespace = "com.example.annapurnaseva"
    compileSdk = 35

    defaultConfig {
        applicationId = "com.example.annapurnaseva"
        minSdk = 23
        targetSdk = 35
        versionCode = 1
        versionName = "1.0"

        testInstrumentationRunner = "androidx.test.runner.AndroidJUnitRunner"

        javaCompileOptions {
            annotationProcessorOptions {
                arguments["room.schemaLocation"] = "$projectDir/schemas"
            }
        }
    }

    buildTypes {
        release {
            isMinifyEnabled = false
            proguardFiles(
                getDefaultProguardFile("proguard-android-optimize.txt"),
                "proguard-rules.pro"
            )
        }
    }
    compileOptions {
        sourceCompatibility = JavaVersion.VERSION_11
        targetCompatibility = JavaVersion.VERSION_11
    }
    kotlinOptions {
        jvmTarget = "11"
    }
}

dependencies {
    implementation(libs.androidx.appcompat.v171)
    implementation(libs.androidx.activity.ktx)
    implementation(libs.androidx.core.ktx.v1131)
    implementation(libs.androidx.lifecycle.runtime.ktx.v283)
    implementation(libs.androidx.lifecycle.viewmodel.ktx.v291)
    implementation(libs.androidx.lifecycle.livedata.ktx.v283)
    implementation(libs.material)
    implementation(platform(libs.firebase.bom))
    implementation(libs.firebase.analytics)
    implementation(libs.firebase.auth)
    implementation(libs.support.annotations) // Consider removing if not needed by an old library
    implementation(libs.androidx.lifecycle.extensions) // REMOVE THIS - Deprecated
    implementation(libs.ui) // Keep if using Jetpack Compose
    implementation(libs.androidx.annotation)
    implementation(libs.androidx.work.runtime)
    implementation(libs.firebase.database)

    // Room dependencies (consider updating version in libs.versions.toml)
    // val room_version = "2.5.1" // This variable isn't used if you use libs.versions.toml for Room versions
    implementation(libs.androidx.room.runtime)
    kapt(libs.androidx.room.compiler) // Or ksp(libs.androidx.room.compiler) if you migrate
    implementation(libs.androidx.room.ktx)
}