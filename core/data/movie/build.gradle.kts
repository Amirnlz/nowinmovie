plugins {
    alias(libs.plugins.nowinmovie.android.library)
}

android {
    namespace = "com.amirnlz.core.data.movie"
}

dependencies {
    implementation(libs.retrofit)
    implementation(libs.converter.gson)
    implementation(projects.core.data.network)
    implementation(projects.core.domain.movie)
}