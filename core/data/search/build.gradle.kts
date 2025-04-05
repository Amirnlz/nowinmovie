plugins {
    alias(libs.plugins.nowinmovie.android.library)
    alias(libs.plugins.nowinmovie.hilt)
}

android {
    namespace = "com.amirnlz.core.data.search"
}

dependencies {
    implementation(projects.core.network)
    implementation(projects.core.domain.search)
    implementation(libs.androidx.paging.runtime.ktx)
}