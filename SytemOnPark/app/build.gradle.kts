plugins {
    alias(libs.plugins.android.application)
}

android {
    namespace = "com.example.sytemonpark"
    compileSdk = 35

    defaultConfig {
        applicationId = "com.example.sytemonpark"
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
    compileOptions {
        sourceCompatibility = JavaVersion.VERSION_1_8
        targetCompatibility = JavaVersion.VERSION_1_8
    }
}

dependencies {

    implementation(libs.appcompat)
    implementation(libs.material)
    implementation(libs.activity)
    implementation(libs.constraintlayout)
    implementation(libs.cronet.embedded)
    testImplementation(libs.junit)
    implementation ("androidx.cardview:cardview:1.0.0")

    implementation ("com.squareup.retrofit2:retrofit:2.9.0")
    implementation ("com.squareup.retrofit2:converter-gson:2.9.0")

        implementation ("androidx.appcompat:appcompat:1.4.0")
        implementation ("com.google.android.material:material:1.5.0")
    implementation ("com.android.volley:volley:1.2.1")
    implementation ("com.github.bumptech.glide:glide:4.15.1")
    implementation ("com.airbnb.android:lottie:6.0.0")
    implementation ("com.google.android.material:material:1.9.0")
    implementation ("com.github.denzcoskun:ImageSlideshow:0.1.0")

    annotationProcessor ("com.github.bumptech.glide:compiler:4.15.1")







    androidTestImplementation(libs.ext.junit)
    androidTestImplementation(libs.espresso.core)
}