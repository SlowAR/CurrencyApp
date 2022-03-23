package by.slowar.currenciesapp.data.currencies.local

import androidx.room.*
import by.slowar.currenciesapp.data.currencies.local.entities.CurrencyAndMetadata
import by.slowar.currenciesapp.data.currencies.local.entities.CurrencyEntity
import by.slowar.currenciesapp.data.currencies.local.entities.CurrencyMetadataEntity

@Dao
interface CurrenciesDao {

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertAll(currencies: List<CurrencyEntity>)

    @Transaction
    @Query("SELECT * FROM ${CurrencyEntity.TABLE_NAME}")
    suspend fun getAllCurrencies(): List<CurrencyAndMetadata>

    @Transaction
    @Query("SELECT * FROM ${CurrencyEntity.TABLE_NAME} " +
            "INNER JOIN ${CurrencyMetadataEntity.TABLE_NAME} " +
            "ON ${CurrencyEntity.TABLE_NAME}.${CurrencyEntity.SYMBOL} = ${CurrencyMetadataEntity.TABLE_NAME}.${CurrencyMetadataEntity.SYMBOL} " +
            "WHERE ${CurrencyMetadataEntity.IS_FAVOURITE}=1")
    suspend fun getFavouriteCurrencies(): List<CurrencyAndMetadata>

    @Transaction
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertMetadata(currencyMetadataEntity: CurrencyMetadataEntity)

    @Query("SELECT * FROM ${CurrencyMetadataEntity.TABLE_NAME} WHERE ${CurrencyMetadataEntity.SYMBOL}=:symbol")
    suspend fun getMetadataBySymbol(symbol: String): CurrencyMetadataEntity?
}