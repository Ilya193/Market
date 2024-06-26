package ru.ikom.market.presentation

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch
import ru.ikom.database.basket.BasketDao

class MainViewModel(
    private val navigation: Navigation<Screen>,
    private val dao: BasketDao,
    private val dispatcher: CoroutineDispatcher = Dispatchers.IO
) : ViewModel(), MainRouter {

    private val _uiState = MutableStateFlow(0)
    val uiState: StateFlow<Int> get() = _uiState

    init {
        viewModelScope.launch(dispatcher) {
            dao.fetchMealsSize().collect {
                _uiState.value = it
            }
        }
    }

    fun init(first: Boolean) {
        if (first) navigation.update(MenuScreen())
    }

    fun readScreen(): StateFlow<Screen> = navigation.read()

    override fun openMenu() = navigation.openMenu()

    override fun openBasket() = navigation.openBasket()

    override fun coup() = navigation.coup()
}