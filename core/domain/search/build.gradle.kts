plugins {
    alias(libs.plugins.nowinmovie.android.library)
    alias(libs.plugins.nowinmovie.hilt)
}

android {
    namespace = "com.amirnlz.core.domain.search"
}

dependencies {
    implementation(libs.androidx.paging.runtime.ktx)
}