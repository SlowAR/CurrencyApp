package by.slowar.currenciesapp.data.currencies.mappers

import by.slowar.currenciesapp.data.currencies.local.entities.CurrencyEntity
import by.slowar.currenciesapp.data.currencies.remote.models.CurrencyRateDto

fun CurrencyRateDto.toEntity() = CurrencyEntity(
    symbol = this.symbol,
    rate = this.rate
)