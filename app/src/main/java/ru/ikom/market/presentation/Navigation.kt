package ru.ikom.market.presentation

import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentManager
import androidx.fragment.app.commit
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import ru.ikom.feature_basket.presentation.BasketFragment
import ru.ikom.feature_basket.presentation.BasketRouter
import ru.ikom.feature_menu.presentation.MenuFragment
import ru.ikom.feature_menu.presentation.MenuRouter

interface Navigation<T> : MainRouter {
    fun read(): StateFlow<T>
    fun update(value: T)

    class Base : Navigation<Screen>, MenuRouter, BasketRouter {
        private val screen = MutableStateFlow<Screen>(Screen.Empty)

        override fun read(): StateFlow<Screen> = screen

        override fun update(value: Screen) {
            screen.value = value
        }

        override fun openMenu() {
            update(MenuScreen())
        }

        override fun openBasket() {
            update(BasketScreen())
        }

        override fun coup() {
            update(Screen.Coup)
        }
    }
}

interface Screen {
    fun show(supportFragmentManager: FragmentManager, container: Int) = Unit

    abstract class Replace(
        private val fragment: Fragment
    ) : Screen {
        override fun show(supportFragmentManager: FragmentManager, container: Int) {
            supportFragmentManager.commit {
                replace(container, fragment)
            }
        }
    }

    data object Coup : Screen

    data object Empty : Screen
}

class MenuScreen : Screen.Replace(MenuFragment.newInstance())
class BasketScreen : Screen.Replace(BasketFragment.newInstance())