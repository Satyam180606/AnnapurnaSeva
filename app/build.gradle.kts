plugins {
    id("com.android.application")
    id("org.jetbrains.kotlin.android") // REQUIRED for Kotlin and kapt
    id("org.jetbrains.kotlin.kapt")    // REQUIRED for kapt
    // If you were to use KSP instead of kapt:
    // id("com.google.devtools.ksp") version "1.9.22-1.0.17" // Or the latest KSP version
}

android {
    namespace = "com.example.annapurnaseva" // Make sure this matches your package
    compileSdk = 34 // Or your target SDK

    defaultConfig {
        applicationId = "com.example.annapurnaseva"
        minSdk = 21 // Or your min SDK
        targetSdk = 34
        versionCode = 1
        versionName = "1.0"

        testInstrumentationRunner = "androidx.test.runner.AndroidJUnitRunner"

        // For Room, if you need to provide a schema location (optional but good practice)
        // If using kapt, you might need to configure it differently or ensure it works with javaCompileOptions
        javaCompileOptions {
            annotationProcessorOptions {
                arguments["room.schemaLocation"] = "$projectDir/schemas"
            }
        }
        // For kapt with Room schema location (alternative if above doesn't work with kapt)
        // kapt {
        //     arguments {
        //         arg("room.schemaLocation", "$projectDir/schemas")
        //     }
        // }
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
    kotlinOptions { // REQUIRED for Kotlin
        jvmTarget = "11"
    }
}

dependencies {
    // FROM YOUR PROJECT INFORMATION (Likely to be present or similar)
    implementation("androidx.appcompat:appcompat:1.6.1")
    implementation("androidx.activity:activity:1.8.0") // Consider activity-ktx if using Kotlin UI features
    implementation("androidx.core:core-ktx:1.13.1")
    implementation("androidx.lifecycle:lifecycle-runtime-ktx:2.8.3") // lifecycle-runtime-ktx is often preferred with Kotlin
    implementation("androidx.lifecycle:lifecycle-viewmodel-ktx:2.8.3") // lifecycle-viewmodel-ktx
    implementation("androidx.lifecycle:lifecycle-livedata-ktx:2.8.3") // lifecycle-livedata-ktx
    implementation(libs.material) // From your toml file

    // Other existing dependencies you might have (e.g., ConstraintLayout)
    // implementation("androidx.constraintlayout:constraintlayout:2.1.4")


    // ✅ Room Persistence Library
    val room_version = "2.5.1" // Using val for Kotlin
    implementation("androidx.room:room-runtime:$room_version")
    kapt("androidx.room:room-compiler:$room_version") // As per your request
    implementation("androidx.room:room-ktx:$room_version") // Kotlin Extensions and Coroutines support

    // Optional - Test helpers
    // testImplementation("androidx.room:room-testing:$room_version")
    // androidTestImplementation("androidx.room:room-testing:$room_version")

    // Other testing dependencies
    // testImplementation("junit:junit:4.13.2")
    // androidTestImplementation("androidx.test.ext:junit:1.1.5")
    // androidTestImplementation("androidx.test.espresso:espresso-core:3.5.1")
}


