plugins {
    id("com.android.application")
    kotlin("android")
    kotlin("android.extensions")
    id("dagger.hilt.android.plugin")
    kotlin("kapt")
}
android {
    compileSdk = 31
    buildToolsVersion = "30.0.3"

    defaultConfig {
        applicationId = "com.simple.mathgame"
        minSdk = 21
        targetSdk = 31
        versionCode = 2
        versionName = "2.0"
        vectorDrawables {
            useSupportLibrary = true
        }
    }
    buildFeatures {
        compose = true
    }
    kotlinOptions {
        jvmTarget = JavaVersion.VERSION_1_8.toString()
    }
    androidExtensions {
        isExperimental = true
    }

    buildTypes {
        getByName("release") {
            isMinifyEnabled = true
            proguardFiles(
                getDefaultProguardFile("proguard-android-optimize.txt"),
                "proguard-rules.pro"
            )
        }
    }
    composeOptions {
        kotlinCompilerExtensionVersion = "1.0.4"
    }
    compileOptions {
        sourceCompatibility = JavaVersion.VERSION_1_8
        targetCompatibility = JavaVersion.VERSION_1_8
    }
    packagingOptions {
        resources {
//            excludes += "/META-INF/{AL2.0,LGPL2.1}"
        }
    }
}
dependencies {
    implementation(fileTree("dir" to "libs", "include" to listOf("*.jar")))
    implementation("androidx.appcompat:appcompat:1.3.1")
    implementation("com.google.dagger:hilt-android:2.40")
    kapt("com.google.dagger:hilt-android-compiler:2.40")

    implementation("androidx.compose.ui:ui:1.0.4")
    // Tooling support (Previews, etc.)
    implementation("androidx.compose.ui:ui-tooling:1.0.4")
    // Foundation (Border, Background, Box, Image, Scroll, shapes, animations, etc.)
    implementation("androidx.compose.foundation:foundation:1.0.4")
    implementation("androidx.compose.compiler:compiler:1.0.4")
    // Material Design
    implementation("androidx.compose.material:material:1.0.4")

    implementation("com.google.android.material:material:1.4.0")
    implementation("androidx.compose.material3:material3:1.0.0-alpha01")
    // Material design icons
    implementation("androidx.activity:activity-ktx:1.4.0")

    implementation("androidx.activity:activity-compose:1.4.0")

    implementation("androidx.fragment:fragment-ktx:1.3.6")

//    implementation("androidx.compose.material:material-icons-core:1.0.4")
//    implementation("androidx.compose.material:material-icons-extended:1.0.4")
    // Integration with observables
    implementation("androidx.compose.runtime:runtime-livedata:1.0.4")
    implementation("androidx.compose.runtime:runtime-rxjava2:1.0.4")
    implementation("androidx.constraintlayout:constraintlayout-compose:1.0.0-rc01")

    implementation("androidx.hilt:hilt-lifecycle-viewmodel:1.0.0-alpha03")
    implementation("androidx.hilt:hilt-navigation-fragment:1.0.0")

    kapt("androidx.hilt:hilt-compiler:1.0.0")
//    implementation("com.airbnb.android:lottie-compose:4.2.0")

    implementation("androidx.room:room-runtime:2.3.0")
    kapt("androidx.room:room-compiler:2.3.0")
}

// Allow references to generated code
kapt {
    correctErrorTypes = true
}