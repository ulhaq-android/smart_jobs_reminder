plugins {
    alias(libs.plugins.android.application)
    alias(libs.plugins.kotlin.android)
    id ("androidx.room")
    id("com.google.devtools.ksp")
}

android {
    namespace = "com.example.smartjobreminder"
    compileSdk = 35

    defaultConfig {
        applicationId = "com.example.smartjobreminder"
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
    room {
        schemaDirectory ("$projectDir/schemas")
    }
    compileOptions {
        sourceCompatibility = JavaVersion.VERSION_11
        targetCompatibility = JavaVersion.VERSION_11
    }
    kotlinOptions {
        jvmTarget = "11"
    }
    buildFeatures {
        viewBinding = true
    }
}

dependencies {

    implementation(libs.androidx.core.ktx)
    implementation(libs.androidx.appcompat)
    implementation(libs.material)
    implementation(libs.androidx.activity)
    implementation(libs.androidx.constraintlayout)
    testImplementation(libs.junit)
    androidTestImplementation(libs.androidx.junit)
    androidTestImplementation(libs.androidx.espresso.core)
    implementation (libs.androidx.work.runtime.ktx)

    // Koin
    implementation(project.dependencies.platform("io.insert-koin:koin-bom:4.1.0"))
    implementation(libs.koin.core)
    implementation(libs.koin.android)
    implementation ("io.insert-koin:koin-androidx-workmanager")

    // Room DB
    implementation(libs.androidx.room.runtime)
    ksp(libs.androidx.room.room.compiler)
    annotationProcessor(libs.androidx.room.room.compiler)
    implementation(libs.androidx.room.ktx)

    // Intuit SPD SSP
    implementation (libs.sdp.android)
    implementation (libs.ssp.android)

    // Datastore
    implementation (libs.androidx.datastore.preferences)

}