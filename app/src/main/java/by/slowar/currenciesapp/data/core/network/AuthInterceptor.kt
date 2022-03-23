package by.slowar.currenciesapp.data.core.network

import okhttp3.Interceptor
import okhttp3.Response

class AuthInterceptor(private val apiKey: String) : Interceptor {

    override fun intercept(chain: Interceptor.Chain): Response {
        val request = chain.request()

        val url = request.url.newBuilder()
            .addQueryParameter("access_key", apiKey)
            .build()

        val resultRequest = request.newBuilder().url(url).build()

        return chain.proceed(resultRequest)
    }
}