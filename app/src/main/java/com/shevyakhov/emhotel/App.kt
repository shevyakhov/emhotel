package com.shevyakhov.emhotel

import android.app.Application
import com.shevyakhov.emhotel.di.AppModule
import com.shevyakhov.emhotel.di.RouterModule
import com.shevyakhov.features.booking.di.BookingFragmentModule
import com.shevyakhov.features.hotelrooms.di.RoomsFragmentModule
import com.shevyakhov.features.main.di.MainFragmentModule
import com.shevyakhov.features.orderconfirm.di.OrderConfirmFragmentModule
import com.shevyakhov.features.splash.di.SplashModule
import com.shevyakhov.libraries.network.di.BACKEND
import com.shevyakhov.libraries.network.di.NetworkModule
import org.koin.android.ext.koin.androidContext
import org.koin.core.context.startKoin

class App : Application() {

	override fun onCreate() {
		super.onCreate()
		startKoin {
			properties(
				mapOf(BACKEND to BuildConfig.BASE_URL),
			)

			androidContext(this@App)
			modules(AppModule)
			modules(RouterModule)
			modules(NetworkModule)

			modules(MainFragmentModule)
			modules(RoomsFragmentModule)
			modules(SplashModule)
			modules(BookingFragmentModule)
			modules(OrderConfirmFragmentModule)
		}
	}
}