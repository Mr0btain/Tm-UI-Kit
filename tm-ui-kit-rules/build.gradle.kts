/*
 * The checks that keep an app's screens on the kit, for each app's own tests to run over its sources:
 * assertTrue(KitRules.checkFiles(root, files).isEmpty())
 */
plugins {
    alias(libs.plugins.kotlinJvm)
    `maven-publish`
}

kotlin {
    jvmToolchain(21)
}

java {
    withSourcesJar()
}

dependencies {
    testImplementation(libs.kotlin.test)
}

publishing {
    publications {
        create<MavenPublication>("maven") {
            from(components["java"])
        }
    }
}
