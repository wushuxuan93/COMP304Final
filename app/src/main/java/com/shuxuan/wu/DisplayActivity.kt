package com.shuxuan.wu

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.compose.foundation.layout.*
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import com.shuxuan.wu.MyApplication
import com.shuxuan.wu.data.StockRepository
import kotlinx.coroutines.launch

class DisplayActivity : ComponentActivity() {

    private lateinit var repository: StockRepository
    private var symbol: String? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        val app = application as MyApplication
        repository = app.repository

        symbol = intent.getStringExtra("STOCK_SYMBOL")

        setContent {
            val factory = ViewModelFactory(repository)
            val viewModel: StockViewModel = viewModel(factory = factory)

            symbol?.let { s ->
                DisplayScreen(symbol = s, viewModel = viewModel, onBack = { finish() })
            } ?: run {
                Text("No symbol provided")
            }
        }
    }
}

@Composable
fun DisplayScreen(symbol: String, viewModel: StockViewModel, onBack: () -> Unit) {
    var stockInfo by remember { mutableStateOf<com.shuxuan.wu.data.StockInfo?>(null) }
    val scope = rememberCoroutineScope()

    LaunchedEffect(symbol) {
        val info = viewModel.getStockBySymbol(symbol)
        stockInfo = info
    }

    Column(modifier = Modifier.padding(16.dp)) {
        Text("Stock Information", style = MaterialTheme.typography.titleLarge)
        Spacer(modifier = Modifier.height(8.dp))
        if (stockInfo != null) {
            Text("Symbol: ${stockInfo!!.stockSymbol}")
            Text("Company: ${stockInfo!!.companyName}")
            Text("Quote: ${stockInfo!!.stockQuote}")
        } else {
            Text("Loading...")
        }
        Spacer(modifier = Modifier.height(16.dp))
        Button(onClick = onBack) {
            Text("Back")
        }
    }
}
