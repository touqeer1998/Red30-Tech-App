import org.gradle.kotlin.dsl.testFixturesCompileOnly

plugins {
    alias(libs.plugins.android.application)
    alias(libs.plugins.kotlin.android)
    alias(libs.plugins.kotlin.compose)
    alias(libs.plugins.kotlin.serialization)
    alias(libs.plugins.ksp)
    alias(libs.plugins.safeargs)
    alias(libs.plugins.screenshot)
}

@Suppress("UnstableApiUsage")
android {
    namespace = "com.example.red30"
    compileSdk = 35
    compileSdkPreview = "Baklava"

    defaultConfig {
        applicationId = "com.example.red30"
        minSdk = 26
        targetSdk = 35
        versionCode = 1
        versionName = "1.0"

        testInstrumentationRunner = "androidx.test.runner.AndroidJUnitRunner"
    }

    buildTypes {
        release {
            isMinifyEnabled = false
            proguardFiles(
                getDefaultProguardFile("proguard-android-optimize.txt"),
                "proguard-rules.pro"
            )
        }
    }
    compileOptions {
        sourceCompatibility = JavaVersion.VERSION_17
        targetCompatibility = JavaVersion.VERSION_17
    }
    kotlinOptions {
        jvmTarget = "17"
        freeCompilerArgs +=
            listOf(
                "-opt-in=androidx.compose.material3.ExperimentalMaterial3Api",
                "-opt-in=androidx.compose.foundation.layout.ExperimentalLayoutApi",
                "-opt-in=kotlinx.coroutines.ExperimentalCoroutinesApi"
            )
    }
    buildFeatures {
        compose = true
        viewBinding = true
    }
    testOptions {
        unitTests {
            isReturnDefaultValues = true // needed for handling Log.* calls in unit tests
        }
        emulatorControl {
            enable = true
        }
        managedDevices {
            localDevices {
                create("pixel9api35") {
                    device = "Pixel 9"
                    apiLevel = 35
                    systemImageSource = "aosp-atd"
                }
                create("mediumTabletapi30") {
                    device = "Medium Tablet"
                    apiLevel = 30
                    systemImageSource = "aosp-atd"
                }
                create("pixel7api30") {
                    device = "Pixel 7"
                    apiLevel = 30
                    systemImageSource = "aosp-atd"
                }
                create("pixelFoldapi34") {
                    device = "Pixel Fold"
                    apiLevel = 34
                    systemImageSource = "aosp"
                }
            }
        }
    }
    testFixtures {
        enable = true
    }
    experimentalProperties["android.experimental.enableScreenshotTest"] = true
}

dependencies {
    implementation(libs.android.material)
    implementation(libs.androidx.appcompat)
    implementation(libs.androidx.activity.ktx)
    implementation(libs.androidx.fragment.ktx)
    implementation(libs.androidx.core.ktx)
    implementation(libs.androidx.recyclerView)
    implementation(libs.androidx.preferences.datastore)
    implementation(libs.androidx.constraint.layout)
    implementation(libs.androidx.navigation.runtime.ktx)
    implementation(libs.androidx.navigation.fragment.ktx)
    implementation(libs.androidx.navigation.ui.ktx)
    implementation(libs.androidx.lifecycle.runtime.ktx)
    implementation(libs.androidx.lifecycle.runtime.compose)
    implementation(libs.androidx.lifecycle.viewModel.compose)
    implementation(platform(libs.androidx.compose.bom))
    implementation(libs.androidx.activity.compose)
    implementation(libs.androidx.compose.navigation)
    implementation(libs.androidx.ui)
    implementation(libs.androidx.ui.graphics)
    implementation(libs.androidx.ui.tooling.preview)
    implementation(libs.androidx.material3)
    implementation(libs.androidx.compose.material.icons)
    implementation(libs.androidx.compose.material.adaptive)
    implementation(libs.androidx.ui.text.google.fonts)
    implementation(libs.androidx.material3.window.size)
    implementation(libs.coil)
    implementation(libs.coil.network)
    implementation(libs.coil.compose)
    implementation(libs.ktor.client)
    implementation(libs.kotlinx.serialization.json)
    implementation(platform(libs.koin.bom))
    implementation(libs.koin.core)
    implementation(libs.koin.android)
    implementation(libs.koin.androidx.compose)

    testFixturesImplementation(platform(libs.androidx.compose.bom))
    testFixturesImplementation(libs.androidx.ui)
    testFixturesCompileOnly(libs.kotlin.stdlib)
    testFixturesRuntimeOnly(libs.kotlin.stdlib)

    testImplementation(libs.junit)
    testImplementation(libs.koin.test)
    testImplementation(libs.kotlinx.coroutines.test)
    testImplementation(libs.app.cash.turbine)
    testImplementation(libs.androidx.test.ext.truth)

    androidTestImplementation(libs.androidx.junit)
    androidTestImplementation(libs.androidx.test.rules)
    androidTestImplementation(libs.androidx.test.ext.truth)
    androidTestImplementation(libs.androidx.test.espresso.core)
    androidTestImplementation(libs.androidx.test.espresso.device)
    androidTestImplementation(platform(libs.androidx.compose.bom))
    androidTestImplementation(libs.androidx.ui.test.junit4)
    androidTestImplementation(libs.androidx.compose.ui.test)
    androidTestImplementation(libs.androidx.compose.ui.test.junit4)

    screenshotTestImplementation(libs.androidx.ui.tooling)
    screenshotTestImplementation(libs.androidx.material3.window.size)

    debugImplementation(libs.androidx.ui.tooling)
    debugImplementation(libs.androidx.ui.test.manifest)
}
