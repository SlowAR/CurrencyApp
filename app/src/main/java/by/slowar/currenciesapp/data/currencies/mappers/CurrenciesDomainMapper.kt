package by.slowar.currenciesapp.data.currencies.mappers

import by.slowar.currenciesapp.data.currencies.local.entities.CurrencyAndMetadata
import by.slowar.currenciesapp.domain.CurrencyItem

fun CurrencyAndMetadata.toModel() = CurrencyItem(
    symbol = this.currency.symbol,
    rate = this.currency.rate,
    isFavourite = this.metadata.isFavourite
)