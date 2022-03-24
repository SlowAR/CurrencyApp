package by.slowar.currenciesapp.presentation.currencieslist.sort

import androidx.lifecycle.ViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow

class SortViewModel : ViewModel() {

    private val _sortWayResult = MutableStateFlow(SortWayResult.DEFAULT)
    val sortWayResult: StateFlow<SortWayResult> = _sortWayResult

    fun changeSortWay(sortWayType: SortWayType, isAscending: Boolean) {
        _sortWayResult.value = SortWayResult(sortWayType, isAscending)
    }
}