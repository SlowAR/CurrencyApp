package by.slowar.currenciesapp.presentation.currencieslist.sort

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.DialogFragment
import androidx.fragment.app.activityViewModels
import androidx.lifecycle.lifecycleScope
import by.slowar.currenciesapp.databinding.FragmentSortDialogBinding
import by.slowar.currenciesapp.utils.ui.getSelectedId
import by.slowar.currenciesapp.utils.ui.selectById
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.launch

class SortDialogFragment : DialogFragment() {

    private var _binding: FragmentSortDialogBinding? = null
    private val binding: FragmentSortDialogBinding
        get() = _binding!!

    private val sortViewModel: SortViewModel by activityViewModels()

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentSortDialogBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        viewLifecycleOwner.lifecycleScope.launch {
            sortViewModel.sortWayResult.collect { result ->
                binding.sortWayGroup.selectById(result.sortWayType.ordinal)
                binding.sortTypeGroup.selectById(if (result.isAscending) 0 else 1)
            }
        }

        binding.saveButton.setOnClickListener {
            val sortWay: SortWayType = SortWayType.values()[binding.sortWayGroup.getSelectedId()]
            val sortType: Boolean = binding.sortTypeGroup.getSelectedId() == 0
            sortViewModel.changeSortWay(sortWay, sortType)
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