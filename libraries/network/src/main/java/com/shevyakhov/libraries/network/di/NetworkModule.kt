package com.shevyakhov.libraries.network.di

import com.shevyakhov.libraries.network.providers.provideOkHttpClient
import com.shevyakhov.libraries.network.providers.provideRetrofit
import org.koin.core.module.Module
import org.koin.dsl.module

const val BACKEND = "BACKEND"

val NetworkModule: Module = module {
	single {
		provideOkHttpClient()
	}
	single {
		provideRetrofit(
			okHttpClient = get(),
			url = getProperty(BACKEND)
		)
	}
}