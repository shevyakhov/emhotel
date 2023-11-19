package com.shevyakhov.features.orderconfirm.di

import com.shevyakhov.features.orderconfirm.domain.GetOrderDataUseCase
import com.shevyakhov.features.orderconfirm.presentation.OrderConfirmViewModel
import org.koin.androidx.viewmodel.dsl.viewModel
import org.koin.dsl.module

val useCaseModule = module {
	single { GetOrderDataUseCase() }
}
val ViewModelModule = module {
	viewModel {
		OrderConfirmViewModel(
			router = get(),
			getOrderDataUseCase = get()
		)
	}
}

val OrderConfirmFragmentModule = listOf(ViewModelModule, useCaseModule)