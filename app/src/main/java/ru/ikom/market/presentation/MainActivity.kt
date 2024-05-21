package ru.ikom.market.presentation

import android.os.Bundle
import android.view.View
import androidx.appcompat.app.AppCompatActivity
import androidx.core.content.ContextCompat
import androidx.core.splashscreen.SplashScreen.Companion.installSplashScreen
import androidx.core.view.WindowCompat
import androidx.fragment.app.Fragment
import androidx.fragment.app.commit
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.flowWithLifecycle
import androidx.lifecycle.lifecycleScope
import kotlinx.coroutines.launch
import org.koin.androidx.viewmodel.ext.android.viewModel
import ru.ikom.feature_basket.presentation.BasketFragment
import ru.ikom.feature_menu.presentation.MenuFragment
import ru.ikom.market.R
import ru.ikom.market.databinding.ActivityMainBinding


class MainActivity : AppCompatActivity() {
    private val binding: ActivityMainBinding by lazy {
        ActivityMainBinding.inflate(layoutInflater)
    }

    private val viewModel: MainViewModel by viewModel()

    override fun onCreate(savedInstanceState: Bundle?) {
        installSplashScreen()
        super.onCreate(savedInstanceState)
        setContentView(binding.root)

        viewModel.init(savedInstanceState == null)

        lifecycleScope.launch {
            launch {
                viewModel.readScreen().flowWithLifecycle(lifecycle).collect {
                    viewModel.readScreen().collect {
                        it.show(supportFragmentManager, R.id.fragmentContainer)
                    }
                }
            }
            launch {
                viewModel.uiState.flowWithLifecycle(lifecycle).collect {
                    if (it == 0) binding.bottomNav.removeBadge(R.id.basket)
                    else {
                        val badge = binding.bottomNav.getOrCreateBadge(R.id.basket)
                        badge.badgeTextColor = ContextCompat.getColor(this@MainActivity, R.color.white)
                        badge.backgroundColor = ContextCompat.getColor(this@MainActivity, R.color.colorPrimary)
                        badge.number = it
                    }
                }
            }
        }

        binding.bottomNav.setOnItemSelectedListener {
            if (binding.bottomNav.selectedItemId != it.itemId) {
                when (it.itemId) {
                    R.id.menu -> viewModel.openMenu()
                    R.id.profile -> {}
                    R.id.basket -> viewModel.openBasket()
                }
            }
            true
        }
    }
}