plugins {
    alias(libs.plugins.nowinmovie.android.feature)
}

android {
    namespace = "com.amirnlz.core.navigation"
}

dependencies {
    implementation(libs.androidx.compose.material.icons.core)
    implementation(libs.androidx.material3.android)
}

