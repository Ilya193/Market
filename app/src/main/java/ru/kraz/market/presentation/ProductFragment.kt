package ru.kraz.market.presentation

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.DividerItemDecoration
import com.bumptech.glide.Glide
import org.koin.androidx.viewmodel.ext.android.sharedViewModel
import org.koin.androidx.viewmodel.ext.android.viewModel
import ru.kraz.market.R
import ru.kraz.market.core.log
import ru.kraz.market.databinding.FragmentProductBinding

class ProductFragment : Fragment() {
    private var _binding: FragmentProductBinding? = null
    private val binding: FragmentProductBinding
        get() = _binding!!

    private lateinit var adapter: ReviewsAdapter

    private val viewModel: ProductsViewModel by sharedViewModel()

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?,
    ): View? {
        _binding = FragmentProductBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        settingRecyclerView()
        settingViewModel()
        settingClickListener()
    }

    private fun settingClickListener() {
        binding.icSend.setOnClickListener {
            val textReview = binding.etReview.text.toString()
            if (textReview.isNotEmpty()) {
                viewModel.sendReview(textReview)
                binding.etReview.setText("")
            }
        }
    }

    private fun settingRecyclerView() {
        adapter = ReviewsAdapter(requireContext())
        binding.rvReviews.adapter = adapter
        binding.rvReviews.setHasFixedSize(true)
    }

    private fun settingViewModel() {
        viewModel.currentProduct.observe(viewLifecycleOwner) {
            Glide.with(requireContext())
                .load(it.url)
                .into(binding.image)

            binding.tvName.text = it.name
            binding.tvDescription.text = it.description
        }

        viewModel.resultReviews.observe(viewLifecycleOwner) { data ->
            data.getContentOrNot {
                when (it) {
                    is ReviewUiState.Success -> renderSuccess(it)
                    is ReviewUiState.Error -> renderError(it)
                    is ReviewUiState.Loading -> renderLoading()
                }
            }
        }

        viewModel.fetchReviews()
    }

    private fun renderSuccess(state: ReviewUiState.Success) {
        binding.apply {
            loading.visibility = View.GONE
            containerError.visibility = View.GONE

            mainContent.visibility = View.VISIBLE
        }

        adapter.submitList(state.data)
    }

    private fun renderError(state: ReviewUiState.Error) {
        binding.apply {
            loading.visibility = View.GONE
            mainContent.visibility = View.GONE

            tvError.text = state.message
            containerError.visibility = View.VISIBLE
            btnRetry.setOnClickListener {
                viewModel.fetchReviews()
            }
        }
    }

    private fun renderLoading() {
        binding.apply {
            mainContent.visibility = View.GONE
            containerError.visibility = View.GONE

            loading.visibility = View.VISIBLE
        }
    }

    override fun onDestroyView() {
        _binding = null
        super.onDestroyView()
    }

    companion object {
        fun newInstance() =
            ProductFragment()
    }
}