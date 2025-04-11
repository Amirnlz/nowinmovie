plugins {
    alias(libs.plugins.nowinmovie.android.library)
    alias(libs.plugins.nowinmovie.android.library.compose)
}

android {
    namespace = "com.amirnlz.core.designsystem"

    defaultConfig {
        buildConfigField("String", "BASE_URL", "\"https://image.tmdb.org/t/p\";")
    }

}

dependencies {
    implementation(libs.coil.kt)
    implementation(libs.coil.network.okhttp)
    implementation(libs.coil.kt.compose)
    implementation(libs.coil.kt.svg)
    implementation(projects.core.domain.movie)
    implementation(libs.androidx.paging.compose)
  implementation(libs.android.youtube.player)
}
