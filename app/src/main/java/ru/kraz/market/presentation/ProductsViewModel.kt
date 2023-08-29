package ru.kraz.market.presentation

import androidx.lifecycle.LifecycleOwner
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import ru.kraz.market.core.EventWrapper
import ru.kraz.market.domain.ProductsInteractor

class ProductsViewModel(
    private val productsInteractor: ProductsInteractor,
    private val communicationProduct: ProductCommunication,
    private val communicationReview: ReviewCommunication,
) : ViewModel() {

    private val _currentProduct = MutableLiveData<ProductUi>()
    val currentProduct: LiveData<ProductUi>
        get() = _currentProduct

    fun fetchProducts() = viewModelScope.launch(Dispatchers.IO) {
        productsInteractor.fetchProduct().collect { productsDomain ->
            val data = productsDomain.map()
            withContext(Dispatchers.Main) {
                data.map(communicationProduct)
            }
        }
    }

    fun sendReview(textReview: String) = viewModelScope.launch(Dispatchers.IO) {
        _currentProduct.value?.let {
            productsInteractor.sendReview(textReview, it.id).collect { reviewsDomain ->
                val data = reviewsDomain.map()
                withContext(Dispatchers.Main) {
                    data.map(communicationReview)
                }
            }
        }
    }

    fun fetchReviews() = viewModelScope.launch(Dispatchers.IO) {
        _currentProduct.value?.let {
            productsInteractor.fetchReviews(it.id).collect { reviewsDomain ->
                val data = reviewsDomain.map()
                withContext(Dispatchers.Main) {
                    data.map(communicationReview)
                }
            }
        }
    }

    fun observeProduct(lifecycleOwner: LifecycleOwner, observer: Observer<List<ProductUi>>) {
        communicationProduct.observe(lifecycleOwner, observer)
    }

    fun observeReview(lifecycleOwner: LifecycleOwner, observer: Observer<EventWrapper<List<ReviewUi>>>) {
        communicationReview.observe(lifecycleOwner, observer)
    }

    fun setCurrentProduct(product: ProductUi) {
        _currentProduct.value = product
    }
}