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

    private val _resultProducts = MutableLiveData<ProductUiState>()
    val resultProducts: LiveData<ProductUiState> get() = _resultProducts


    private val _resultReviews = MutableLiveData<EventWrapper<ReviewUiState>>()
    val resultReviews: LiveData<EventWrapper<ReviewUiState>> get() = _resultReviews

    fun fetchProducts() = viewModelScope.launch(Dispatchers.IO) {
        when (val res = productsInteractor.fetchProduct()) {
            is Result.Success -> {
                val products = res.data.map { product ->
                    productUiMapper.map(product)
                }
                _resultProducts.postValue(ProductUiState.Success(products))
            }

            is Result.Error -> {
                _resultProducts.postValue(ProductUiState.Error(res.e.message ?: "Error"))
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
        _resultReviews.postValue(EventWrapper.Single(ReviewUiState.Loading))
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
/*                val list = mutableListOf<ReviewUi>()
                currentProduct.value?.let {
                    list.add(ReviewUi.Image(it.url))
                    list.add(ReviewUi.Title(it.name))
                    list.add(ReviewUi.Text(it.description))
                    list.add(ReviewUi.Delimiter)
                    list.add(ReviewUi.Section("Отзывы"))
                }*/
                val data = result.data
                val reviews =
                    if (data.isEmpty()) listOf(ReviewUi.Empty) else result.data.map { review ->
                        reviewUiMapper.map(review)
                    }
                _resultReviews.postValue(EventWrapper.Single(ReviewUiState.Success(/*list + */reviews)))
            }

            is Result.Error -> {
                _resultReviews.postValue(EventWrapper.Single(ReviewUiState.Error(result.e.message ?: "Error")))
            }
        }
    }
}