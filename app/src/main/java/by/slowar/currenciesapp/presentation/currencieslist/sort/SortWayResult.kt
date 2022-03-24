package by.slowar.currenciesapp.presentation.currencieslist.sort

data class SortWayResult(
    val sortWayType: SortWayType = SortWayType.Alphabetical,
    val isAscending: Boolean = true
) {
    companion object {
        val DEFAULT = SortWayResult()
    }
}