/*
 * A desktop window showing the kit's gallery, for working on the kit by itself: ./gradlew :gallery:run
 */
plugins {
    alias(libs.plugins.kotlinJvm)
    alias(libs.plugins.composeMultiplatform)
    alias(libs.plugins.composeCompiler)
}

kotlin {
    jvmToolchain(21)
}

dependencies {
    implementation(project(":tm-ui-kit"))
    implementation(compose.desktop.currentOs)
    implementation(libs.kotlinx.coroutinesSwing)
}

compose.desktop {
    application {
        mainClass = "org.mr0btain.uikit.gallery.app.MainKt"
    }
}
