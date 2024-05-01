package ru.ikom.feature_basket.presentation

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.flowWithLifecycle
import androidx.lifecycle.lifecycleScope
import kotlinx.coroutines.launch
import org.koin.androidx.viewmodel.ext.android.viewModel
import ru.ikom.common.BaseFragment
import ru.ikom.common.Router
import ru.ikom.feature_basket.databinding.FragmentBasketBinding

class BasketFragment : BaseFragment<FragmentBasketBinding, BasketViewModel>() {
    override val viewModel: BasketViewModel by viewModel()

    override fun bind(inflater: LayoutInflater, container: ViewGroup?): FragmentBasketBinding =
        FragmentBasketBinding.inflate(inflater, container, false)

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        val adapter = MealsAdapter(viewModel::update)

        binding.meals.adapter = adapter

        viewLifecycleOwner.lifecycleScope.launch {
            viewModel.uiState.flowWithLifecycle(lifecycle, Lifecycle.State.STARTED).collect {
                binding.tvInfo.visibility = if (it is BasketUiState.Empty) View.VISIBLE else View.GONE
                binding.containerError.visibility = if (it is BasketUiState.Error) View.VISIBLE else View.GONE
                binding.meals.visibility = if (it is BasketUiState.Success) View.VISIBLE else View.GONE

                if (it is BasketUiState.Success) {
                    adapter.submitList(it.meals)
                }

                if (it is BasketUiState.Error) {
                    binding.btnRetry.setOnClickListener {
                        viewModel.fetchMeals()
                    }
                }
            }
        }

        viewModel.fetchMeals()
    }

    companion object {
        fun newInstance() = BasketFragment()
    }
}