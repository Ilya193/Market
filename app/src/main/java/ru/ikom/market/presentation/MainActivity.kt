package ru.ikom.market.presentation

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.flowWithLifecycle
import androidx.lifecycle.lifecycleScope
import androidx.lifecycle.repeatOnLifecycle
import coil.imageLoader
import kotlinx.coroutines.launch
import org.koin.androidx.viewmodel.ext.android.viewModel
import ru.ikom.market.R
import ru.ikom.market.databinding.ActivityMainBinding

class MainActivity : AppCompatActivity() {
    private val binding: ActivityMainBinding by lazy {
        ActivityMainBinding.inflate(layoutInflater)
    }

    private val viewModel: MainViewModel by viewModel()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(binding.root)

        viewModel.init(savedInstanceState == null)

        lifecycleScope.launch {
            viewModel.read().flowWithLifecycle(lifecycle, Lifecycle.State.STARTED).collect {
                viewModel.read().collect {
                    it.show(supportFragmentManager, R.id.fragmentContainer)
                }
            }
        }

        binding.bottomNav.setOnItemSelectedListener {
            true
        }
    }
}