package com.shevyakhov.features.main.di

import com.shevyakhov.features.main.data.api.HotelApi
import com.shevyakhov.features.main.data.datasource.HotelDataSource
import com.shevyakhov.features.main.data.datasource.HotelDataSourceImpl
import com.shevyakhov.features.main.data.repository.HotelRepositoryImpl
import com.shevyakhov.features.main.domain.repository.HotelRepository
import com.shevyakhov.features.main.domain.usecase.GetHotelDataUseCase
import com.shevyakhov.features.main.presentation.MainFragmentViewModel
import com.shevyakhov.libraries.network.createRetrofitService
import com.shevyakhov.libraries.network.getRetrofit
import org.koin.androidx.viewmodel.dsl.viewModel
import org.koin.dsl.module

val ViewModelModule = module {

	single { GetHotelDataUseCase(repository = get()) }
	viewModel {
		MainFragmentViewModel(
			router = get(),
			getHotelDataUseCase = get()
		)
	}
}
private val RetrofitModule = module {
	factory { createRetrofitService<HotelApi>(getRetrofit()) }
}
private val DataSourceModule = module {
	single<HotelDataSource> { HotelDataSourceImpl(api = get()) }
}

private val RepositoryModule = module {
	factory<HotelRepository> { HotelRepositoryImpl(hotelDataSource = get()) }
}

private val UseCaseModule = module {
	factory { GetHotelDataUseCase(repository = get()) }
}

val MainFragmentModule = listOf(
	ViewModelModule, RetrofitModule, DataSourceModule, RepositoryModule, UseCaseModule
)