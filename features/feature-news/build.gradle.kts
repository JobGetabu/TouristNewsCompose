plugins {
    id("com.android.library")
    id("org.jetbrains.kotlin.android")
    id("kotlin-kapt")
    id("dagger.hilt.android.plugin")
    id("org.jetbrains.kotlin.plugin.serialization")
}

android {
    namespace = "ke.newsarticles.feature_news"
    compileSdk = Configs.compileSdk

    defaultConfig {
        minSdk = Configs.minSdk
        targetSdk = Configs.targetSdk

        testInstrumentationRunner = "androidx.test.runner.AndroidJUnitRunner"
        consumerProguardFiles("consumer-rules.pro")
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
        sourceCompatibility = JavaVersion.VERSION_1_8
        targetCompatibility = JavaVersion.VERSION_1_8
    }
    kotlinOptions {
        jvmTarget = "1.8"
    }
    composeOptions {
        kotlinCompilerExtensionVersion = Versions.COMPOSE_COMPILER
    }
    buildFeatures {
        compose = true
    }
}

dependencies {

    implementation(Dependencies.ktxCore)
    implementation(Dependencies.composeUi)
    implementation(Dependencies.composeToolingPreview)
    implementation(Dependencies.composeMaterial)
    implementation(Dependencies.composeNavigation)
    debugImplementation(Dependencies.composeTooling)
    debugImplementation(Dependencies.activityCompose)

    implementation(Dependencies.coil)

    implementation(Dependencies.hiltNavigationCompose)
    implementation(Dependencies.hilt)
    kapt(Dependencies.hiltCompiler)

    implementation(Dependencies.composeViewModel)
    implementation(Dependencies.coroutinesCore)
    implementation(Dependencies.lifecycleRuntime)

    implementation(project(mapOf("path" to ":core-network")))
    implementation(project(mapOf("path" to ":core-utils")))
    implementation(project(mapOf("path" to ":core-database")))
    implementation(project(":core-resourses"))

    implementation(Dependencies.kotlinSerializationJson)
    implementation(Dependencies.retrofitKotlinSerialization)
    implementation(Dependencies.retrofit)
}