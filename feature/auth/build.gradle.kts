plugins {
    alias(libs.plugins.nowinmovie.android.feature)
    alias(libs.plugins.nowinmovie.android.library.compose)
}

android {
    namespace = "com.amirnlz.feature.auth"
}

dependencies {
    implementation(projects.core.navigation)
    implementation(projects.core.domain.auth)
}