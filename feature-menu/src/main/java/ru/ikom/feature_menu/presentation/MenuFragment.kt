package ru.ikom.feature_menu.presentation

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.flowWithLifecycle
import androidx.lifecycle.lifecycleScope
import androidx.lifecycle.repeatOnLifecycle
import kotlinx.coroutines.launch
import org.koin.androidx.viewmodel.ext.android.viewModel
import ru.ikom.common.BaseFragment
import ru.ikom.feature_menu.databinding.FragmentMenuBinding

class MenuFragment : BaseFragment<FragmentMenuBinding, MenuViewModel>() {
    override val viewModel: MenuViewModel by viewModel()

    override fun bind(inflater: LayoutInflater, container: ViewGroup?): FragmentMenuBinding =
        FragmentMenuBinding.inflate(inflater, container, false)

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        val bannersAdapter = BannersAdapter()
        val categoriesAdapter = CategoriesAdapter(click = viewModel::changeCategory)
        val mealsAdapter = MealsAdapter(viewModel::buy)

        binding.banners.adapter = bannersAdapter
        binding.categories.adapter = categoriesAdapter
        binding.meals.adapter = mealsAdapter

        viewLifecycleOwner.lifecycleScope.launch {
            viewModel.uiState.flowWithLifecycle(lifecycle, Lifecycle.State.STARTED).collect {
                binding.appBarLayout.visibility = if (it is MenuUiState.Success) View.VISIBLE else View.GONE
                binding.content.visibility = if (it is MenuUiState.Success) View.VISIBLE else View.GONE
                binding.shimmer.visibility = if (it is MenuUiState.Loading) View.VISIBLE else View.GONE
                binding.containerError.visibility = if (it is MenuUiState.Error) View.VISIBLE else View.GONE
                binding.tvMessageError.text = if (it is MenuUiState.Error) getString(it.msg) else ""

                if (it is MenuUiState.Loading) binding.shimmer.startShimmer()
                else binding.shimmer.stopShimmer()

                if (it is MenuUiState.Success) {
                    bannersAdapter.submitList(it.banners)
                    categoriesAdapter.submitList(it.categories)
                    mealsAdapter.submitList(it.meals)
                }

                if (it is MenuUiState.Error) {
                    binding.btnRetry.setOnClickListener {
                        viewModel.fetchMeals()
                    }
                }
            }
        }

        viewModel.fetchMeals()
    }

    companion object {
        fun newInstance() =
            MenuFragment()
    }
}