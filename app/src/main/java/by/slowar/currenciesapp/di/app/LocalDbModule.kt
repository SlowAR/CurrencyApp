package by.slowar.currenciesapp.di.app

import android.app.Application
import androidx.room.Room
import by.slowar.currenciesapp.data.core.db.CurrenciesDb
import dagger.Module
import dagger.Provides

@Module
object LocalDbModule {

    @Provides
    @AppScope
    fun provideCurrenciesDatabase(appContext: Application): CurrenciesDb {
        return Room.databaseBuilder(appContext, CurrenciesDb::class.java, "currencies_database")
            .build()
    }

    @Provides
    fun provideCurrenciesDao(currenciesDb: CurrenciesDb) = currenciesDb.currenciesDao()
}