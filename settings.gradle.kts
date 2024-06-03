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
include(":utility")
include(":resources")
include(":settings")

include(":domain:projects")
include(":domain:registration")

include(":data:projects")
include(":data:registration")

include(":feature:preview")
include(":feature:registration")
include(":feature:home")
include(":feature:taskDetails")
include(":feature:createProject")
