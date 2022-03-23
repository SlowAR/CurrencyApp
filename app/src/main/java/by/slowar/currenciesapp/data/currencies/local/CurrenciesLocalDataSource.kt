package by.slowar.currenciesapp.data.currencies.local

import by.slowar.currenciesapp.data.currencies.local.entities.CurrencyAndMetadata
import by.slowar.currenciesapp.data.currencies.local.entities.CurrencyEntity
import by.slowar.currenciesapp.data.currencies.local.entities.CurrencyMetadataEntity
import javax.inject.Inject

interface CurrenciesLocalDataSource {

    suspend fun insertAll(currencies: List<CurrencyEntity>)

    suspend fun getAllCurrencies(): List<CurrencyAndMetadata>

    suspend fun getFavouriteCurrencies(): List<CurrencyAndMetadata>

    suspend fun insertMetadata(currencyMetadataEntity: CurrencyMetadataEntity): Long

    suspend fun getCurrencyBySymbol(symbol: String): CurrencyAndMetadata?

    suspend fun getMetadataBySymbol(symbol: String): CurrencyMetadataEntity?
}

class CurrenciesLocalDataSourceImpl @Inject constructor(
    private val currenciesDao: CurrenciesDao,
) : CurrenciesLocalDataSource {

    override suspend fun insertAll(currencies: List<CurrencyEntity>) {
        currenciesDao.insertAll(currencies)
    }

    override suspend fun getAllCurrencies(): List<CurrencyAndMetadata> {
        return currenciesDao.getAllCurrencies()
    }

    override suspend fun getFavouriteCurrencies(): List<CurrencyAndMetadata> {
        return currenciesDao.getFavouriteCurrencies()
    }

    override suspend fun insertMetadata(currencyMetadataEntity: CurrencyMetadataEntity): Long {
        return currenciesDao.insertMetadata(currencyMetadataEntity)
    }

    override suspend fun getCurrencyBySymbol(symbol: String): CurrencyAndMetadata? {
        return currenciesDao.getCurrencyBySymbol(symbol)
    }

    override suspend fun getMetadataBySymbol(symbol: String): CurrencyMetadataEntity? {
        return currenciesDao.getMetadataBySymbol(symbol)
    }
}