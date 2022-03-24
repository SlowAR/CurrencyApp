package by.slowar.currenciesapp.di.app

import by.slowar.currenciesapp.data.core.CurrenciesConfigData
import by.slowar.currenciesapp.data.core.network.AuthInterceptor
import by.slowar.currenciesapp.data.currencies.remote.CurrenciesApi
import by.slowar.currenciesapp.data.currencies.remote.models.LatestCurrenciesDto
import by.slowar.currenciesapp.di.app.qualifiers.GsonConverter
import by.slowar.currenciesapp.utils.MyCustomDeserializer
import com.google.gson.GsonBuilder
import dagger.Module
import dagger.Provides
import okhttp3.OkHttpClient
import retrofit2.Converter
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

@Module
object RemoteApiModule {

    @Provides
    @AppScope
    fun provideCurrenciesApi(
        configData: CurrenciesConfigData,
        httpClient: OkHttpClient,
        @GsonConverter converterFactory: Converter.Factory
    ): CurrenciesApi {
        return Retrofit.Builder()
            .baseUrl(configData.baseUrl)
            .client(httpClient)
            .addConverterFactory(converterFactory)
            .build()
            .create(CurrenciesApi::class.java)
    }

    @Provides
    @AppScope
    fun provideOkHttpClient(configData: CurrenciesConfigData) =
        OkHttpClient.Builder()
            .addInterceptor(AuthInterceptor(configData.apiKey))
            .build()

    @Provides
    @AppScope
    @GsonConverter
    fun provideGsonConverter(): Converter.Factory = GsonConverterFactory.create(
        GsonBuilder().registerTypeAdapter(LatestCurrenciesDto::class.java, MyCustomDeserializer())
            .create()
    )
}