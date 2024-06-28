@Suppress("DSL_SCOPE_VIOLATION") // TODO: Remove once KTIJ-19369 is fixed
plugins {
    alias(libs.plugins.com.android.library)
    alias(libs.plugins.org.jetbrains.kotlin.android)
    alias(libs.plugins.com.google.dagger)
    id("kotlin-kapt")
}

android {
    namespace = "com.finto.data.registration"
    compileSdk = 34

    defaultConfig {
        minSdk = 24

        testInstrumentationRunner = "androidx.test.runner.AndroidJUnitRunner"
        consumerProguardFiles("consumer-rules.pro")
    }

    compileOptions {
        sourceCompatibility = JavaVersion.VERSION_17
        targetCompatibility = JavaVersion.VERSION_17
    }
    kotlinOptions {
        jvmTarget = "17"
    }
}

dependencies {
    implementation(project(":domain:registration"))
    implementation(project(":domain:projects"))
    implementation(project(":utility"))
    implementation(project(":resources"))

    implementation(libs.bundles.core)

    implementation(libs.hilt)
    kapt(libs.hilt.compiler)
    implementation(libs.hilt.androidx.compose)
    kapt(libs.hilt.androidx.compiler)

    implementation(platform(libs.firebase.bom))
    implementation(libs.firebase.auth)
    implementation(libs.firebase.database)

    implementation(libs.androidx.credentials)
    implementation(libs.androidx.credentials.auth)
    implementation(libs.googleid)
}