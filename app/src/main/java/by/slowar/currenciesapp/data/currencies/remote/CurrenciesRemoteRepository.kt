package by.slowar.currenciesapp.data.currencies.remote

import by.slowar.currenciesapp.data.currencies.remote.models.CurrencyRateDto
import by.slowar.currenciesapp.utils.Result
import javax.inject.Inject

interface CurrenciesRemoteRepository {

    suspend fun getLatestCurrencies(
        baseCurrency: String,
        symbols: String = ""
    ): Result<List<CurrencyRateDto>, Throwable>
}

class CurrenciesRemoteRepositoryImpl @Inject constructor(
    private val currenciesRemoteDataSource: CurrenciesRemoteDataSource
) : CurrenciesRemoteRepository {

    override suspend fun getLatestCurrencies(
        baseCurrency: String,
        symbols: String
    ): Result<List<CurrencyRateDto>, Throwable> {
        return if (symbols.isBlank()) {
            currenciesRemoteDataSource.getLatestCurrencies(baseCurrency)
        } else {
            currenciesRemoteDataSource.getLatestCurrencies(baseCurrency, symbols)
        }
    }
}