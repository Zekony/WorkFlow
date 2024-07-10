@Suppress("DSL_SCOPE_VIOLATION") // TODO: Remove once KTIJ-19369 is fixed
plugins {
    alias(libs.plugins.com.android.application)
    alias(libs.plugins.org.jetbrains.kotlin.android)
    alias(libs.plugins.com.google.dagger)
    alias(libs.plugins.com.google.gms)
    id("kotlin-kapt")
}

android {
    namespace = "com.finto.workflow"
    compileSdk = 34

    defaultConfig {
        applicationId = "com.finto.workflow"
        minSdk = 24
        targetSdk = 34
        versionCode = 1
        versionName = "1.0"

        testInstrumentationRunner = "androidx.test.runner.AndroidJUnitRunner"
        vectorDrawables {
            useSupportLibrary = true
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
        isCoreLibraryDesugaringEnabled = true
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
    packaging {
        resources {
            excludes += "/META-INF/{AL2.0,LGPL2.1}"
        }
    }
    hilt {
        enableAggregatingTask = true
    }
}

dependencies {

    coreLibraryDesugaring(libs.desugarring)

    implementation(project(":navigation"))
    implementation(project(":data:registration"))
    implementation(project(":data:projects"))
    implementation(project(":domain:registration"))
    implementation(project(":domain:projects"))
    implementation(project(":feature:home"))
    implementation(project(":feature:registration"))
    implementation(project(":feature:taskDetails"))
    implementation(project(":feature:createProject"))

    implementation(libs.bundles.core)

    implementation(platform(libs.compose.bom))
    implementation(libs.bundles.compose)

    implementation(libs.hilt)
    kapt(libs.hilt.compiler)

    implementation(libs.firebase.auth)
    implementation(libs.firebase.database)
    implementation(libs.test.core)
    androidTestImplementation(platform(libs.compose.bom))
    testImplementation(libs.bundles.test.common)
    testImplementation(libs.bundles.test.local)
    androidTestImplementation(libs.bundles.test.common)
    androidTestImplementation(libs.bundles.test.android)
    kaptAndroidTest(libs.dagger.compiler)

}