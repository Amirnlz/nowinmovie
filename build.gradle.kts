// Top-level build file where you can add configuration options common to all sub-projects/modules.
plugins {
    alias(libs.plugins.android.application) apply false
    alias(libs.plugins.android.library) apply false
    alias(libs.plugins.kotlin.android) apply false
    alias(libs.plugins.kotlin.compose) apply false
    alias(libs.plugins.kotlin.serialization) apply false
    alias(libs.plugins.ksp) apply false
    alias(libs.plugins.room) apply false
    alias(libs.plugins.hilt) apply false
    alias(libs.plugins.firebase.crashlytics) apply false
    alias(libs.plugins.firebase.perf) apply false
    alias(libs.plugins.gms) apply false
  alias(libs.plugins.spotless)
}

spotless {
  kotlin {
    target("**/*.kt")
    trimTrailingWhitespace()
    leadingTabsToSpaces()
    endWithNewline()
    ktlint("0.48.0")
      .setEditorConfigPath("$projectDir/.editorconfig")
      .editorConfigOverride(
        mapOf(
          "indent_size" to 2,
          "ktlint_code_style" to "intellij_idea",
        ),
      )
  }
  kotlinGradle {
    target("*.gradle.kts")
    ktlint()
  }
  format("misc") {
    target("**/*.gradle", "**/*.md", "**/.gitignore")
    leadingTabsToSpaces()
    trimTrailingWhitespace()
    endWithNewline()
  }
  format("xml") {
    target("**/*.xml")
    leadingTabsToSpaces()
    trimTrailingWhitespace()
    endWithNewline()
  }
}
