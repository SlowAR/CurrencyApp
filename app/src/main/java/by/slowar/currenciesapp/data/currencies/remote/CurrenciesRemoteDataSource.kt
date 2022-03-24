package by.slowar.currenciesapp.data.currencies.remote

import by.slowar.currenciesapp.data.currencies.remote.models.CurrencyRateDto
import by.slowar.currenciesapp.utils.Result
import by.slowar.currenciesapp.utils.executeCatching
import javax.inject.Inject

interface CurrenciesRemoteDataSource {

    suspend fun getLatestCurrencies(
        baseCurrency: String
    ): Result<List<CurrencyRateDto>, Throwable>

    suspend fun getLatestCurrencies(
        baseCurrency: String,
        symbols: String = ""
    ): Result<List<CurrencyRateDto>, Throwable>
}

class CurrenciesRemoteDataSourceImpl @Inject constructor(
    private val currenciesApi: CurrenciesApi
) : CurrenciesRemoteDataSource {

    override suspend fun getLatestCurrencies(baseCurrency: String): Result<List<CurrencyRateDto>, Throwable> {
        return executeCatching {
            currenciesApi.getLatestCurrencies(baseCurrency).rates!!
        }
    }

    override suspend fun getLatestCurrencies(
        baseCurrency: String,
        symbols: String
    ): Result<List<CurrencyRateDto>, Throwable> {
        return executeCatching {
            currenciesApi.getLatestCurrencies(baseCurrency, symbols).rates!!
        }
    }
}