package com.shuxuan.wu

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.shuxuan.wu.data.StockInfo
import com.shuxuan.wu.data.StockRepository
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.launch
import kotlinx.coroutines.flow.asStateFlow


class StockViewModel(private val repository: StockRepository) : ViewModel() {

    private val _stocks = MutableStateFlow<List<StockInfo>>(emptyList())
    val stocks = _stocks.asStateFlow()

    fun loadAllStocks() {
        viewModelScope.launch {
            _stocks.value = repository.getAllStocks()
        }
    }

    fun insertStock(stockSymbol: String, companyName: String, stockQuote: Double) {
        viewModelScope.launch {
            val stock = StockInfo(stockSymbol, companyName, stockQuote)
            repository.insertStock(stock)
            loadAllStocks()
        }
    }

    suspend fun getStockBySymbol(symbol: String): StockInfo? {
        return repository.getStockBySymbol(symbol)
    }
}
