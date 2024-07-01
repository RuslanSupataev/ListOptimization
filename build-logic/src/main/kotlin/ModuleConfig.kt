import org.gradle.api.JavaVersion

object LangOptions {

    const val jvmToolchain: Int = 17
    val javaVersion: JavaVersion = JavaVersion.VERSION_17
    const val jvmTarget: String = jvmToolchain.toString()
}

object AndroidConfig {

    const val applicationId = "kg.ruslan.testproject"
    const val compileSdk: Int = 34
    const val minSdk: Int = 24
    const val targetSdk: Int = 34
    const val versionCode = 1
    const val versionName = "1.0"
}