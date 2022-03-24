package by.slowar.currenciesapp.presentation.currencieslist.dialog

import android.annotation.SuppressLint
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import by.slowar.currenciesapp.databinding.CurrencyItemSimpleBinding
import by.slowar.currenciesapp.presentation.currencieslist.CurrencyItemUiState

class CurrenciesDialogAdapter(
    private val items: MutableList<CurrencyItemUiState> = mutableListOf(),
    private val onItemClickListener: (String) -> Unit
) : RecyclerView.Adapter<CurrenciesDialogViewHolder>() {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): CurrenciesDialogViewHolder {
        val inflater = LayoutInflater.from(parent.context)
        val binding = CurrencyItemSimpleBinding.inflate(inflater, parent, false)
        return CurrenciesDialogViewHolder(binding)
    }

    override fun onBindViewHolder(holder: CurrenciesDialogViewHolder, position: Int) {
        holder.bind(items[position], onItemClickListener)
    }

    override fun getItemCount() = items.size

    @SuppressLint("NotifyDataSetChanged")
    fun setNewItemsStates(itemsStates: List<CurrencyItemUiState>) {
        items.clear()
        items.addAll(itemsStates)
        notifyDataSetChanged()
    }
}

class CurrenciesDialogViewHolder(private val binding: CurrencyItemSimpleBinding) :
    RecyclerView.ViewHolder(binding.root) {

    fun bind(state: CurrencyItemUiState, onItemClickListener: (String) -> Unit) {
        binding.symbolText.text = state.symbol
        binding.root.setOnClickListener {
            onItemClickListener(state.symbol)
        }
    }
}