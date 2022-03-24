package by.slowar.currenciesapp.domain

import by.slowar.currenciesapp.data.currencies.local.CurrenciesLocalRepository
import by.slowar.currenciesapp.data.currencies.mappers.toEntity
import by.slowar.currenciesapp.data.currencies.mappers.toModel
import by.slowar.currenciesapp.data.currencies.remote.CurrenciesRemoteRepository
import by.slowar.currenciesapp.utils.Result
import by.slowar.currenciesapp.utils.doOnSuccess
import by.slowar.currenciesapp.utils.mapSuccess
import javax.inject.Inject

class LoadAndStoreCurrenciesUseCase @Inject constructor(
    private val currenciesRemoteRepository: CurrenciesRemoteRepository,
    private val currenciesLocalRepository: CurrenciesLocalRepository
) {

    suspend fun execute(
        baseCurrency: String,
        symbols: String = "",
        forceRefresh: Boolean = true
    ): Result<List<CurrencyItem>, Throwable> {
        if(forceRefresh) {
            currenciesRemoteRepository.getLatestCurrencies(baseCurrency, symbols)
                .mapSuccess { dtoList -> dtoList.map { it.toEntity() } }
                .doOnSuccess { entities -> currenciesLocalRepository.insertAll(entities) }
        }

        return currenciesLocalRepository.getAllCurrencies().mapSuccess { dtoList ->
            dtoList.map { it.toModel() }
        }
    }
}