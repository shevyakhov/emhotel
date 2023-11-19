package com.shevyakhov.features.booking.di

import com.shevyakhov.features.booking.data.api.BookingApi
import com.shevyakhov.features.booking.data.datasource.BookingDataSource
import com.shevyakhov.features.booking.data.datasource.BookingDataSourceImpl
import com.shevyakhov.features.booking.data.repository.BookingRepositoryImpl
import com.shevyakhov.features.booking.domain.repository.BookingRepository
import com.shevyakhov.features.booking.domain.usecase.GetBookingDataUseCase
import com.shevyakhov.features.booking.presentation.BookingViewModel
import com.shevyakhov.libraries.network.createRetrofitService
import com.shevyakhov.libraries.network.getRetrofit
import org.koin.androidx.viewmodel.dsl.viewModel
import org.koin.dsl.module

val ViewModelModule = module {

	single { GetBookingDataUseCase(repository = get()) }
	viewModel {
		BookingViewModel(
			router = get(),
			getBookingDataUseCase = get()
		)
	}
}
private val RetrofitModule = module {
	factory { createRetrofitService<BookingApi>(getRetrofit()) }
}
private val DataSourceModule = module {
	single<BookingDataSource> { BookingDataSourceImpl(api = get()) }
}

private val RepositoryModule = module {
	factory<BookingRepository> { BookingRepositoryImpl(bookingDataSource = get()) }
}

private val UseCaseModule = module {
	factory { GetBookingDataUseCase(repository = get()) }
}

val BookingFragmentModule = listOf(
	ViewModelModule, RetrofitModule, DataSourceModule, RepositoryModule, UseCaseModule
)