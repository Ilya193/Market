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
import ru.kraz.market.core.log
import ru.kraz.market.domain.ProductsInteractor

class ProductsViewModel(
    private val productsInteractor: ProductsInteractor,
    private val communication: ProductCommunication,
    private val communicationReview: ReviewCommunication
) : ViewModel() {

    private val _currentProduct = MutableLiveData<ProductUi>()
    val currentProduct: LiveData<ProductUi>
        get() = _currentProduct

    fun fetchProducts() = viewModelScope.launch(Dispatchers.IO) {
        productsInteractor.fetchProduct().collect  {
            val data = it.map()
            withContext(Dispatchers.Main) {
                data.map(communication)
            }
        }
    }

    fun sendReview(textReview: String) = viewModelScope.launch(Dispatchers.IO) {
        _currentProduct.value?.let {
            productsInteractor.sendReview(textReview, it.id).collect {
                log(it)
                val data = it.map()
                withContext(Dispatchers.Main) {
                    data.map(communicationReview)
                }
            }
        }
    }

    fun observe(lifecycleOwner: LifecycleOwner, observer: Observer<List<ProductUi>>) {
        communication.observe(lifecycleOwner, observer)
    }

    fun observeReview(lifecycleOwner: LifecycleOwner, observer: Observer<List<ReviewUi>>) {
        communicationReview.observe(lifecycleOwner, observer)
    }

    fun setCurrentProduct(product: ProductUi) {
        _currentProduct.value = product
    }
}