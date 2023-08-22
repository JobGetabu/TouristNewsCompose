plugins {
    id("com.android.library")
    id("org.jetbrains.kotlin.android")
    id("kotlin-kapt")
    id("dagger.hilt.android.plugin")
    id("org.jetbrains.kotlin.plugin.serialization")
}

android {
    namespace = "ke.newsarticles.core_network"
    compileSdk = Configs.compileSdk

    defaultConfig {
        minSdk = Configs.minSdk
        targetSdk = Configs.targetSdk

        testInstrumentationRunner = "androidx.test.runner.AndroidJUnitRunner"
        consumerProguardFiles("consumer-rules.pro")
    }

    buildTypes {
        debug {
            buildConfigField("String", "BASE_URL", "\"http://restapi.adequateshop.com/api/\"")
        }
        release {
            buildConfigField("String", "BASE_URL", "\"http://restapi.adequateshop.com/api/\"")
            isMinifyEnabled = false
            proguardFiles(
                getDefaultProguardFile("proguard-android-optimize.txt"), "proguard-rules.pro"
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
}

dependencies {

    implementation(Dependencies.ktxCore)
    implementation(Dependencies.appCombat)

    implementation(Dependencies.retrofit)
    implementation(Dependencies.loggingInterceptor)
    implementation(Dependencies.retrofitCoroutineAdapter)
    implementation(Dependencies.kotlinSerializationJson)
    implementation(Dependencies.retrofitKotlinSerialization)

    implementation(Dependencies.hilt)
    kapt(Dependencies.hiltCompiler)

    //implementation(project(mapOf("path" to ":core-resourses")))
    //implementation(project(mapOf("path" to ":core-utils")))
}