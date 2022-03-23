package by.slowar.currenciesapp.presentation.currencieslist.favourite

import androidx.annotation.StringRes
import by.slowar.currenciesapp.presentation.currencieslist.CurrencyItemUiState

sealed class FavouriteCurrencyResult {

    object Idle : FavouriteCurrencyResult()

    object Changing : FavouriteCurrencyResult()

    data class Success(val newItemUiState: CurrencyItemUiState) : FavouriteCurrencyResult()

    data class Error(@StringRes val errorId: Int) : FavouriteCurrencyResult()
}
