package by.slowar.currenciesapp.presentation.currencieslist.dialog

import android.content.Context
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.DialogFragment
import androidx.fragment.app.viewModels
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.lifecycleScope
import androidx.lifecycle.repeatOnLifecycle
import by.slowar.currenciesapp.CurrenciesApp
import by.slowar.currenciesapp.databinding.FragmentCurrenciesDialogBinding
import by.slowar.currenciesapp.presentation.currencieslist.CurrenciesListResult
import by.slowar.currenciesapp.presentation.currencieslist.CurrenciesListViewModel
import by.slowar.currenciesapp.presentation.currencieslist.CurrenciesListViewModelFactory
import by.slowar.currenciesapp.utils.ui.showDismissibleSnackbar
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.launch
import javax.inject.Inject

class CurrenciesDialogFragment : DialogFragment() {

    private var _binding: FragmentCurrenciesDialogBinding? = null
    private val binding: FragmentCurrenciesDialogBinding
        get() = _binding!!

    @Inject
    lateinit var currenciesListViewModelFactory: CurrenciesListViewModelFactory
    private val currenciesViewModel: CurrenciesListViewModel by viewModels {
        currenciesListViewModelFactory
    }

    private lateinit var adapter: CurrenciesDialogAdapter

    private lateinit var itemClickListener: ItemClickListener

    override fun onAttach(context: Context) {
        super.onAttach(context)
        (requireActivity().applicationContext as CurrenciesApp).appComponent
            .currenciesComponent
            .inject(this)
        itemClickListener = parentFragment as ItemClickListener
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentCurrenciesDialogBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        adapter =
            CurrenciesDialogAdapter(onItemClickListener = itemClickListener::onCurrencyItemClick)
        binding.currenciesList.setHasFixedSize(true)
        binding.currenciesList.adapter = adapter

        viewLifecycleOwner.lifecycleScope.launch {
            viewLifecycleOwner.repeatOnLifecycle(Lifecycle.State.STARTED) {
                currenciesViewModel.currenciesListResult.collect(::handleCurrenciesListResult)
            }
        }

        binding.dismissButton.setOnClickListener {
            dismiss()
        }
    }

    private fun handleCurrenciesListResult(result: CurrenciesListResult) {
        when (result) {
            CurrenciesListResult.Idle -> {}
            CurrenciesListResult.Loading -> {}
            is CurrenciesListResult.Success -> {
                adapter.setNewItemsStates(result.result)
            }
            is CurrenciesListResult.Error -> {
                if (!result.error.isNullOrBlank()) {
                    showDismissibleSnackbar(binding.root, result.error)
                }
            }
        }
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }

    companion object {

        @JvmStatic
        fun newInstance() = CurrenciesDialogFragment()

        const val DIALOG_TAG = "CurrenciesDialogFragmentTag"
    }

    interface ItemClickListener {

        fun onCurrencyItemClick(symbol: String)
    }
}