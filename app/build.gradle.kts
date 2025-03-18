plugins {
    alias(libs.plugins.kotlin.android)
    alias(libs.plugins.kotlin.compose)
    alias(libs.plugins.nowinmovie.android.application.compose)
    alias(libs.plugins.nowinmovie.android.application)
    alias(libs.plugins.nowinmovie.hilt)
}

android {
    namespace = "com.amirnlz.nowinmovie"
}

dependencies {
    implementation(libs.androidx.core.ktx)
    implementation(libs.androidx.lifecycle.runtime.ktx)
}