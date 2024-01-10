import java.text.SimpleDateFormat
import java.util.Date
import java.util.Locale

plugins {
    id("com.android.application")
}

android {
    namespace = "com.example.myapplication"
    compileSdk = 34

    signingConfigs {
        create("release") {
//            storeFile = file("C:\\Users\\mhzbi\\AndroidStudioProjects\\MyApplication\\myapp.jks")
            storeFile = file(System.getenv("ANDROID_KEY_ALIAS"))
            storePassword = "bikram"
            keyAlias = "bikram"
            keyPassword = "bikram"
        }

        create("uat") {
            storeFile = file(System.getenv("UAT_STORE_FILE_PATH"))
            storePassword = "bikram"
            keyAlias = "bikram"
            keyPassword = "bikram"
        }
    }

    flavorDimensions.add("version")
    productFlavors {
        create("version6") {
            isDefault = true
            dimension = "version"
            buildConfigField("Boolean", "PREMIUM", "false")
            resValue("string", "app_name", "Version6")
        }
        create("version9") {
            isDefault = true
            dimension = "version"
            buildConfigField("Boolean", "PREMIUM", "false")
            resValue("string", "app_name", "Version9")
//            applicationIdSuffix = ".v9"
        }

        create("version12") {
            dimension = "version"
            buildConfigField("Boolean", "PREMIUM", "true")
            resValue("string", "app_name", "Version12")
//            applicationIdSuffix = ".v12"
            signingConfig = signingConfigs.getByName("release")
        }
    }

    defaultConfig {
        applicationId = "com.example.myapplication"
        minSdk = 23
        targetSdk = 34
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

        create("new") {
            applicationIdSuffix = ".new"
            initWith(buildTypes.getByName("release"))
        }

    }

    compileOptions {
        sourceCompatibility = JavaVersion.VERSION_1_8
        targetCompatibility = JavaVersion.VERSION_1_8
    }
    buildFeatures {
        viewBinding = true
        buildConfig = true
    }

    applicationVariants.all {
        val variant = this
        val formattedDate = SimpleDateFormat("yyyy-MM-dd", Locale.getDefault()).format(Date())

//        val customApkNames = mapOf(
//            "version6" to listOf("test-v6${variant.versionCode}.apk", "test-v6.0.1default.apk"),
//            "version9" to listOf("test-v9${variant.versionCode}.apk", "test-v9default.apk"),
//            "version12" to listOf("test-v12${variant.versionCode}.apk", "test-v9default.apk")
//        )

        variant.outputs
            .map { it as com.android.build.gradle.internal.api.BaseVariantOutputImpl }
            .forEach { output ->
                val outputFileName =
                    "${variant.baseName}-${variant.versionName}-${variant.versionCode}-$formattedDate.apk"
                output.outputFileName = outputFileName
            }
    }
}

dependencies {

    implementation("androidx.appcompat:appcompat:1.6.1")
    implementation("com.google.android.material:material:1.11.0")
    implementation("androidx.constraintlayout:constraintlayout:2.1.4")
    implementation("androidx.navigation:navigation-fragment:2.7.6")
    implementation("androidx.navigation:navigation-ui:2.7.6")
    testImplementation("junit:junit:4.13.2")
    androidTestImplementation("androidx.test.ext:junit:1.1.5")
    androidTestImplementation("androidx.test.espresso:espresso-core:3.5.1")
}