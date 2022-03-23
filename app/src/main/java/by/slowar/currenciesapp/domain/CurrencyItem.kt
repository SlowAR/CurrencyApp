package by.slowar.currenciesapp.domain

data class CurrencyItem(
    val symbol: String,
    val rate: Double,
    val isFavourite: Boolean
)