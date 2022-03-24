package by.slowar.currenciesapp.presentation.currencieslist

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.viewModelScope
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

    private val _favouriteCurrenciesResult =
        MutableStateFlow<CurrenciesListResult>(CurrenciesListResult.Idle)
    val favouriteCurrenciesResult: StateFlow<CurrenciesListResult> = _favouriteCurrenciesResult

    private val _favouriteCurrencyChangeResult =
        MutableStateFlow<FavouriteCurrencyResult>(FavouriteCurrencyResult.Idle)
    val favouriteCurrencyChangeResult: StateFlow<FavouriteCurrencyResult> =
        _favouriteCurrencyChangeResult

    var lastBaseCurrency: String = ""
        set(value) {
            getLatestCurrencyRates(field, forceRefresh = field != value)
            field = value
        }

    private fun getLatestCurrencyRates(
        baseCurrency: String,
        symbols: List<String> = emptyList(),
        forceRefresh: Boolean = true
    ) {
        viewModelScope.launch {
            _currenciesListResult.value = CurrenciesListResult.Loading

            val currenciesRequest = async(Dispatchers.IO) {
                val symbolsString = symbols.joinToString(separator = ",")
                loadAndStoreCurrenciesUseCase.execute(baseCurrency, symbolsString, forceRefresh)
            }

            when (val currenciesResponse = currenciesRequest.await()) {
                is Result.Error -> handleCurrenciesResultError(currenciesResponse.error)
                is Result.Success -> handleCurrenciesResultSuccess(currenciesResponse.result)
            }
        }
    }

    fun getFavouriteCurrencies() {
        viewModelScope.launch {
            _currenciesListResult.value = CurrenciesListResult.Loading

            val currenciesRequest = async(Dispatchers.IO) {
                currenciesLocalRepository.getFavouriteCurrencies()
            }

            when (val currenciesResponse = currenciesRequest.await()) {
                is Result.Error -> {
                    val errorText = currenciesResponse.error.localizedMessage
                    _favouriteCurrenciesResult.value = CurrenciesListResult.Error(errorText)
                }
                is Result.Success -> {
                    _favouriteCurrenciesResult.value = CurrenciesListResult.Success(
                        currenciesResponse.result.map { currency ->
                            currency.toUiState(::changeFavouriteCurrency)
                        }
                    )
                }
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
            _favouriteCurrencyChangeResult.value = FavouriteCurrencyResult.Changing

            val changeRequest = async(Dispatchers.IO) {
                currenciesLocalRepository.changeFavouriteCurrency(symbol, isFavourite)
            }

            when (val changeResponse = changeRequest.await()) {
                is Result.Error -> FavouriteCurrencyResult.Error(changeResponse.error.localizedMessage)
                is Result.Success -> {
                    if (changeResponse.result != null) {
                        val uiState = changeResponse.result.toUiState(::changeFavouriteCurrency)
                        _favouriteCurrencyChangeResult.value =
                            FavouriteCurrencyResult.Success(uiState)
                    }
                }
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