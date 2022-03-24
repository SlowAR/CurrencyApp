package by.slowar.currenciesapp.presentation.currencieslist.sort

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.DialogFragment
import by.slowar.currenciesapp.databinding.FragmentSortDialogBinding
import by.slowar.currenciesapp.presentation.currencieslist.CurrenciesListResult
import by.slowar.currenciesapp.utils.ui.showDismissibleSnackbar

class SortDialogFragment : DialogFragment() {

    private var _binding: FragmentSortDialogBinding? = null
    private val binding: FragmentSortDialogBinding
        get() = _binding!!

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentSortDialogBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        binding.saveButton.setOnClickListener {
            dismiss()
        }
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }

    companion object {

        @JvmStatic
        fun newInstance() = SortDialogFragment()

        const val DIALOG_TAG = "SortDialogFragmentTag"
    }
}