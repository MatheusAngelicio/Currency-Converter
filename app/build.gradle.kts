plugins {
    alias(libs.plugins.android.application)
    alias(libs.plugins.kotlin.android)
    alias(libs.plugins.kotlin.compose)
    kotlin("plugin.serialization").version("2.2.21") // mesma versão do kotlin
}

android {
    namespace = "com.example.currencyconverter"
    compileSdk {
        version = release(36)
    }

    defaultConfig {
        applicationId = "com.example.currencyconverter"
        minSdk = 26
        targetSdk = 36
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
        sourceCompatibility = JavaVersion.VERSION_11
        targetCompatibility = JavaVersion.VERSION_11
    }
    kotlinOptions {
        jvmTarget = "11"
    }
    buildFeatures {
        compose = true
    }
}

dependencies {
    implementation(libs.androidx.core.ktx)
    implementation(libs.androidx.lifecycle.runtime.ktx)
    implementation(libs.androidx.activity.compose)
    implementation(platform(libs.androidx.compose.bom))
    implementation(libs.androidx.compose.ui)
    implementation(libs.androidx.compose.ui.graphics)
    implementation(libs.androidx.compose.ui.tooling.preview)
    implementation(libs.androidx.compose.material3)

    // VIEWMODEL
    implementation(libs.androidx.lifecycle.viewmodel.compose)

    // KTOR
    //ktor core, configuracoes basicas do ktor
    implementation(libs.ktor.client.core)
    //ktor okhttp, que vai ser o protocolo que vamos usar, mas poderia ser websocket tambem como exemplo
    implementation(libs.ktor.client.okhttp)
    //ktor loggin para melhorar a visualizacao no logcat
    implementation(libs.ktor.client.logging)
    //kton content negoation para podermos receber json e fazer o parse para classes kotlin
    implementation(libs.ktor.client.content.negotiation)
    //ktor serialization para podermos enviar e receber json
    implementation(libs.ktor.serialization.kotlinx.json)


    testImplementation(libs.junit)
    androidTestImplementation(libs.androidx.junit)
    androidTestImplementation(libs.androidx.espresso.core)
    androidTestImplementation(platform(libs.androidx.compose.bom))
    androidTestImplementation(libs.androidx.compose.ui.test.junit4)
    debugImplementation(libs.androidx.compose.ui.tooling)
    debugImplementation(libs.androidx.compose.ui.test.manifest)
}