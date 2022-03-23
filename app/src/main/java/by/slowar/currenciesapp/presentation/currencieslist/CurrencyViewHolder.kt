package by.slowar.currenciesapp.presentation.currencieslist

import androidx.recyclerview.widget.RecyclerView
import by.slowar.currenciesapp.databinding.CurrencyItemBinding

class CurrencyViewHolder(
    private val binding: CurrencyItemBinding,
) : RecyclerView.ViewHolder(binding.root) {

    fun bind(currencyState: CurrencyItemUiState) {
        binding.symbolText.text = currencyState.symbol
        binding.rateText.text = currencyState.rate

        bindFavourite(currencyState)
        binding.favouriteButton.setOnClickListener {
            currencyState.onFavouriteClick(currencyState.symbol, !currencyState.isFavourite)
        }
    }

    fun bindFavourite(currencyState: CurrencyItemUiState) {
        val favouriteIcon = if (currencyState.isFavourite) android.R.drawable.star_big_on
        else android.R.drawable.star_big_off
        binding.favouriteButton.setImageResource(favouriteIcon)
    }
}