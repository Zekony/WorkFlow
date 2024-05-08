pluginManagement {
    repositories {
        google()
        mavenCentral()
        gradlePluginPortal()
    }
}
dependencyResolutionManagement {
    repositoriesMode.set(RepositoriesMode.FAIL_ON_PROJECT_REPOS)
    repositories {
        google()
        mavenCentral()
    }
}

rootProject.name = "WorkFlow"
include(":app")
include(":navigation")
include(":feature:preview")
include(":utility")
include(":resources")
include(":feature:home")
include(":feature:registration")
