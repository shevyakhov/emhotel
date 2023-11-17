package com.shevyakhov.libraries.network

import org.koin.core.scope.Scope
import retrofit2.Retrofit

inline fun <reified T> createRetrofitService(retrofit: Retrofit): T =
	retrofit.create(T::class.java)

inline fun <reified T> Scope.getRetrofit(): T =
	get(T::class)