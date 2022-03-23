package by.slowar.currenciesapp.di.app

import by.slowar.currenciesapp.data.core.CurrenciesConfigData
import by.slowar.currenciesapp.data.core.network.AuthInterceptor
import by.slowar.currenciesapp.data.currencies.remote.CurrenciesApi
import dagger.Module
import dagger.Provides
import okhttp3.OkHttpClient
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

@Module
object RemoteApiModule {

    @Provides
    @AppScope
    fun provideCurrenciesApi(
        configData: CurrenciesConfigData,
        httpClient: OkHttpClient
    ): CurrenciesApi {
        return Retrofit.Builder()
            .baseUrl(configData.baseUrl)
            .client(httpClient)
            .addConverterFactory(GsonConverterFactory.create())
            .build()
            .create(CurrenciesApi::class.java)
    }

    @Provides
    @AppScope
    fun provideOkHttpClient(configData: CurrenciesConfigData) =
        OkHttpClient.Builder()
            .addInterceptor(AuthInterceptor(configData.apiKey))
            .build()
}