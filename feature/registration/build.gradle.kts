@Suppress("DSL_SCOPE_VIOLATION") // TODO: Remove once KTIJ-19369 is fixed
plugins {
    alias(libs.plugins.com.android.library)
    alias(libs.plugins.org.jetbrains.kotlin.android)
    alias(libs.plugins.com.google.dagger)
    id("kotlin-kapt")
}

android {
    namespace = "com.zekony.feature.registration"
    compileSdk = 34

    defaultConfig {
        minSdk = 24

        testInstrumentationRunner = "androidx.test.runner.AndroidJUnitRunner"
        consumerProguardFiles("consumer-rules.pro")
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
        sourceCompatibility = JavaVersion.VERSION_17
        targetCompatibility = JavaVersion.VERSION_17
    }
    kotlinOptions {
        jvmTarget = "17"
    }
    buildFeatures {
        compose = true
    }
    composeOptions {
        kotlinCompilerExtensionVersion = "1.5.0"
    }
}

dependencies {

    implementation(project(":resources"))
    implementation(project(":utility"))

    implementation(libs.bundles.core)
    implementation(platform(libs.compose.bom))
    implementation(libs.bundles.compose)
    implementation(libs.compose.icons)

    implementation(libs.androidx.navigation)
    implementation(libs.bundles.orbit)

    implementation(libs.hilt)
    kapt(libs.hilt.compiler)
    implementation(libs.hilt.androidx.compose)
    kapt(libs.hilt.androidx.compiler)

    implementation(libs.firebase.auth)
    implementation(libs.android.gms)

    androidTestImplementation(platform(libs.compose.bom))
    implementation(libs.bundles.test)
}