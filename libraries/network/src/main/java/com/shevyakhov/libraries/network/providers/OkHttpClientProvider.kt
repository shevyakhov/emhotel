package com.shevyakhov.libraries.network.providers

import com.shevyakhov.libraries.network.okHttpClientSetups
import okhttp3.OkHttpClient

fun provideOkHttpClient(): OkHttpClient =
	okHttpClientSetups()