package by.slowar.currenciesapp.data.currencies.mappers

import by.slowar.currenciesapp.data.currencies.local.entities.CurrencyAndMetadata
import by.slowar.currenciesapp.domain.CurrencyItem
import by.slowar.currenciesapp.presentation.currencieslist.CurrencyItemUiState

fun CurrencyItem.toUiState(onFavouriteClick: (symbol: String, isFavourite: Boolean) -> Unit) =
    CurrencyItemUiState(
        symbol = this.symbol,
        rate = this.rate.toString(),
        isFavourite = this.isFavourite,
        onFavouriteClick = onFavouriteClick
    )

fun CurrencyAndMetadata.toUiState(onFavouriteClick: (symbol: String, isFavourite: Boolean) -> Unit) =
    CurrencyItemUiState(
        symbol = this.currency.symbol,
        rate = this.currency.rate.toString(),
        isFavourite = this.metadata.isFavourite,
        onFavouriteClick = onFavouriteClick
    )