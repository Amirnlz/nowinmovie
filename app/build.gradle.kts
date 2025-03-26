plugins {
    alias(libs.plugins.nowinmovie.android.application.compose)
    alias(libs.plugins.nowinmovie.android.application)
    alias(libs.plugins.nowinmovie.android.application.firebase)
    alias(libs.plugins.nowinmovie.hilt)
    alias(libs.plugins.kotlin.serialization)
}

android {
    namespace = "com.amirnlz.nowinmovie"
}

dependencies {
    implementation(projects.core.navigation)
    implementation(libs.kotlinx.serialization.json)

    implementation(libs.androidx.core.ktx)
    implementation(libs.androidx.lifecycle.runtime.ktx)
}