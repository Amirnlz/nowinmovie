plugins {
    alias(libs.plugins.nowinmovie.android.library)
}

android {
    namespace = "com.amirnlz.core.data.network"

    defaultConfig {
        buildConfigField("String", "BASE_URL", "https://api.themoviedb.org")
    }

    buildFeatures {
        buildConfig = true
    }
}

dependencies {
    implementation(libs.retrofit)
    implementation(libs.converter.gson)
    implementation(libs.logging.interceptor)
    implementation(libs.okhttp)
}