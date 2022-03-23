package by.slowar.currenciesapp.data.currencies.local

import by.slowar.currenciesapp.data.currencies.local.entities.CurrencyAndMetadata
import by.slowar.currenciesapp.data.currencies.local.entities.CurrencyEntity
import by.slowar.currenciesapp.data.currencies.local.entities.CurrencyMetadataEntity
import javax.inject.Inject

interface CurrenciesLocalRepository {

    suspend fun insertAll(currencies: List<CurrencyEntity>)

    suspend fun getAllCurrencies(): List<CurrencyAndMetadata>

    suspend fun getFavouriteCurrencies(): List<CurrencyAndMetadata>

    suspend fun changeFavouriteCurrency(symbol: String, isFavourite: Boolean)

    suspend fun getMetadataBySymbol(symbol: String): CurrencyMetadataEntity?
}

class CurrenciesLocalRepositoryImpl @Inject constructor(
    private val currenciesLocalDataSource: CurrenciesLocalDataSource
) : CurrenciesLocalRepository {

    override suspend fun insertAll(currencies: List<CurrencyEntity>) {
        currenciesLocalDataSource.insertAll(currencies)
    }

    override suspend fun getAllCurrencies(): List<CurrencyAndMetadata> {
        return currenciesLocalDataSource.getAllCurrencies()
    }

    override suspend fun getFavouriteCurrencies(): List<CurrencyAndMetadata> {
        return currenciesLocalDataSource.getFavouriteCurrencies()
    }

    override suspend fun changeFavouriteCurrency(symbol: String, isFavourite: Boolean) {
        val metadata = currenciesLocalDataSource.getMetadataBySymbol(symbol)
        val newMetadata = metadata?.copy(isFavourite = isFavourite)
            ?: CurrencyMetadataEntity(symbol = symbol, isFavourite = isFavourite)
        currenciesLocalDataSource.insertMetadata(newMetadata)
    }

    override suspend fun getMetadataBySymbol(symbol: String): CurrencyMetadataEntity? {
        return currenciesLocalDataSource.getMetadataBySymbol(symbol)
    }
}