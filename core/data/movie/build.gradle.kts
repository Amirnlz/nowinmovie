plugins {
    alias(libs.plugins.nowinmovie.android.library)
    alias(libs.plugins.nowinmovie.hilt)
}

android {
    namespace = "com.amirnlz.core.data.movie"
}

dependencies {
    implementation(projects.core.network)
    implementation(projects.core.domain.movie)
    implementation(libs.androidx.paging.runtime.ktx)
}