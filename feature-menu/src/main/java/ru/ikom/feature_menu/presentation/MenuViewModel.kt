package ru.ikom.feature_menu.presentation

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch
import ru.ikom.common.Router
import ru.ikom.feature_menu.domain.FetchMealsUseCase
import ru.ikom.feature_menu.domain.LoadResult
import ru.ikom.feature_menu.domain.UpdateMealUseCase
import ru.ikom.feature_menu.presentation.Mappers.getData
import ru.ikom.feature_menu.presentation.Mappers.toMealUi

class MenuViewModel(
    private val router: MenuRouter,
    private val fetchMealsUseCase: FetchMealsUseCase,
    private val updateMealUseCase: UpdateMealUseCase,
    private val dispatcher: CoroutineDispatcher = Dispatchers.IO,
) : ViewModel(), Router {

    private val banners = mutableListOf<BannerUi>().apply {
        addAll((0..5).map { BannerUi(it) })
    }

    private val categories = mutableListOf<CategoryUi>().apply {
        addAll(
            listOf(
                CategoryUi(0, "Пицца", true),
                CategoryUi(1, "Комбо", false),
                CategoryUi(2, "Десерты", false),
                CategoryUi(3, "Напитки", false)
            )
        )
    }

    private var meals = mutableListOf<MealUi>()

    private val _uiState = MutableStateFlow<MenuUiState>(MenuUiState.Loading)
    val uiState: StateFlow<MenuUiState> get() = _uiState

    init {
        fetchMeals()
    }

    fun reconnected() = viewModelScope.launch(dispatcher) {
        if (meals.isEmpty()) fetchMeals()
    }

    private fun fetchMeals() = viewModelScope.launch(dispatcher) {
        _uiState.value = MenuUiState.Loading
        fetchMealsUseCase().collect { result ->
            when (result) {
                is LoadResult.Success -> {
                    meals = result.data.map { it.toMealUi() }.toMutableList()
                    _uiState.value =
                        MenuUiState.Success(banners.toList(), categories.toList(), meals.toList())
                }

                is LoadResult.Loading -> {
                    if (result.data.isNotEmpty()) {
                        meals = result.data.map { it.toMealUi() }.toMutableList()
                        _uiState.value = MenuUiState.Success(
                            banners.toList(),
                            categories.toList(),
                            meals.toList(),
                            LoadInformation.Loading
                        )
                    }
                }

                is LoadResult.Error -> {
                    if (result.data.isNotEmpty()) {
                        meals = result.data.map { it.toMealUi() }.toMutableList()
                        _uiState.value = MenuUiState.Success(
                            banners.toList(),
                            categories.toList(),
                            meals.toList(),
                            LoadInformation.Error(result.e.getData())
                        )
                    } else _uiState.value = MenuUiState.Error(result.e.getData())
                }
            }
        }

    }

    fun changeCategory(index: Int) = viewModelScope.launch(dispatcher) {
        val item = categories[index]
        if (!item.selected) {
            for (i in 0..<categories.size) {
                if (categories[i] == item) categories[i] = categories[i].copy(selected = true)
                else categories[i] = categories[i].copy(selected = false)
            }
            _uiState.value = MenuUiState.Success(banners.toList(), categories.toList(), meals.toList())
        }
    }

    fun buy(index: Int) = viewModelScope.launch(dispatcher) {
        val item = meals[index]
        meals[index] = item.copy(purchased = !item.purchased)
        updateMealUseCase(meals[index].toMealDomain())
        _uiState.value = MenuUiState.Success(banners.toList(), categories.toList(), meals.toList())
    }

    override fun coup() = router.coup()
}

sealed interface MenuUiState {
    data object Loading : MenuUiState
    data class Error(val msg: Int) : MenuUiState

    data class Success(
        val banners: List<BannerUi>,
        val categories: List<CategoryUi>,
        val meals: List<MealUi>,
        val loadInformation: LoadInformation? = null
    ) : MenuUiState
}

sealed interface LoadInformation {
    data object Loading : LoadInformation
    data class Error(val msg: Int) : LoadInformation
}