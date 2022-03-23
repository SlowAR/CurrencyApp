package by.slowar.currenciesapp.presentation.currencieslist

sealed class CurrenciesListResult {

    object Idle : CurrenciesListResult()

    object Loading : CurrenciesListResult()

    data class Success(val result: List<CurrencyItemUiState>) : CurrenciesListResult()

    data class Error(val error: String?) : CurrenciesListResult()
}