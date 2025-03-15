import com.amirnlz.nowinmovie.androidGradle
import com.amirnlz.nowinmovie.configureKotlinAndroid
import com.amirnlz.nowinmovie.disableUnnecessaryAndroidTests
import com.android.build.api.variant.LibraryAndroidComponentsExtension
import org.gradle.api.Plugin
import org.gradle.api.Project
import org.gradle.kotlin.dsl.apply
import org.gradle.kotlin.dsl.configure

class AndroidLibraryConventionPlugin : Plugin<Project> {
    override fun apply(target: Project) {
        with(target) {
            apply(plugin = "com.android.library")
            apply(plugin = "org.jetbrains.kotlin.android")

            extensions.configure<LibraryAndroidComponentsExtension> {
                disableUnnecessaryAndroidTests(target)
            }

            androidGradle {
                configureKotlinAndroid(this)
//                config flavors
            }
        }
    }
}