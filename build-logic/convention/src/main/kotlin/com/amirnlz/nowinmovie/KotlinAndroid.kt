package com.amirnlz.nowinmovie

import com.android.build.api.dsl.CommonExtension
import org.gradle.api.Project
import org.gradle.api.plugins.JavaPluginExtension
import org.gradle.kotlin.dsl.configure
import org.gradle.kotlin.dsl.provideDelegate
import org.jetbrains.kotlin.gradle.dsl.KotlinAndroidProjectExtension
import org.jetbrains.kotlin.gradle.dsl.KotlinBaseExtension
import org.jetbrains.kotlin.gradle.dsl.KotlinJvmProjectExtension

/**
 * Configure base Kotlin with Android options
 */
internal fun Project.configureKotlinAndroid(
  commonExtension: CommonExtension<*, *, *, *, *, *>,
) {
  commonExtension.apply {
    compileSdk = Config.android.compileSdk

    defaultConfig {
      minSdk = Config.android.minSdk
    }

    buildFeatures {
      buildConfig = true
    }
    compileOptions {
      sourceCompatibility = Config.jvm.javaVersion
      targetCompatibility = Config.jvm.javaVersion
//            isCoreLibraryDesugaringEnabled = true
    }
  }

  configureKotlin<KotlinAndroidProjectExtension>()

//    dependencies {
//        "coreLibraryDesugaring"(libs.findLibrary("android.desugarJdkLibs").get())
//    }
}

/**
 * Configure base Kotlin options for JVM (non-Android)
 */
internal fun Project.configureKotlinJvm() {
  extensions.configure<JavaPluginExtension> {
    sourceCompatibility = Config.jvm.javaVersion
    targetCompatibility = Config.jvm.javaVersion
  }

  configureKotlin<KotlinJvmProjectExtension>()
}

/**
 * Configure base Kotlin options
 */
private inline fun <reified T : KotlinBaseExtension> Project.configureKotlin() = configure<T> {
  val warningsAsErrors: String? by project
  when (this) {
    is KotlinAndroidProjectExtension -> compilerOptions
    is KotlinJvmProjectExtension -> compilerOptions
    else -> TODO("Unsupported project extension $this ${T::class}")
  }.apply {
    jvmTarget = Config.jvm.jvmTarget
    allWarningsAsErrors = warningsAsErrors.toBoolean()
    freeCompilerArgs.add(
      // Enable experimental coroutines APIs, including Flow
      "-opt-in=kotlinx.coroutines.ExperimentalCoroutinesApi",
    )
    freeCompilerArgs.add(
      "-Xconsistent-data-class-copy-visibility",
    )
  }
}
