package by.slowar.currenciesapp.presentation.currencieslist

import android.content.Context
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.lifecycleScope
import androidx.lifecycle.repeatOnLifecycle
import by.slowar.currenciesapp.CurrenciesApp
import by.slowar.currenciesapp.databinding.FragmentCurrenciesListBinding
import by.slowar.currenciesapp.presentation.currencieslist.favourite.FavouriteCurrencyResult
import by.slowar.currenciesapp.utils.ui.showDismissibleSnackbar
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.launch
import javax.inject.Inject

class CurrenciesListFragment : Fragment() {

    private var _binding: FragmentCurrenciesListBinding? = null
    private val binding: FragmentCurrenciesListBinding
        get() = _binding!!

    @Inject
    lateinit var currenciesListViewModelFactory: CurrenciesListViewModelFactory
    private val currenciesViewModel: CurrenciesListViewModel by viewModels {
        currenciesListViewModelFactory
    }

    private lateinit var currenciesAdapter: CurrenciesListAdapter

    override fun onAttach(context: Context) {
        super.onAttach(context)
        (requireActivity().applicationContext as CurrenciesApp).appComponent
            .currenciesComponent
            .inject(this)
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentCurrenciesListBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        currenciesAdapter = CurrenciesListAdapter()
        binding.currenciesRecyclerView.adapter = currenciesAdapter

        viewLifecycleOwner.lifecycleScope.launch {
            viewLifecycleOwner.repeatOnLifecycle(Lifecycle.State.STARTED) {
                currenciesViewModel.currenciesListResult.collect(::handleCurrenciesListResult)
            }
        }

        viewLifecycleOwner.lifecycleScope.launch {
            currenciesViewModel.favouriteCurrencyResult.collect(::handleFavouriteCurrencyResult)
        }

        currenciesViewModel.getLatestCurrencyRates("EUR")
    }

    private fun handleCurrenciesListResult(result: CurrenciesListResult) {
        when (result) {
            CurrenciesListResult.Idle -> {
                binding.swipeRefreshLayout.isRefreshing = false
            }
            CurrenciesListResult.Loading -> {
                binding.swipeRefreshLayout.isRefreshing = true
            }
            is CurrenciesListResult.Success -> {
                currenciesAdapter.setNewItemsStates(result.result)
                binding.swipeRefreshLayout.isRefreshing = false
            }
            is CurrenciesListResult.Error -> {
                if (!result.error.isNullOrBlank()) {
                    showDismissibleSnackbar(binding.root, result.error)
                }
                binding.swipeRefreshLayout.isRefreshing = false
            }
        }
    }

    private fun handleFavouriteCurrencyResult(result: FavouriteCurrencyResult) {
        when (result) {
            FavouriteCurrencyResult.Idle -> {}
            FavouriteCurrencyResult.Changing -> {}
            is FavouriteCurrencyResult.Success -> {
                currenciesAdapter.changeItemState(result.newItemUiState)
            }
            is FavouriteCurrencyResult.Error -> {
                showDismissibleSnackbar(binding.root, result.errorId)
            }
        }
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }

    companion object {

        @JvmStatic
        fun newInstance() = CurrenciesListFragment()
    }
}