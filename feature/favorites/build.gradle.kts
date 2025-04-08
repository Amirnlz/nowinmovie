plugins {
    alias(libs.plugins.nowinmovie.android.feature)
    alias(libs.plugins.nowinmovie.android.library.compose)
    alias(libs.plugins.nowinmovie.hilt)
}

android {
    namespace = "com.amirnlz.feature.favorites"
}

dependencies {
    implementation(projects.core.domain.movie)
    implementation(libs.androidx.paging.compose)
}