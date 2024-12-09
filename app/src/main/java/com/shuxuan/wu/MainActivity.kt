package com.shuxuan.wu

import android.content.Intent
import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.input.TextFieldValue
import androidx.compose.ui.unit.dp
import com.shuxuan.wu.MyApplication
import com.shuxuan.wu.data.StockRepository
import com.shuxuan.wu.data.StockInfo1

class ShuxuanActivity : ComponentActivity() {

    private lateinit var repository: StockRepository

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        val app = application as MyApplication
        repository = app.repository

        setContent {
            val factory = ViewModelFactory(repository)
            val viewModel: StockViewModel = viewModel(factory = factory)

            LaunchedEffect(Unit) {
                viewModel.loadAllStocks()
            }

            MainScreen(
                stocks = viewModel.stocks.collectAsState().value,
                onInsertStock = { symbol, company, quote ->
                    viewModel.insertStock(symbol, company, quote)
                },
                onItemClick = { symbol ->
                    val intent = Intent(this@ShuxuanActivity, DisplayActivity::class.java)
                    intent.putExtra("STOCK_SYMBOL", symbol)
                    startActivity(intent)
                }
            )
        }
    }
}

@Composable
fun MainScreen(
    stocks: List<com.shuxuan.wu.data.StockInfo>,
    onInsertStock: (String, String, Double) -> Unit,
    onItemClick: (String) -> Unit
) {
    var symbol by remember { mutableStateOf(TextFieldValue("")) }
    var company by remember { mutableStateOf(TextFieldValue("")) }
    var quote by remember { mutableStateOf(TextFieldValue("")) }

    Column(modifier = Modifier.padding(16.dp)) {
        Text("Insert Stock Info", style = MaterialTheme.typography.titleLarge)
        OutlinedTextField(
            value = symbol,
            onValueChange = { symbol = it },
            label = { Text("Stock Symbol") },
            modifier = Modifier.fillMaxWidth()
        )
        OutlinedTextField(
            value = company,
            onValueChange = { company = it },
            label = { Text("Company Name") },
            modifier = Modifier.fillMaxWidth()
        )
        OutlinedTextField(
            value = quote,
            onValueChange = { quote = it },
            label = { Text("Stock Quote") },
            modifier = Modifier.fillMaxWidth()
        )
        Spacer(modifier = Modifier.height(8.dp))
        Button(onClick = {
            val q = quote.text.toDoubleOrNull() ?: 0.0
            if (symbol.text.isNotEmpty() && company.text.isNotEmpty()) {
                onInsertStock(symbol.text, company.text, q)
            }
        }) {
            Text("Insert")
        }

        Spacer(modifier = Modifier.height(16.dp))
        Text("Available Stocks:", style = MaterialTheme.typography.titleMedium)

        LazyColumn {
            items(stocks) { stock ->
                Row(
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(vertical = 8.dp)
                ) {
                    Text(stock.stockSymbol, modifier = Modifier.weight(1f))
                    Button(onClick = { onItemClick(stock.stockSymbol) }) {
                        Text("View")
                    }
                }
            }
        }
    }
}
