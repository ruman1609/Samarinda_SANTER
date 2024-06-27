import java.io.FileInputStream
import java.util.Properties

plugins {
    alias(libs.plugins.android.library)
    alias(libs.plugins.jetbrains.kotlin.android)
    alias(libs.plugins.devtools.ksp)
    alias(libs.plugins.hilt.dagger)
    id("kotlin-parcelize")
}

fun getLocalProperty(propName: String) : Any? {
    val filename = "local.properties"
    val propsFile = rootProject.file(filename)
    if (propsFile.exists()) {
        val props = Properties()
        props.load(FileInputStream(propsFile))
        if (props[propName] != null) {
            return props[propName]
        } else {
            print("No such property $propName in file $filename")
        }
    } else {
        print("$filename does not exist!")
    }
    return null
}

android {
    namespace = "com.rudyrachman16.samarindasanter.core"
    compileSdk = 34

    defaultConfig {
        minSdk = 24

        testInstrumentationRunner = "androidx.test.runner.AndroidJUnitRunner"
        consumerProguardFiles("consumer-rules.pro")

        buildConfigField("String", "TOKEN", "${getLocalProperty("token")}")
        buildConfigField("String", "KEY", "${getLocalProperty("key")}")
        buildConfigField("String", "BASE_URL", "${getLocalProperty("base_url")}")
        buildConfigField("String", "IMAGE_URL", "${getLocalProperty("image_url")}")
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

    buildFeatures {
        buildConfig = true
    }

    compileOptions {
        sourceCompatibility = JavaVersion.VERSION_17
        targetCompatibility = JavaVersion.VERSION_17
    }
    kotlinOptions {
        jvmTarget = "17"
    }
}

dependencies {
    implementation(libs.androidx.room.runtime)
    ksp(libs.room.compiler)
    implementation(libs.androidx.room.ktx)

    implementation(libs.gson) // GSON
    implementation(libs.retrofit) // Retrofit
    implementation(libs.converter.gson)  // Retrofit GSON Converter
    implementation(libs.logging.interceptor)  // buat Log Retrofit

    implementation(libs.hilt.android)
    ksp(libs.hilt.android.compiler)

    implementation(libs.androidx.core.ktx)
    implementation(libs.androidx.appcompat)
    implementation(libs.material)
    testImplementation(libs.junit)
    androidTestImplementation(libs.androidx.junit)
    androidTestImplementation(libs.androidx.espresso.core)
}