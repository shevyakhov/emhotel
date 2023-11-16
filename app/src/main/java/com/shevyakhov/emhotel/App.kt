package com.shevyakhov.emhotel

import android.app.Application
import com.shevyakhov.features.splash.di.SplashModule
import com.shevyakhov.emhotel.di.AppModule
import com.shevyakhov.emhotel.di.RouterModule
import org.koin.android.ext.koin.androidContext
import org.koin.core.context.startKoin

class App : Application() {

	override fun onCreate() {
		super.onCreate()
		startKoin {
			androidContext(this@App)
			modules(AppModule)
			modules(RouterModule)

			modules(SplashModule)
		}
	}
}