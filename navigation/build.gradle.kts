@Suppress("DSL_SCOPE_VIOLATION") // TODO: Remove once KTIJ-19369 is fixed
plugins {
    alias(libs.plugins.com.android.library)
    alias(libs.plugins.org.jetbrains.kotlin.android)
}

android {
    namespace = "com.finto.navigation"
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

    implementation(project(":feature:preview"))
    implementation(project(":feature:home"))
    implementation(project(":feature:registration"))
    implementation(project(":feature:taskDetails"))
    implementation(project(":feature:createProject"))
    implementation(project(":resources"))

    implementation(libs.bundles.core)
    implementation(platform(libs.compose.bom))
    implementation(libs.bundles.compose)
    implementation(libs.androidx.navigation)

    androidTestImplementation(platform(libs.compose.bom))
    implementation(libs.bundles.test)
}