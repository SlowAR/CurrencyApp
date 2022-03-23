package by.slowar.currenciesapp.data.currencies.mappers

import by.slowar.currenciesapp.domain.CurrencyItem
import by.slowar.currenciesapp.presentation.currencieslist.CurrencyItemUiState

fun CurrencyItem.toUiState(onFavouriteClick: (symbol: String, isFavourite: Boolean) -> Unit) =
    CurrencyItemUiState(
        symbol = this.symbol,
        rate = this.rate.toString(),
        isFavourite = this.isFavourite,
        onFavouriteClick = onFavouriteClick
    )