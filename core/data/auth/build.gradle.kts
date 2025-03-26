plugins {
    alias(libs.plugins.nowinmovie.android.library)
    alias(libs.plugins.nowinmovie.hilt)
}

android {
    namespace = "com.amirnlz.core.data.auth"

}

dependencies {
    implementation(projects.core.network)
    implementation(projects.core.secureStorage)
    implementation(projects.core.domain.auth)

}