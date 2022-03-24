package by.slowar.currenciesapp.data.currencies.local.entities

import androidx.room.Embedded
import androidx.room.Relation

data class CurrencyAndMetadata(

    @Embedded
    val currency: CurrencyEntity,

    @Relation(parentColumn = CurrencyEntity.SYMBOL, entityColumn = CurrencyMetadataEntity.SYMBOL)
    val metadata: CurrencyMetadataEntity?
)