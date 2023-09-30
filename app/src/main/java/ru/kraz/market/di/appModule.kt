package ru.kraz.market.di

import org.koin.androidx.viewmodel.dsl.viewModel
import org.koin.dsl.module
import retrofit2.Converter
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import ru.kraz.market.data.ProductsCloudDataSource
import ru.kraz.market.data.ProductsRepositoryImpl
import ru.kraz.market.domain.ProductsRepository
import ru.kraz.market.data.ProductsService
import ru.kraz.market.domain.ProductsInteractor
import ru.kraz.market.presentation.BaseToProductUiMapper
import ru.kraz.market.presentation.BaseToReviewUiMapper
import ru.kraz.market.presentation.ProductsViewModel
import ru.kraz.market.presentation.ResourceProvider
import ru.kraz.market.presentation.ResourceProviderImpl

val appModule = module {
    factory<Converter.Factory> {
        GsonConverterFactory.create()
    }

    single<ProductsService> {
        Retrofit.Builder()
            .baseUrl("http://192.168.1.2:8080/")
            .addConverterFactory(get())
            .build()
            .create(ProductsService::class.java)
    }

    factory<ProductsCloudDataSource> {
        ProductsCloudDataSource.Base(get())
    }

    factory<ProductsRepository> {
        ProductsRepositoryImpl(get())
    }

    factory<ProductsInteractor> {
        ProductsInteractor.Base(get())
    }

    /*factory<ToUiMapper<ProductDomain, ProductUi.Base>> {
        BaseToProductUiMapper()
    }

    factory<ToUiMapper<ReviewDomain, ReviewUi.Base>> {
        BaseToReviewUiMapper()
    }*/

    factory<BaseToProductUiMapper> {
        BaseToProductUiMapper()
    }

    factory<BaseToReviewUiMapper> {
        BaseToReviewUiMapper()
    }

    factory<ResourceProvider> {
        ResourceProviderImpl(get())
    }

    viewModel<ProductsViewModel> {
        ProductsViewModel(get(), get(), get(), get())
    }
}