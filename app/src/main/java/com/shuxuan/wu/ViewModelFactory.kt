package com.shuxuan.wu

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.shuxuan.wu.data.StockRepository

class ViewModelFactory(private val repository: StockRepository) : ViewModelProvider.Factory {
    @Suppress("UNCHECKED_CAST")
    override fun <T: ViewModel> create(modelClass: Class<T>): T {
        if (modelClass.isAssignableFrom(StockViewModel::class.java)) {
            return StockViewModel(repository) as T
        }
        throw IllegalArgumentException("Unknown ViewModel class")
    }
}