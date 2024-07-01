package kg.ruslan.testproject

import android.app.Application
import kg.ruslan.testproject.data.koinModuleData
import kg.ruslan.testproject.domain.koinModuleDomain
import kg.ruslan.testproject.presentaion.koinModulePresentation
import org.koin.android.ext.koin.androidContext
import org.koin.core.context.startKoin
import timber.log.Timber

class App : Application() {
	override fun onCreate() {
		super.onCreate()
		
		if (BuildConfig.DEBUG) {
			Timber.plant(Timber.DebugTree())
		}
		
		// start a KoinApplication in Global context
		startKoin {
			androidContext(this@App)
			// declare used modules
			modules(
				koinModuleDomain,
				koinModuleData,
				koinModulePresentation,
			)
		}
	}
}