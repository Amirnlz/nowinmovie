import com.amirnlz.nowinmovie.libs
import com.android.build.gradle.LibraryExtension
import org.gradle.api.Plugin
import org.gradle.api.Project
import org.gradle.kotlin.dsl.apply
import org.gradle.kotlin.dsl.configure
import org.gradle.kotlin.dsl.dependencies

class AndroidFeatureConventionPlugin : Plugin<Project> {
  override fun apply(target: Project) {
    with(target) {
      apply(plugin = "nowinmovie.android.library")
      apply(plugin = "nowinmovie.hilt")
      apply(plugin = "org.jetbrains.kotlin.plugin.serialization")

      extensions.configure<LibraryExtension> {
        testOptions.animationsDisabled = true
      }

      dependencies {
        "implementation"(project(":core:designsystem"))
        "implementation"(project(":core:navigation"))

        "implementation"(libs.findLibrary("hilt.navigation.compose").get())
        "implementation"(libs.findLibrary("lifecycle.runtime.compose").get())
        "implementation"(libs.findLibrary("lifecycle.viewmodel.compose").get())
        "implementation"(libs.findLibrary("androidx.navigation.compose").get())
        "implementation"(libs.findLibrary("androidx.tracing.ktx").get())
        "implementation"(libs.findLibrary("kotlinx.serialization.json").get())

        "testImplementation"(libs.findLibrary("androidx.navigation.testing").get())
        "androidTestImplementation"(
          libs.findLibrary("androidx.lifecycle.runtimeTesting").get(),
        )
      }
    }
  }
}
