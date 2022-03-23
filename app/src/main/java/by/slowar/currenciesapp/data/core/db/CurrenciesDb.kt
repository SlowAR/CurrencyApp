package by.slowar.currenciesapp.data.core.db

import androidx.room.Database
import androidx.room.RoomDatabase
import by.slowar.currenciesapp.data.currencies.local.CurrenciesDao
import by.slowar.currenciesapp.data.currencies.local.entities.CurrencyEntity
import by.slowar.currenciesapp.data.currencies.local.entities.CurrencyMetadataEntity

@Database(
    entities = [CurrencyEntity::class, CurrencyMetadataEntity::class],
    version = 1,
    exportSchema = true
)
abstract class CurrenciesDb : RoomDatabase() {

    abstract fun currenciesDao(): CurrenciesDao
}