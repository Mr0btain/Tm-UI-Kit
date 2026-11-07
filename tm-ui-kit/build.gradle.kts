import org.jetbrains.kotlin.gradle.ExperimentalWasmDsl
import org.jetbrains.kotlin.gradle.dsl.JvmTarget

/*
 * The UI kit: theme, surfaces, buttons, fields, lists, dialogs, navigation and the component gallery, shared by
 * TicketMan and Tuners Toolkit. Package org.mr0btain.uikit
 */
plugins {
    alias(libs.plugins.kotlinMultiplatform)
    alias(libs.plugins.androidMultiplatformLibrary)
    alias(libs.plugins.composeMultiplatform)
    alias(libs.plugins.composeCompiler)
    `maven-publish`
}

kotlin {
    // JDK 21 (Gradle downloads it through foojay when it isn't installed), the same as the apps
    jvmToolchain(21)

    android {
        namespace = "org.mr0btain.uikit"
        compileSdk = libs.versions.android.compileSdk.get().toInt()
        minSdk = libs.versions.android.minSdk.get().toInt()

        compilerOptions {
            jvmTarget.set(JvmTarget.JVM_11)
        }

        androidResources {
            enable = true
        }
    }

    // iOS only builds on a Mac (it needs Xcode), so elsewhere the targets are left out
    if (System.getProperty("os.name").startsWith("Mac")) {
        iosArm64()
        iosSimulatorArm64()
    }

    jvm("desktop")

    @OptIn(ExperimentalWasmDsl::class)
    wasmJs {
        browser()
    }

    sourceSets {
        commonMain.dependencies {
            api(libs.runtime)
            api(libs.foundation)
            api(libs.ui)
            api(libs.material3)
            api(libs.components.resources)

            // TmLogTerminal shows its lines
            api(libs.tm.applogger)
            implementation(libs.kotlinx.datetime)

            // Frosted glass for the floating layers
            implementation(libs.haze)
            implementation(libs.haze.blur)
        }

        commonTest.dependencies {
            implementation(libs.kotlin.test)
        }

        getByName("desktopTest").dependencies {
            // KitGalleryRenderTest draws with Skia
            implementation(compose.desktop.currentOs)
        }
    }
}

// The fonts and icons ship with the library, under Res in org.mr0btain.uikit.resources
compose.resources {
    publicResClass = true
    packageOfResClass = "org.mr0btain.uikit.resources"
    generateResClass = always
}
