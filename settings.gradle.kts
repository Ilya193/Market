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

rootProject.name = "Market"
include(":app")
include(":feature-menu")
include(":common")
include(":data:meals")
include(":data:basket")
include(":core:database")
include(":core:network")
include(":feature-basket")
