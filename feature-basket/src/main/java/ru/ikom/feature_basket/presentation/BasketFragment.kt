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
import ru.ikom.feature_basket.databinding.FragmentBasketBinding

class BasketFragment : Fragment() {
    private var _binding: FragmentBasketBinding? = null
    private val binding: FragmentBasketBinding get() = _binding!!

    private val viewModel: BasketViewModel by viewModel()

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        _binding = FragmentBasketBinding.inflate(inflater, container, false)
        return binding.root
    }

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

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }

    companion object {
        fun newInstance() = BasketFragment()
    }
}