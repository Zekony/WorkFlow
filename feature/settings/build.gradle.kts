@Suppress("DSL_SCOPE_VIOLATION") // TODO: Remove once KTIJ-19369 is fixed
plugins {
    alias(libs.plugins.com.android.library)
    alias(libs.plugins.org.jetbrains.kotlin.android)
    alias(libs.plugins.com.google.dagger)
    id("kotlin-kapt")
}

android {
    namespace = "com.finto.feature.settings"
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
    buildFeatures {
        compose = true
    }
    composeOptions {
        kotlinCompilerExtensionVersion = "1.5.0"
    }
}

dependencies {

    implementation(project(":domain:registration"))
    implementation(project(":domain:projects"))
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

    implementation(platform(libs.firebase.bom))
    implementation(libs.firebase.auth)
    implementation(libs.test.core)
    androidTestImplementation(platform(libs.compose.bom))
    testImplementation(libs.bundles.test.common)
    testImplementation(libs.bundles.test.local)
    androidTestImplementation(libs.bundles.test.common)
    androidTestImplementation(libs.bundles.test.android)
    kaptAndroidTest(libs.dagger.compiler)}