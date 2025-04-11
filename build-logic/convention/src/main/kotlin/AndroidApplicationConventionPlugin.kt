import com.amirnlz.nowinmovie.Config
import com.amirnlz.nowinmovie.configureFlavors
import com.amirnlz.nowinmovie.configureKotlinAndroid
import com.amirnlz.nowinmovie.libs
import com.android.build.api.dsl.ApplicationExtension
import org.gradle.api.Plugin
import org.gradle.api.Project
import org.gradle.kotlin.dsl.configure
import org.gradle.kotlin.dsl.dependencies

class AndroidApplicationConventionPlugin : Plugin<Project> {
  override fun apply(target: Project) {
    with(target) {
      pluginManager.apply {
        apply("com.android.application")
        apply("org.jetbrains.kotlin.android")
      }

      extensions.configure<ApplicationExtension> {
        configureKotlinAndroid(this)
        defaultConfig.apply {
          defaultConfig.targetSdk = Config.android.targetSdk
          applicationId = Config.android.applicationId
          versionCode = Config.android.versionCode
          versionName = Config.android.versionName
          namespace = Config.android.applicationId
          testInstrumentationRunner = "androidx.test.runner.AndroidJUnitRunner"
        }
        testOptions.animationsDisabled = true
        configureFlavors(this)
      }
      dependencies {
        add(
          "implementation",
          libs.findLibrary("hilt.navigation.compose").get(),
        )
        val subprojects = project
          .rootProject
          .subprojects

        subprojects.filter { it.path.startsWith(":core:domain:", false) }
          .forEach { add("implementation", project(it.path)) }

        subprojects.filter { it.path.startsWith(":core:data:", false) }
          .forEach { add("implementation", project(it.path)) }

        subprojects.filter { it.path.startsWith(":feature:", false) }
          .forEach { add("implementation", project(it.path)) }
      }
    }
  }
}
