package com.shuxuan.wu

import android.app.Application
import com.shuxuan.wu.data.AppDatabase
import com.shuxuan.wu.data.StockRepository

class MyApplication : Application() {
    val database by lazy { AppDatabase.getDatabase(this) }
    val repository by lazy { StockRepository(database.stockDao()) }
}