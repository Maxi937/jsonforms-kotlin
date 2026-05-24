import org.gradle.kotlin.dsl.withType
import org.jetbrains.kotlin.gradle.targets.js.KotlinJsPlugin
import org.jetbrains.kotlin.gradle.targets.js.nodejs.NodeJsEnvSpec
import org.jetbrains.kotlin.gradle.targets.js.nodejs.NodeJsPlugin
import org.jlleitschuh.gradle.ktlint.KtlintExtension
import kotlin.apply

plugins {
    alias(libs.plugins.android.application).apply(false)
    alias(libs.plugins.android.library).apply(false)
    alias(libs.plugins.jetbrains.compose).apply(false)
    alias(libs.plugins.jetbrains.compose.compiler).apply(false)
    alias(libs.plugins.jetbrains.kotlin.multiplatform).apply(false)
    alias(libs.plugins.jetbrains.kotlin.serialization).apply(false)
    alias(libs.plugins.jetbrains.kotlinx.binary.compatibility.validator).apply(false)
    alias(libs.plugins.ktlint).apply(false)
    alias(libs.plugins.vanniktech.maven.publish).apply(false)
    alias(libs.plugins.jetbrains.dokka).apply(true)
}

plugins.withType<NodeJsPlugin> {
    the<NodeJsEnvSpec>().apply {
        version.set("18.20.4")
        download.set(true)
    }
}

subprojects {
    if (pluginManager.hasPlugin("org.jlleitschuh.gradle.ktlint")) {
        configure<KtlintExtension> {
            debug.set(false)
            version.set("0.47.1")
            verbose.set(true)
            android.set(false)
            outputToConsole.set(true)
            ignoreFailures.set(false)
            enableExperimentalRules.set(true)
            filter {
                exclude { projectDir.toURI().relativize(it.file.toURI()).path.contains("/generated/") }
                exclude { it.file.path.contains("/build/") }
                include("**/kotlin/**")
            }
        }
    }
}
