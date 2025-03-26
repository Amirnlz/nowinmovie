plugins {
    alias(libs.plugins.nowinmovie.android.library)
    alias(libs.plugins.nowinmovie.hilt)
}

android {
    namespace = "com.amirnlz.core.secure_storage"
}
dependencies {
    implementation(libs.androidx.security.crypto)
}
