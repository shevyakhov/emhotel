package com.shevyakhov.features.Roomsrooms.di

import com.shevyakhov.features.hotelrooms.data.api.RoomsApi
import com.shevyakhov.features.hotelrooms.data.datasource.RoomsDataSource
import com.shevyakhov.features.hotelrooms.data.datasource.RoomsDataSourceImpl
import com.shevyakhov.features.hotelrooms.data.repository.RoomsRepositoryImpl
import com.shevyakhov.features.hotelrooms.domain.repository.RoomsRepository
import com.shevyakhov.features.hotelrooms.domain.usecase.GetRoomsDataUseCase
import com.shevyakhov.features.hotelrooms.presentation.RoomsViewModel
import com.shevyakhov.libraries.network.createRetrofitService
import com.shevyakhov.libraries.network.getRetrofit
import org.koin.androidx.viewmodel.dsl.viewModel
import org.koin.dsl.module

val ViewModelModule = module {

	single { GetRoomsDataUseCase(repository = get()) }
	viewModel {
		RoomsViewModel(
			router = get(),
			getRoomsDataUseCase = get()
		)
	}
}
private val RetrofitModule = module {
	factory { createRetrofitService<RoomsApi>(getRetrofit()) }
}
private val DataSourceModule = module {
	single<RoomsDataSource> { RoomsDataSourceImpl(api = get()) }
}

private val RepositoryModule = module {
	factory<RoomsRepository> { RoomsRepositoryImpl(roomsDataSource = get()) }
}

private val UseCaseModule = module {
	factory { GetRoomsDataUseCase(repository = get()) }
}

val RoomsFragmentModule = listOf(
	ViewModelModule, RetrofitModule, DataSourceModule, RepositoryModule, UseCaseModule
)