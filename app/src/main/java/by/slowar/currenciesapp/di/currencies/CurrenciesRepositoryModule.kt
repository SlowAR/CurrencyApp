package by.slowar.currenciesapp.di.currencies

import by.slowar.currenciesapp.data.currencies.local.CurrenciesLocalRepository
import by.slowar.currenciesapp.data.currencies.local.CurrenciesLocalRepositoryImpl
import by.slowar.currenciesapp.data.currencies.remote.CurrenciesRemoteRepository
import by.slowar.currenciesapp.data.currencies.remote.CurrenciesRemoteRepositoryImpl
import dagger.Binds
import dagger.Module

@Module
interface CurrenciesRepositoryModule {

    @Binds
    fun bindCurrenciesLocalRepository(localRepository: CurrenciesLocalRepositoryImpl): CurrenciesLocalRepository

    @Binds
    fun bindCurrenciesRemoteRepository(remoteRepository: CurrenciesRemoteRepositoryImpl): CurrenciesRemoteRepository
}