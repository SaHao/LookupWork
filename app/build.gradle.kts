plugins {
    alias(libs.plugins.android.application)
    alias(libs.plugins.kotlin.android)
}

android {
    namespace = "com.lllloookkk.upupp"
    compileSdk = 35

    defaultConfig {
        applicationId = "com.lllloookkk.upupp"
        minSdk = 24
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
    dataBinding {
        enable = true
    }
    viewBinding {
        enable = true
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
    implementation(libs.androidx.recyclerview.recyclerview2)
    implementation (libs.com.adjust.sdk.adjust.android12)
    implementation(libs.com.android.installreferrer.installreferrer10)
    implementation(libs.com.adjust.sdk.adjust.android.webbridge8)
    implementation(libs.com.google.android.gms.play.services.ads.identifier6)
    implementation(libs.com.adjust.sdk.adjust.android.meta.referrer3)
    implementation(libs.retrofit2.retrofit)
    implementation(libs.retrofit2.converter.gson)
    implementation(libs.adapter.rxjava3) // 如果你使用RxJava
    implementation(libs.kotlinx.coroutines.android) // 协程
    // OkHttp
    implementation(libs.okhttp)
    implementation(libs.logging.interceptor)
    implementation(libs.androidx.databinding.runtime)
    implementation(libs.androidx.core.ktx)
    implementation(libs.androidx.appcompat)
    implementation(libs.material)
    implementation(libs.androidx.activity)
    implementation(libs.androidx.constraintlayout)
    testImplementation(libs.junit)
    androidTestImplementation(libs.androidx.junit)
    androidTestImplementation(libs.androidx.espresso.core)
}