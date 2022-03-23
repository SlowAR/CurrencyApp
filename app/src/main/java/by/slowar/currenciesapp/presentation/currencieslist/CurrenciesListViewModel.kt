package by.slowar.currenciesapp.presentation.currencieslist

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.viewModelScope
import by.slowar.currenciesapp.R
import by.slowar.currenciesapp.data.currencies.local.CurrenciesLocalRepository
import by.slowar.currenciesapp.data.currencies.mappers.toUiState
import by.slowar.currenciesapp.domain.CurrencyItem
import by.slowar.currenciesapp.domain.LoadAndStoreCurrenciesUseCase
import by.slowar.currenciesapp.presentation.currencieslist.favourite.FavouriteCurrencyResult
import by.slowar.currenciesapp.utils.Result
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.async
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

class CurrenciesListViewModel(
    private val loadAndStoreCurrenciesUseCase: LoadAndStoreCurrenciesUseCase,
    private val currenciesLocalRepository: CurrenciesLocalRepository
) : ViewModel() {

    private val _currenciesListResult =
        MutableStateFlow<CurrenciesListResult>(CurrenciesListResult.Idle)
    val currenciesListResult: StateFlow<CurrenciesListResult> = _currenciesListResult

    private val _favouriteCurrencyResult =
        MutableStateFlow<FavouriteCurrencyResult>(FavouriteCurrencyResult.Idle)
    val favouriteCurrencyResult: StateFlow<FavouriteCurrencyResult> = _favouriteCurrencyResult

    fun getLatestCurrencyRates(baseCurrency: String, symbols: List<String> = emptyList()) {
        viewModelScope.launch {
            _currenciesListResult.value = CurrenciesListResult.Loading

            val currenciesRequest = async(Dispatchers.IO) {
                val symbolsString = symbols.joinToString(separator = ",")
                loadAndStoreCurrenciesUseCase.execute(baseCurrency, symbolsString)
            }

            when (val currenciesResponse = currenciesRequest.await()) {
                is Result.Error -> handleCurrenciesResultError(currenciesResponse.error)
                is Result.Success -> handleCurrenciesResultSuccess(currenciesResponse.result)
            }
        }
    }

    private fun handleCurrenciesResultError(error: Throwable) {
        _currenciesListResult.value = CurrenciesListResult.Error(error.localizedMessage)
    }

    private fun handleCurrenciesResultSuccess(result: List<CurrencyItem>) {
        _currenciesListResult.value = CurrenciesListResult.Success(
            result.map { currency ->
                currency.toUiState(::changeFavouriteCurrency)
            }
        )
    }

    private fun changeFavouriteCurrency(symbol: String, isFavourite: Boolean) {
        viewModelScope.launch {
            _favouriteCurrencyResult.value = FavouriteCurrencyResult.Changing

            val changeRequest = async(Dispatchers.IO) {
                currenciesLocalRepository.changeFavouriteCurrency(symbol, isFavourite)
            }

            val changeResponse = changeRequest.await()
            if(changeResponse == null) {
                _favouriteCurrencyResult.value = FavouriteCurrencyResult.Error(R.string.favourite_error)
            } else {
                val uiState = changeResponse.toUiState(::changeFavouriteCurrency)
                _favouriteCurrencyResult.value = FavouriteCurrencyResult.Success(uiState)
            }
        }
    }
}

class CurrenciesListViewModelFactory @Inject constructor(
    private val loadAndStoreCurrenciesUseCase: LoadAndStoreCurrenciesUseCase,
    private val currenciesLocalRepository: CurrenciesLocalRepository
) : ViewModelProvider.Factory {

    override fun <T : ViewModel> create(modelClass: Class<T>): T {
        if (modelClass.isAssignableFrom(CurrenciesListViewModel::class.java)) {
            return CurrenciesListViewModel(
                loadAndStoreCurrenciesUseCase,
                currenciesLocalRepository
            ) as T
        }
        throw IllegalArgumentException("Unknown ViewModel class: $modelClass")
    }
}