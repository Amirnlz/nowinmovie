plugins {
    alias(libs.plugins.nowinmovie.android.feature)
    alias(libs.plugins.nowinmovie.android.library.compose)
    alias(libs.plugins.nowinmovie.hilt)
}

android {
    namespace = "com.amirnlz.feature.search"
}

dependencies {
    implementation(projects.core.domain.search)
    implementation(projects.core.navigation)
}