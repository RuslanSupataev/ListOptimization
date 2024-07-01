import com.android.build.gradle.BaseExtension

plugins {
    id("com.android.application")
    kotlin("android")
}

configure<BaseExtension> {
	val libs = libs
	plugins {
		id(libs.plugKotlinxSerialization())
	}
}

android {
    namespace = AndroidConfig.applicationId

    compileSdk = AndroidConfig.compileSdk

    defaultConfig {
        applicationId = AndroidConfig.applicationId
        minSdk = AndroidConfig.minSdk
        targetSdk = AndroidConfig.targetSdk
        versionCode = AndroidConfig.versionCode
        versionName = AndroidConfig.versionName
    }

    buildTypes {
        getByName("release") {
            isMinifyEnabled = true
            isShrinkResources = true
            proguardFiles(
                getDefaultProguardFile("proguard-android-optimize.txt"), "proguard-rules.pro"
            )
        }
        getByName("debug") {
            applicationIdSuffix = ".debug"
            isDebuggable = true
        }
    }
    compileOptions {
        sourceCompatibility = LangOptions.javaVersion
        targetCompatibility = LangOptions.javaVersion
    }
    kotlinOptions {
        jvmTarget = LangOptions.jvmTarget
    }
    buildFeatures {
        viewBinding = true
	    buildConfig = true
    }
}

kotlin {
    jvmToolchain(LangOptions.jvmToolchain)
}

dependencies {
	implementation(libs.libKoin())
	implementation(libs.libGlide())
}