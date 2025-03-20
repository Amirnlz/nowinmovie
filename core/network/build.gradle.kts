plugins {
    alias(libs.plugins.nowinmovie.android.library)
    alias(libs.plugins.nowinmovie.hilt)
}

android {
    namespace = "com.amirnlz.core.network"

    defaultConfig {
        buildConfigField("String", "BASE_URL", "\"https://api.themoviedb.org\";")
    }
}

dependencies {
    implementation(libs.retrofit)
    implementation(libs.converter.gson)
    implementation(libs.logging.interceptor)
    implementation(libs.okhttp)
}