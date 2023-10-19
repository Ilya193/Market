package ru.kraz.market.presentation

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import com.bumptech.glide.Glide
import com.elveum.elementadapter.SimpleBindingAdapter
import com.elveum.elementadapter.simpleAdapter
import org.koin.androidx.viewmodel.ext.android.sharedViewModel
import ru.kraz.market.R
import ru.kraz.market.databinding.FragmentProductsBinding
import ru.kraz.market.databinding.ProductLayoutBinding


class ProductsFragment : Fragment(), OnClickListener {
    private var _binding: FragmentProductsBinding? = null
    private val binding: FragmentProductsBinding
        get() = _binding!!

    private lateinit var adapter: SimpleBindingAdapter<ProductUi>

    private val viewModel: ProductsViewModel by sharedViewModel()

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?,
    ): View? {
        _binding = FragmentProductsBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        settingRecyclerView()
        settingViewModel()
    }

    private fun settingRecyclerView() {
        //adapter = ProductsAdapter(requireContext(), this)
        //binding.rvProducts.adapter = adapter
        adapter = simpleAdapter<ProductUi, ProductLayoutBinding> {
            areItemsSame = { old, new ->
                old.id == new.id
            }

            bind { item ->
                tvName.text = item.name

                Glide.with(requireContext())
                    .load(item.url)
                    .centerCrop()
                    .into(image)
            }

            listeners {
                root.onClick {
                    onClick(it)
                }
            }
        }
        binding.rvProducts.adapter = adapter
        binding.rvProducts.setHasFixedSize(true)
    }

    private fun settingViewModel() {
        viewModel.fetchProducts()
        viewModel.resultProducts.observe(viewLifecycleOwner) {
            when (it) {
                is ProductUiState.Success -> renderSuccess(it)
                is ProductUiState.Error -> renderError(it)
                is ProductUiState.Loading -> renderLoading()
            }
        }
    }

    private fun renderSuccess(state: ProductUiState.Success) {
        binding.containerError.visibility = View.GONE

        binding.shimmer.stopShimmerAnimation()
        binding.shimmer.visibility = View.GONE
        binding.rvProducts.visibility = View.VISIBLE
        adapter.submitList(state.data)
    }

    private fun renderError(state: ProductUiState.Error) {
        binding.rvProducts.visibility = View.GONE

        binding.shimmer.stopShimmerAnimation()
        binding.shimmer.visibility = View.GONE
        binding.containerError.visibility = View.VISIBLE
        binding.tvError.text = state.message
        binding.btnRetry.setOnClickListener {
            viewModel.fetchProducts()
        }
    }

    private fun renderLoading() {
        binding.rvProducts.visibility = View.GONE
        binding.containerError.visibility = View.GONE
        binding.shimmer.startShimmerAnimation()
        binding.shimmer.visibility = View.VISIBLE
    }

    override fun onClick(product: ProductUi) {
        viewModel.setCurrentProduct(product)

        parentFragmentManager.beginTransaction()
            .replace(R.id.fragmentContainer, ProductFragment.newInstance())
            .addToBackStack(null)
            .commit()
    }

    override fun onClick() {
        viewModel.fetchProducts()
    }

    override fun onDestroyView() {
        _binding = null
        super.onDestroyView()
    }
}