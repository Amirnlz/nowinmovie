plugins {
    alias(libs.plugins.nowinmovie.android.library)
    alias(libs.plugins.nowinmovie.hilt)
    alias(libs.plugins.nowinmovie.android.room)
}

android {
    namespace = "com.amirnlz.core.database"
}

dependencies {
    implementation(libs.converter.gson)
    implementation(libs.room.paging)

}