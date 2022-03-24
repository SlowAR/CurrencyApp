package by.slowar.currenciesapp.presentation.currencieslist

import android.annotation.SuppressLint
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import by.slowar.currenciesapp.databinding.CurrencyItemBinding

class CurrenciesListAdapter(private var itemsStates: MutableList<CurrencyItemUiState> = mutableListOf()) :
    RecyclerView.Adapter<CurrencyViewHolder>() {

    companion object {
        const val FAVOURITE_CURRENCY_PAYLOAD = "FavouriteCurrencyPayload"
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): CurrencyViewHolder {
        val inflater = LayoutInflater.from(parent.context)
        val binding = CurrencyItemBinding.inflate(inflater, parent, false)
        return CurrencyViewHolder(binding)
    }

    override fun onBindViewHolder(holder: CurrencyViewHolder, position: Int) {
        holder.bind(itemsStates[position])
    }

    override fun onBindViewHolder(
        holder: CurrencyViewHolder,
        position: Int,
        payloads: MutableList<Any>
    ) {
        if (payloads.isEmpty()) {
            super.onBindViewHolder(holder, position, payloads)
        } else {
            for (payload in payloads) {
                if (payload == FAVOURITE_CURRENCY_PAYLOAD) {
                    holder.bindFavourite(itemsStates[position])
                }
            }
        }
    }

    @SuppressLint("NotifyDataSetChanged")
    fun setNewItemsStates(itemsStates: List<CurrencyItemUiState>) {
        this.itemsStates.clear()
        this.itemsStates.addAll(itemsStates)
        notifyDataSetChanged()
    }

    fun changeItemState(newItemUiState: CurrencyItemUiState, removeFavourite: Boolean = false) {
        val index = itemsStates.indexOfFirst { it.symbol == newItemUiState.symbol }
        if (index == -1) return

        if (removeFavourite && !newItemUiState.isFavourite) {
            itemsStates.removeAt(index)
            notifyItemRemoved(index)
            return
        }

        itemsStates[index] = newItemUiState
        notifyItemChanged(index, FAVOURITE_CURRENCY_PAYLOAD)
    }

    override fun getItemCount() = itemsStates.size
}