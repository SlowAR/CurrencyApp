package by.slowar.currenciesapp.di.app

import by.slowar.currenciesapp.BuildConfig
import by.slowar.currenciesapp.data.core.CurrenciesConfigData
import dagger.Module
import dagger.Provides

@Module
object ConfigsModule {

    @Provides
    @AppScope
    fun provideConfigData() = CurrenciesConfigData(
        BuildConfig.BASE_URL,
        BuildConfig.EXCHANGE_RATES_API_KEY
    )
}