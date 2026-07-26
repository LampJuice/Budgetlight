// Top-level build file where you can add configuration options common to all sub-projects/modules.
plugins {
    alias(libs.plugins.android.application) apply false
    alias(libs.plugins.kotlin.compose) apply false
    alias(libs.plugins.ksp) apply false
    alias(libs.plugins.hilt) apply false
    alias(libs.plugins.detekt) apply false
    alias(libs.plugins.spotless) apply false

}

tasks.register("format") {
    group = "formatting"
    description = "Formats Kotlin code"

    dependsOn(
        ":app:spotlessApply"
    )
}

tasks.register("verify") {
    group = "verification"
    description = "Runs all checks"

    dependsOn(
        ":app:spotlessCheck",
        ":app:detekt",
        ":app:lintDebug",
        ":app:build",
    )
}
