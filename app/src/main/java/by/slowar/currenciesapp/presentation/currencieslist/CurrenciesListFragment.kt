package by.slowar.currenciesapp.presentation.currencieslist

import android.content.Context
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.fragment.app.activityViewModels
import androidx.fragment.app.viewModels
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.lifecycleScope
import androidx.lifecycle.repeatOnLifecycle
import by.slowar.currenciesapp.CurrenciesApp
import by.slowar.currenciesapp.databinding.FragmentCurrenciesListBinding
import by.slowar.currenciesapp.presentation.currencieslist.dialog.CurrenciesDialogFragment
import by.slowar.currenciesapp.presentation.currencieslist.favourite.FavouriteCurrencyResult
import by.slowar.currenciesapp.utils.ui.showDismissibleSnackbar
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.launch
import javax.inject.Inject

class CurrenciesListFragment : Fragment(), CurrenciesDialogFragment.ItemClickListener {

    private var _binding: FragmentCurrenciesListBinding? = null
    private val binding: FragmentCurrenciesListBinding
        get() = _binding!!

    @Inject
    lateinit var currenciesListViewModelFactory: CurrenciesListViewModelFactory
    private val currenciesViewModel: CurrenciesListViewModel by activityViewModels {
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
        binding.currenciesRecyclerView.setHasFixedSize(true)
        binding.currenciesRecyclerView.adapter = currenciesAdapter
        binding.baseCurrencyText.text = "EUR"

        val showOnlyFavourites = arguments?.getBoolean(ONLY_FAVOURITES, false) ?: false

        viewLifecycleOwner.lifecycleScope.launch {
            viewLifecycleOwner.repeatOnLifecycle(Lifecycle.State.STARTED) {
                if (showOnlyFavourites) {
                    currenciesViewModel.favouriteCurrenciesResult.collect(::handleCurrenciesListResult)
                } else {
                    currenciesViewModel.currenciesListResult.collect(::handleCurrenciesListResult)
                }
            }
        }

        viewLifecycleOwner.lifecycleScope.launch {
            currenciesViewModel.favouriteCurrencyChangeResult.collect(::handleFavouriteCurrencyResult)
        }

        binding.baseCurrencyText.setOnClickListener {
            CurrenciesDialogFragment.newInstance()
                .show(childFragmentManager, CurrenciesDialogFragment.DIALOG_TAG)
        }

        if (showOnlyFavourites) {
            currenciesViewModel.getFavouriteCurrencies()
        } else {
            currenciesViewModel.getLatestCurrencyRates(binding.baseCurrencyText.text.toString())
        }
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
                if (!result.error.isNullOrBlank()) {
                    showDismissibleSnackbar(binding.root, result.error)
                }
            }
        }
    }

    override fun onCurrencyItemClick(symbol: String) {
        binding.baseCurrencyText.text = symbol
        currenciesViewModel.getLatestCurrencyRates(symbol)
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }

    companion object {

        const val ONLY_FAVOURITES = "OnlyFavouritesCurrencies"

        @JvmStatic
        fun newInstance(showOnlyFavourites: Boolean = false) = CurrenciesListFragment().apply {
            arguments = Bundle().apply {
                putBoolean(ONLY_FAVOURITES, showOnlyFavourites)
            }
        }
    }
}