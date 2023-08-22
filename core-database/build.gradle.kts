plugins {
    id("com.android.library")
    id("org.jetbrains.kotlin.android")
    id("kotlin-kapt")
    id("dagger.hilt.android.plugin")
    id("org.jetbrains.kotlin.plugin.serialization")
}

android {
    namespace = "ke.newsarticles.core_database"
    compileSdk = 33

    defaultConfig {
        minSdk = 24
        targetSdk = 33

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
}

dependencies {

    implementation(Dependencies.ktxCore)

    implementation(Dependencies.hilt)
    implementation(project(mapOf("path" to ":core-utils")))
    kapt(Dependencies.hiltCompiler)

    androidTestImplementation(Dependencies.coroutinesTest)
    androidTestImplementation(Dependencies.googleTruth)
    androidTestImplementation(Dependencies.androidxJunit)
    androidTestImplementation(Dependencies.expressoCore)

    implementation(Dependencies.roomKtx)
    kapt(Dependencies.roomCompiler)
    implementation(Dependencies.roomKRuntime)
    implementation(Dependencies.roomPaging)

    implementation(Dependencies.kotlinSerializationJson)

    implementation(Dependencies.paging)

    implementation( Dependencies.coreTest )
}