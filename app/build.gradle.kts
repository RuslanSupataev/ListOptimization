plugins {
	id(Conventions.androidApp)
	alias(libs.plugins.kotlin.android)
}

dependencies {
	
	implementation(libs.core.ktx)
	implementation(libs.appcompat)
	implementation(libs.material)
	implementation(libs.constraintlayout)
	implementation(libs.lifecycle.livedata.ktx)
	implementation(libs.lifecycle.viewmodel.ktx)
	implementation(libs.navigation.fragment.ktx)
	implementation(libs.navigation.ui.ktx)
	implementation(libs.timber)
	implementation(libs.kotlinx.serialization)
}