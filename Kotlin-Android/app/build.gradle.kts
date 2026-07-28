plugins {
    id("com.android.application")
    id("org.jetbrains.kotlin.android")
    // FCM ke liye google-services.json daalne ke baad ye line uncomment karo:
    // id("com.google.gms.google-services")
}

android {
    namespace = "in.languageplay.meowguru"
    compileSdk = 34

    defaultConfig {
        applicationId = "in.languageplay.meowguru"
        minSdk = 24
        targetSdk = 34
        versionCode = 1
        versionName = "1.0"

        // ===== YAHAN APNI VALUES DAALO =====
        buildConfigField("String", "API_BASE", "\"https://languageplay.in/languageplay/api/\"")
        buildConfigField("String", "WEB_APP", "\"https://languageplay.in/languageplay/\"")
        buildConfigField("String", "BOT_URL", "\"https://t.me/MeowGuruBot\"")
        // Google Cloud Console → OAuth Client ID (type: WEB) — Android bhi isi ko use karta hai
        buildConfigField(
            "String", "GOOGLE_WEB_CLIENT_ID",
            "\"433797561933-k8rtptd6v7f8vg9j2071sdj9l6hcm3go.apps.googleusercontent.com\""
        )
        // Meow Connect (18+ chat) — Play Store review aasan rakhne ke liye false rakho
        buildConfigField("boolean", "CONNECT_ENABLED", "false")
    }

    buildTypes {
        release {
            isMinifyEnabled = true
            isShrinkResources = true
            proguardFiles(getDefaultProguardFile("proguard-android-optimize.txt"), "proguard-rules.pro")
        }
        debug {
            isMinifyEnabled = false
        }
    }

    compileOptions {
        sourceCompatibility = JavaVersion.VERSION_17
        targetCompatibility = JavaVersion.VERSION_17
    }
    kotlinOptions {
        jvmTarget = "17"
    }
    buildFeatures {
        compose = true
        buildConfig = true
    }
    composeOptions {
        kotlinCompilerExtensionVersion = "1.5.14"
    }
    packaging {
        resources.excludes += "/META-INF/{AL2.0,LGPL2.1}"
    }
}

dependencies {
    val composeBom = platform("androidx.compose:compose-bom:2024.06.00")
    implementation(composeBom)

    implementation("androidx.core:core-ktx:1.13.1")
    implementation("androidx.lifecycle:lifecycle-runtime-ktx:2.8.3")
    implementation("androidx.lifecycle:lifecycle-viewmodel-compose:2.8.3")
    implementation("androidx.activity:activity-compose:1.9.0")

    implementation("androidx.compose.ui:ui")
    implementation("androidx.compose.ui:ui-graphics")
    implementation("androidx.compose.material3:material3")
    implementation("androidx.compose.material:material-icons-extended")
    implementation("androidx.navigation:navigation-compose:2.7.7")

    // Storage
    implementation("androidx.datastore:datastore-preferences:1.1.1")

    // Network
    implementation("com.squareup.retrofit2:retrofit:2.11.0")
    implementation("com.squareup.retrofit2:converter-gson:2.11.0")
    implementation("com.squareup.okhttp3:logging-interceptor:4.12.0")

    // Google Sign-In (Credential Manager)
    implementation("androidx.credentials:credentials:1.2.2")
    implementation("androidx.credentials:credentials-play-services-auth:1.2.2")
    implementation("com.google.android.libraries.identity.googleid:googleid:1.1.1")

    // Notifications — local reminders
    implementation("androidx.work:work-runtime-ktx:2.9.0")

    // Push (FCM) — google-services.json daalne ke baad hi kaam karega
    implementation(platform("com.google.firebase:firebase-bom:33.1.2"))
    implementation("com.google.firebase:firebase-messaging-ktx")

    // Camera (ISL sign mirror)
    implementation("androidx.camera:camera-camera2:1.3.4")
    implementation("androidx.camera:camera-lifecycle:1.3.4")
    implementation("androidx.camera:camera-view:1.3.4")

    // Images
    implementation("io.coil-kt:coil-compose:2.6.0")

    // WebView: document-start JS injection (games ki speechSynthesis() ko
    // native TextToSpeech se hook karne ke liye — WebView me speechSynthesis
    // khud se silently kaam nahi karta)
    implementation("androidx.webkit:webkit:1.11.0")

    debugImplementation("androidx.compose.ui:ui-tooling")
    implementation("androidx.compose.ui:ui-tooling-preview")
}
