package by.slowar.currenciesapp.data.currencies.remote.models

import com.google.gson.annotations.SerializedName

data class LatestCurrenciesDto(

    @SerializedName("success")
    val success: Boolean,

    @SerializedName("timestamp")
    val timestamp: Int,

    @SerializedName("base")
    val base: String,

    @SerializedName("date")
    val date: String,

    @SerializedName("rates")
    val rates: List<CurrencyRateDto>
)