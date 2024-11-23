plugins {
    alias(libs.plugins.android.application)
}


android {
    namespace = "com.example.festiva"
    compileSdk = 34

    defaultConfig {
        applicationId = "com.example.festiva"
        minSdk = 27
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

    buildFeatures {
        viewBinding = true;
    }

}

dependencies {

    implementation(libs.appcompat)
    implementation(libs.material)
    implementation(libs.activity)
    implementation(libs.constraintlayout)
    testImplementation(libs.junit)
    androidTestImplementation(libs.ext.junit)
    androidTestImplementation(libs.espresso.core)
    implementation("com.google.android.material:material:1.12.0")
    implementation("androidx.core:core:1.12.0")

    implementation("com.squareup.okhttp3:okhttp:4.11.0")
    implementation("com.google.code.gson:gson:2.10")


    //calendar new
    //implementation("com.applandeo:material-calendar-view:1.9.2")

    // The view calendar library for Android
    //implementation("com.kizitonwose.calendar:view:2.6.0")
    //implementation("com.kizitonwose.calendar:compose:2.6.0")
    //coreLibraryDesugaring("com.android.tools:desugar_jdk_libs:2.1.2")
}
