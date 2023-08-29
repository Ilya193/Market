package ru.kraz.market.di

import org.koin.androidx.viewmodel.dsl.viewModel
import org.koin.dsl.module
import retrofit2.Converter
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import ru.kraz.market.data.ProductCloudDataSource
import ru.kraz.market.data.ProductRepository
import ru.kraz.market.data.ProductService
import ru.kraz.market.domain.ProductsInteractor
import ru.kraz.market.presentation.ProductCommunication
import ru.kraz.market.presentation.ProductsViewModel
import ru.kraz.market.presentation.ReviewCommunication

val appModule = module {
    factory<Converter.Factory> {
        GsonConverterFactory.create()
    }

    single<ProductService> {
        Retrofit.Builder()
            .baseUrl("http://192.168.1.5:8080/")
            .addConverterFactory(get())
            .build()
            .create(ProductService::class.java)
    }

    factory<ProductCloudDataSource> {
        ProductCloudDataSource.Base(get())
    }

    factory<ProductRepository> {
        ProductRepository.Base(get())
    }

    factory<ProductsInteractor> {
        ProductsInteractor.Base(get())
    }

    factory<ProductCommunication> {
        ProductCommunication()
    }

    factory<ReviewCommunication> {
        ReviewCommunication()
    }


    viewModel<ProductsViewModel> {
        ProductsViewModel(get(), get(), get())
    }
}