plugins {
    alias(libs.plugins.nowinmovie.android.library)
    alias(libs.plugins.kotlin.serialization)
}

android {
    namespace = "com.amirnlz.core.navigation"
}

dependencies {
    implementation(libs.androidx.compose.material.icons.core)
    implementation(libs.androidx.material3.android)
    implementation(libs.kotlinx.serialization.json)
}

