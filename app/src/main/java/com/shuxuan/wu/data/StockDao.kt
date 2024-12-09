package com.shuxuan.wu.data

import androidx.room.*

@Dao
interface StockDao {
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertStock(stock: StockInfo)

    @Update
    suspend fun updateStock(stock: StockInfo)

    @Delete
    suspend fun deleteStock(stock: StockInfo)

    @Query("SELECT * FROM stock_info")
    suspend fun getAllStocks(): List<StockInfo>

    @Query("SELECT * FROM stock_info WHERE stockSymbol = :symbol LIMIT 1")
    suspend fun getStockBySymbol(symbol: String): StockInfo?
}
