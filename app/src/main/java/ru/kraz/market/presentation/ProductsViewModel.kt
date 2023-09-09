package ru.kraz.market.presentation

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import ru.kraz.market.core.EventWrapper
import ru.kraz.market.domain.ProductsInteractor
import ru.kraz.market.domain.Result
import ru.kraz.market.domain.ReviewDomain

class ProductsViewModel(
    private val productsInteractor: ProductsInteractor,
    /*private val productUiMapper: ToUiMapper<ProductDomain, ProductUi.Base>,
    private val reviewUiMapper: ToUiMapper<ReviewDomain, ReviewUi.Base>,*/
    private val productUiMapper: BaseToProductUiMapper,
    private val reviewUiMapper: BaseToReviewUiMapper,
) : ViewModel() {

    private val _currentProduct = MutableLiveData<ProductUi>()
    val currentProduct: LiveData<ProductUi>
        get() = _currentProduct

    private val _resultProducts = MutableLiveData<List<ProductUi>>()
    val resultProducts: LiveData<List<ProductUi>> get() = _resultProducts


    private val _resultReviews = MutableLiveData<EventWrapper<List<ReviewUi>>>()
    val resultReviews: LiveData<EventWrapper<List<ReviewUi>>> get() = _resultReviews
    private val event = EventWrapper.Change(listOf<ReviewUi>())

    fun fetchProducts() = viewModelScope.launch(Dispatchers.IO) {
        when (val res = productsInteractor.fetchProduct()) {
            is Result.Success -> {
                val products = res.data.map { product ->
                    productUiMapper.map(product)
                }
                _resultProducts.postValue(products)
            }

            is Result.Error -> {
                _resultProducts.postValue(listOf(ProductUi.Fail(res.e.message ?: "Error")))
            }
        }
    }

    fun sendReview(textReview: String) = viewModelScope.launch(Dispatchers.IO) {
        _currentProduct.value?.let {
            val res = productsInteractor.sendReview(textReview, it.id)
            mapResultReview(res)
        }
    }

    fun fetchReviews() = viewModelScope.launch(Dispatchers.IO) {
        _currentProduct.value?.let {
            val res = productsInteractor.fetchReviews(it.id)
            mapResultReview(res)
        }
    }

    fun setCurrentProduct(product: ProductUi) {
        _currentProduct.value = product
    }

    private fun mapResultReview(result: Result<List<ReviewDomain>>) {
        when (result) {
            is Result.Success -> {
                val reviews = result.data.map { review ->
                    reviewUiMapper.map(review)
                }
                event.setValue(reviews)
                _resultReviews.postValue(event)
            }

            is Result.Error -> {
                event.setValue(listOf(ReviewUi.Fail(result.e.message ?: "Error")))
                _resultReviews.postValue(event)
            }
        }
    }
}