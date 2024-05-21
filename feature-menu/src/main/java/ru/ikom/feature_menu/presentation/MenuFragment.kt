package ru.ikom.feature_menu.presentation

import android.content.Context
import android.net.ConnectivityManager
import android.net.Network
import android.net.NetworkCapabilities
import android.net.NetworkRequest
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.content.ContextCompat
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.flowWithLifecycle
import androidx.lifecycle.lifecycleScope
import kotlinx.coroutines.channels.awaitClose
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.callbackFlow
import kotlinx.coroutines.launch
import org.koin.androidx.viewmodel.ext.android.viewModel
import ru.ikom.common.BaseFragment
import ru.ikom.feature_menu.R
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
            launch {
                viewModel.uiState.flowWithLifecycle(lifecycle).collect { state ->
                    binding.appBarLayout.visibility = if (state is MenuUiState.Success) View.VISIBLE else View.GONE
                    binding.content.visibility = if (state is MenuUiState.Success) View.VISIBLE else View.GONE
                    binding.shimmer.visibility = if (state is MenuUiState.Loading) View.VISIBLE else View.GONE
                    binding.containerError.visibility = if (state is MenuUiState.Error) View.VISIBLE else View.GONE
                    binding.tvMessageError.text = if (state is MenuUiState.Error) getString(state.msg) else ""

                    if (state is MenuUiState.Loading) binding.shimmer.startShimmer()
                    else binding.shimmer.stopShimmer()

                    if (state is MenuUiState.Error) {
                        launch {
                            requireContext().hasNetwork.flowWithLifecycle(lifecycle).collect {
                                viewModel.reconnected()
                            }
                        }
                    }

                    if (state is MenuUiState.Success) {
                        bannersAdapter.submitList(state.banners)
                        categoriesAdapter.submitList(state.categories)
                        mealsAdapter.submitList(state.meals)

                        binding.containerErrorWithData.visibility = if (state.loadInformation == null) View.GONE else View.VISIBLE

                        state.loadInformation?.let { error ->
                            when (error) {
                                is LoadInformation.Loading -> {
                                    binding.containerErrorWithData.setBackgroundColor(ContextCompat.getColor(requireContext(), android.R.color.holo_blue_dark) )
                                    binding.tvMessage.text = getString(R.string.load)
                                }
                                is LoadInformation.Error -> {
                                    binding.containerErrorWithData.setBackgroundColor(ContextCompat.getColor(requireContext(), android.R.color.holo_red_dark) )
                                    binding.tvMessage.text = getString(error.msg)
                                }
                            }
                        }
                    }
                }
            }
        }
    }

    companion object {
        fun newInstance() =
            MenuFragment()
    }
}

val Context.hasNetwork: Flow<Boolean>
    get() = callbackFlow {
        val network = object : ConnectivityManager.NetworkCallback() {

            override fun onAvailable(network: Network) {
                super.onAvailable(network)
                trySend(true)
            }
        }

        val networkRequest = NetworkRequest.Builder()
            .addCapability(NetworkCapabilities.NET_CAPABILITY_INTERNET)
            .build()

        val connectivityManager = getSystemService(Context.CONNECTIVITY_SERVICE) as ConnectivityManager
        connectivityManager.registerNetworkCallback(networkRequest, network)

        awaitClose {
            connectivityManager.unregisterNetworkCallback(network)
        }
    }