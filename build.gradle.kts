plugins {
    kotlin("multiplatform") version Config.Versions.Kotlin.kotlin
    kotlin("plugin.serialization") version Config.Versions.Kotlin.kotlin
}

group = "com.petersamokhin"
version = "0.0.1"

buildscript {
    repositories {
        mavenCentral()
        google()
    }

    dependencies {
        classpath("com.vanniktech:gradle-maven-publish-plugin:${Config.Versions.Plugin.publish}")
    }
}

repositories {
    mavenCentral()
}

kotlin {
    explicitApiWarning()

    jvm()

    js {
        nodejs()

        compilations.all {
            kotlinOptions {
                sourceMap = true
                moduleKind = "umd"
                metaInfo = true
            }
        }
    }

    @Suppress("UNUSED_VARIABLE")
    sourceSets {
        val commonMain by getting {
            dependencies {
                implementation(kotlin("stdlib-common"))

                implementation("org.jetbrains.kotlinx:kotlinx-serialization-core:${Config.Versions.Kotlin.serialization}")
                implementation("org.jetbrains.kotlinx:kotlinx-serialization-json:${Config.Versions.Kotlin.serialization}")
                implementation("io.ktor:ktor-client-core:${Config.Versions.ktor}")
                implementation("io.ktor:ktor-client-serialization:${Config.Versions.ktor}")
                implementation("co.touchlab:stately-common:${Config.Versions.stately}")
                implementation("co.touchlab:stately-concurrency:${Config.Versions.stately}")
            }
        }
        val commonTest by getting {
            dependencies {
                implementation(kotlin("test-common"))
                implementation(kotlin("test-annotations-common"))
            }
        }

        val nativeMain by creating {
            dependsOn(commonMain)
        }

        val darwinMain by creating {
            dependsOn(nativeMain)
        }

        val linuxMain by creating {
            dependsOn(nativeMain)
        }

        val mingwMain by creating {
            dependsOn(nativeMain)
        }

        all {
            languageSettings.optIn("kotlin.RequiresOptIn")
        }
    }

    listOf(
        "iosX64", "iosArm32", "iosArm64", "tvosX64", "tvosArm64", "watchosX86", "watchosArm32", "watchosArm64", "macosX64"
    ).forEach {
        targetFromPreset(presets[it], it) {
            compilations["main"].source(sourceSets["darwinMain"])
        }
    }

    listOf(
        // https://github.com/Kotlin/kotlinx.coroutines/issues/855
        // "linuxArm32Hfp", "linuxMips32",
        "linuxX64"
    ).forEach {
        targetFromPreset(presets[it], it) {
            compilations["main"].source(sourceSets["linuxMain"])
        }
    }

    targetFromPreset(presets["mingwX64"], "mingwX64") {
        compilations["main"].source(sourceSets["mingwMain"])
    }
}

apply(from = "$rootDir/gradle/mavenpublish.gradle")