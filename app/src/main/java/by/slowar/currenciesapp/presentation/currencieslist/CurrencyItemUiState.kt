package by.slowar.currenciesapp.presentation.currencieslist

data class CurrencyItemUiState(
    val symbol: String,
    val rate: String,
    val isFavourite: Boolean,
    val onFavouriteClick: (symbol: String, isFavourite: Boolean) -> Unit
)