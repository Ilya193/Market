package ru.ikom.feature_basket.presentation

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch
import ru.ikom.common.Router
import ru.ikom.feature_basket.domain.FetchMealsBasketUseCase
import ru.ikom.feature_basket.domain.LoadResult
import ru.ikom.feature_basket.presentation.Mappers.getData
import ru.ikom.feature_basket.presentation.Mappers.toMealDomain
import ru.ikom.feature_basket.presentation.Mappers.toMealUi

class BasketViewModel(
    private val router: BasketRouter,
    private val fetchMealsBasketUseCase: FetchMealsBasketUseCase,
    private val deleteMealUseCase: DeleteMealUseCase,
    private val dispatcher: CoroutineDispatcher = Dispatchers.IO
) : ViewModel(), Router {

    private val _uiState = MutableStateFlow<BasketUiState>(BasketUiState.Loading)
    val uiState: StateFlow<BasketUiState> get() = _uiState.asStateFlow()

    private var meals = mutableListOf<MealUi>()

    fun fetchMeals() = viewModelScope.launch(dispatcher) {
        when (val res = fetchMealsBasketUseCase()) {
            is LoadResult.Success -> {
                meals = res.data.map { it.toMealUi() }.toMutableList()
                _uiState.value = if (meals.isEmpty()) BasketUiState.Empty else BasketUiState.Success(meals.toList())
            }

            is LoadResult.Error -> _uiState.value = BasketUiState.Error(res.e.getData())
        }
    }

    fun update(index: Int) = viewModelScope.launch(dispatcher) {
        deleteMealUseCase(meals[index].toMealDomain())
        fetchMeals()
    }

    override fun coup() = router.coup()
}

sealed interface BasketUiState {
    data object Loading : BasketUiState

    data class Error(val msg: Int) : BasketUiState

    data object Empty : BasketUiState

    data class Success(val meals: List<MealUi>) : BasketUiState
}

