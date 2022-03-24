package by.slowar.currenciesapp.data.currencies.local

import by.slowar.currenciesapp.data.currencies.local.entities.CurrencyAndMetadata
import by.slowar.currenciesapp.data.currencies.local.entities.CurrencyEntity
import by.slowar.currenciesapp.data.currencies.local.entities.CurrencyMetadataEntity
import by.slowar.currenciesapp.utils.Result
import by.slowar.currenciesapp.utils.executeCatching
import javax.inject.Inject

interface CurrenciesLocalRepository {

    suspend fun insertAll(currencies: List<CurrencyEntity>)

    suspend fun getAllCurrencies(): Result<List<CurrencyAndMetadata>, Throwable>

    suspend fun getFavouriteCurrencies(): Result<List<CurrencyAndMetadata>, Throwable>

    suspend fun changeFavouriteCurrency(
        symbol: String,
        isFavourite: Boolean
    ): Result<CurrencyAndMetadata?, Throwable>

    suspend fun getMetadataBySymbol(symbol: String): Result<CurrencyMetadataEntity?, Throwable>
}

class CurrenciesLocalRepositoryImpl @Inject constructor(
    private val currenciesLocalDataSource: CurrenciesLocalDataSource
) : CurrenciesLocalRepository {

    override suspend fun insertAll(currencies: List<CurrencyEntity>) {
        currenciesLocalDataSource.insertAll(currencies)
    }

    override suspend fun getAllCurrencies(): Result<List<CurrencyAndMetadata>, Throwable> {
        return executeCatching {
            currenciesLocalDataSource.getAllCurrencies()
        }
    }

    override suspend fun getFavouriteCurrencies(): Result<List<CurrencyAndMetadata>, Throwable> {
        return executeCatching {
            currenciesLocalDataSource.getFavouriteCurrencies()
        }
    }

    override suspend fun changeFavouriteCurrency(
        symbol: String,
        isFavourite: Boolean
    ): Result<CurrencyAndMetadata?, Throwable> {
        return executeCatching {
            val metadata = currenciesLocalDataSource.getMetadataBySymbol(symbol)
            val newMetadata = metadata?.copy(isFavourite = isFavourite)
                ?: CurrencyMetadataEntity(symbol = symbol, isFavourite = isFavourite)
            currenciesLocalDataSource.insertMetadata(newMetadata)
            currenciesLocalDataSource.getCurrencyBySymbol(symbol)
        }
    }

    override suspend fun getMetadataBySymbol(symbol: String): Result<CurrencyMetadataEntity?, Throwable> {
        return executeCatching {
            currenciesLocalDataSource.getMetadataBySymbol(symbol)
        }
    }
}