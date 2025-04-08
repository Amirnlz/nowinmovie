plugins {
    alias(libs.plugins.nowinmovie.android.feature)
    alias(libs.plugins.nowinmovie.android.library.compose)
    alias(libs.plugins.nowinmovie.hilt)
}

android {
    namespace = "com.amirnlz.feature.movie_detail"

}

dependencies {
    implementation(projects.core.domain.movie)
}