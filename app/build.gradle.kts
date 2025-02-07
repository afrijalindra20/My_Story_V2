plugins {
    id("com.android.application")
    id("org.jetbrains.kotlin.android")
    id("com.google.devtools.ksp")
    id("kotlin-parcelize")
    id("com.google.android.libraries.mapsplatform.secrets-gradle-plugin")
}

android {
    namespace = "com.dicoding.picodiploma.mystory2"
    compileSdk = 34

    defaultConfig {
        applicationId = "com.dicoding.picodiploma.mystory2"
        minSdk = 28
        targetSdk = 33
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
    kotlinOptions {
        jvmTarget = "1.8"
    }
    buildFeatures {
        viewBinding = true
        buildConfig = true
    }
}

    dependencies {
        implementation("com.android.car.ui:car-ui-lib:2.6.0")
        implementation("androidx.appcompat:appcompat:1.6.1")
        implementation("com.google.android.material:material:1.11.0")
        implementation("androidx.datastore:datastore-preferences:1.0.0")
        implementation("androidx.lifecycle:lifecycle-viewmodel-ktx:2.6.1")
        implementation("androidx.lifecycle:lifecycle-livedata-ktx:2.6.1")
        implementation("org.jetbrains.kotlinx:kotlinx-coroutines-core:1.5.2")
        implementation("org.jetbrains.kotlinx:kotlinx-coroutines-android:1.5.2")
        implementation("androidx.room:room-ktx:2.4.2")
        implementation("androidx.constraintlayout:constraintlayout:2.1.4")
        implementation("androidx.viewpager2:viewpager2:1.0.0")
        implementation("com.github.bumptech.glide:glide:4.16.0")
        implementation("com.squareup.retrofit2:retrofit:2.9.0")
        implementation("com.squareup.retrofit2:converter-gson:2.9.0")
        implementation("com.squareup.okhttp3:logging-interceptor:4.11.0")
        implementation("com.loopj.android:android-async-http:1.4.11")
        implementation("com.google.android.gms:play-services-maps:18.2.0")

        implementation("org.jetbrains.kotlinx:kotlinx-coroutines-core:1.6.4")
        implementation("androidx.activity:activity-ktx:1.7.2")
        implementation("androidx.core:core-ktx:1.10.0")
        implementation("androidx.appcompat:appcompat:1.3.1")
        implementation("com.google.android.material:material:1.4.0")
        implementation("androidx.activity:activity:1.2.3")
        implementation("androidx.constraintlayout:constraintlayout:2.0.4")
        implementation("androidx.recyclerview:recyclerview:1.2.1")
        testImplementation("junit:junit:4.13.2")
        androidTestImplementation("androidx.test.ext:junit:1.1.3")
        androidTestImplementation("androidx.test.espresso:espresso-core:3.4.0")
        implementation("androidx.camera:camera-camera2:1.0.1")
        implementation("androidx.camera:camera-lifecycle:1.0.1")
        implementation("androidx.camera:camera-view:1.0.0-alpha25")
        implementation("com.squareup.retrofit2:retrofit:2.9.0")
        implementation("com.squareup.retrofit2:converter-gson:2.9.0")
        implementation("com.squareup.okhttp3:logging-interceptor:4.9.1")
        implementation("androidx.lifecycle:lifecycle-runtime-ktx:2.3.1")
        implementation("androidx.lifecycle:lifecycle-livedata-ktx:2.3.1")
        implementation("androidx.exifinterface:exifinterface:1.3.6")
        implementation("com.google.android.material:material:1.9.0")
        implementation("androidx.activity:activity-ktx:1.7.0")
        implementation("com.google.android.gms:play-services-maps:18.2.0")
        implementation("androidx.paging:paging-runtime-ktx:3.1.1")
        implementation("androidx.room:room-ktx:2.6.1")
        ksp("androidx.room:room-compiler:2.6.1")

        implementation("androidx.room:room-paging:2.6.1")
        implementation("com.squareup.retrofit2:retrofit:2.9.0")
        implementation("com.squareup.retrofit2:converter-gson:2.9.0")
        implementation("com.squareup.okhttp3:logging-interceptor:4.11.0")

        androidTestImplementation("androidx.arch.core:core-testing:2.2.0")
        androidTestImplementation("org.jetbrains.kotlinx:kotlinx-coroutines-test:1.6.4") 

        testImplementation("androidx.arch.core:core-testing:2.2.0")
        testImplementation("org.jetbrains.kotlinx:kotlinx-coroutines-test:1.6.4")
        testImplementation("org.mockito:mockito-core:4.6.1")
        testImplementation("org.mockito:mockito-inline:4.6.1")
    }
