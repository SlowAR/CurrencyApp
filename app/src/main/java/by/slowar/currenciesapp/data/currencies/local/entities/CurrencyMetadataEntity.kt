package by.slowar.currenciesapp.data.currencies.local.entities

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.ForeignKey
import androidx.room.ForeignKey.CASCADE
import androidx.room.PrimaryKey

@Entity(
    tableName = CurrencyMetadataEntity.TABLE_NAME,
    foreignKeys = [
        ForeignKey(
            entity = CurrencyEntity::class,
            parentColumns = [CurrencyEntity.SYMBOL],
            childColumns = [CurrencyMetadataEntity.SYMBOL]
        )
    ]
)
data class CurrencyMetadataEntity(

    @PrimaryKey
    @ColumnInfo(name = SYMBOL)
    val symbol: String,

    @ColumnInfo(name = IS_FAVOURITE)
    val isFavourite: Boolean = false
) {
    companion object {
        const val TABLE_NAME = "currency_metadata"
        const val SYMBOL = "symbol"
        const val IS_FAVOURITE = "is_favourite"
    }
}