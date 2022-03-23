package by.slowar.currenciesapp.di.currencies

import by.slowar.currenciesapp.data.currencies.local.CurrenciesLocalDataSource
import by.slowar.currenciesapp.data.currencies.local.CurrenciesLocalDataSourceImpl
import by.slowar.currenciesapp.data.currencies.remote.CurrenciesRemoteDataSource
import by.slowar.currenciesapp.data.currencies.remote.CurrenciesRemoteDataSourceImpl
import dagger.Binds
import dagger.Module

@Module
interface CurrenciesDataSourceModule {

    @Binds
    fun bindCurrenciesLocalDataSource(localDataSource: CurrenciesLocalDataSourceImpl): CurrenciesLocalDataSource

    @Binds
    fun bindCurrenciesRemoteDataSource(remoteDataSource: CurrenciesRemoteDataSourceImpl): CurrenciesRemoteDataSource
}