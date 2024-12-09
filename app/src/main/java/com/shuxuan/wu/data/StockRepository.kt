package com.shuxuan.wu.data


class StockRepository(private val dao: StockDao) {
    suspend fun insertStock(stock: StockInfo) = dao.insertStock(stock)
    suspend fun updateStock(stock: StockInfo) = dao.updateStock(stock)
    suspend fun deleteStock(stock: StockInfo) = dao.deleteStock(stock)
    suspend fun getAllStocks(): List<StockInfo> = dao.getAllStocks()
    suspend fun getStockBySymbol(symbol: String): StockInfo? = dao.getStockBySymbol(symbol)
}