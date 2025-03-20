package com.amirnlz.nowinmovie


/**
 * This is shared between :app and :benchmarks module to provide configurations type safety.
 */
enum class NimBuildType(val applicationIdSuffix: String? = null) {
    DEBUG(".debug"),
    RELEASE,
}