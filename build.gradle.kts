@Suppress("DSL_SCOPE_VIOLATION") // TODO: Remove once KTIJ-19369 is fixed
plugins {
    alias(libs.plugins.com.android.application) apply false
    alias(libs.plugins.org.jetbrains.kotlin.android) apply false
    alias(libs.plugins.com.android.library) apply false
    alias(libs.plugins.com.google.dagger) apply false
    alias(libs.plugins.com.google.gms) apply false
}
true // Needed to make the Suppress annotation work for the plugins block