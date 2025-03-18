package com.amirnlz.nowinmovie


import org.gradle.api.JavaVersion
import org.jetbrains.kotlin.gradle.dsl.JvmTarget

object Config {
    val android = AndroidConfig(
        minSdk = 21,
        targetSdk = 35,
        compileSdk = 35,
        applicationId = "com.amirnlz.nowinmovie",
        versionCode = 1,
        versionName = "1.0.0",
        dimension = "app",
    )
    val jvm = JvmConfig(
        javaVersion = JavaVersion.VERSION_17,
        kotlinJvm = JavaVersion.VERSION_17.toString(),
        jvmTarget = JvmTarget.JVM_17,
        freeCompilerArgs = listOf("-Xopt-in=kotlin.RequiresOptIn"),
    )
}

data class AndroidConfig(
    val minSdk: Int,
    val targetSdk: Int,
    val compileSdk: Int,
    val applicationId: String,
    val versionCode: Int,
    val versionName: String,
    val dimension: String,
)

data class JvmConfig(
    val javaVersion: JavaVersion,
    val kotlinJvm: String,
    val jvmTarget: JvmTarget,
    val freeCompilerArgs: List<String>,
)