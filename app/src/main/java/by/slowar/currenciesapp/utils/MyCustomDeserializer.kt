package by.slowar.currenciesapp.utils

import by.slowar.currenciesapp.data.currencies.remote.models.CurrencyRateDto
import by.slowar.currenciesapp.data.currencies.remote.models.LatestCurrenciesDto
import com.google.gson.JsonDeserializationContext
import com.google.gson.JsonDeserializer
import com.google.gson.JsonElement
import java.lang.reflect.Type

class MyCustomDeserializer : JsonDeserializer<LatestCurrenciesDto> {

    override fun deserialize(json: JsonElement?, typeOfT: Type?, context: JsonDeserializationContext?): LatestCurrenciesDto {
        val obj = json!!.asJsonObject

        val success = obj.get("success").asBoolean
        val timestamp = obj.get("timestamp").asInt
        val base = obj.get("base").asString
        val date = obj.get("date").asString

        val rates = obj.get("rates").asJsonObject
        val list = mutableListOf<CurrencyRateDto>()
        val iterator = rates.entrySet().iterator()
        for (mutableEntry in iterator) {
            list.add(CurrencyRateDto(mutableEntry.key, mutableEntry.value.asDouble))
        }
        return LatestCurrenciesDto(success, timestamp, base, date, list)
    }
}