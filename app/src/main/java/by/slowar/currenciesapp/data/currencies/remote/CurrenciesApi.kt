package by.slowar.currenciesapp.data.currencies.remote

import by.slowar.currenciesapp.data.currencies.remote.models.LatestCurrenciesDto
import retrofit2.http.GET
import retrofit2.http.Query

interface CurrenciesApi {

    @GET("/latest")
    fun getLatestCurrencies(
        @Query("base") baseCurrency: String
    ): LatestCurrenciesDto

    @GET("/latest")
    fun getLatestCurrencies(
        @Query("base") baseCurrency: String,
        @Query("symbols") symbols: String
    ): LatestCurrenciesDto
}