package com.example.moneytracker

import android.app.Application
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateListOf
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.launch

class MainViewModel (application: Application): AndroidViewModel(application) {
    private val dao: TransactionDao = AppDatabase.getDatabase(application).transactionDao()

    val transactions = mutableStateListOf<Transaction>()

    init {
        viewModelScope.launch {
            loadTransactions()
        }
    }

    suspend fun loadTransactions() {
        transactions.clear()
        transactions.addAll(dao.getAllTransactions())
    }

    fun addTransaction(transaction: Transaction) {
        viewModelScope.launch {
            dao.insertTransaction(transaction)
            loadTransactions()
        }
    }

    fun deleteTransaction(transaction: Transaction) {
        viewModelScope.launch {
            dao.deleteTransaction(transaction)
            loadTransactions()  // Refresh data setelah delete
        }
    }

    fun getSavingSummary(): List<SavingSummary> {
        val savings = transactions.filter { it.type == "Saving" }
        return savings.groupBy { it.category }.map { (category, list) ->
            val total = list.sumOf { it.amount }
            val goal = list.firstOrNull { it.goalAmount != null }?.goalAmount ?: 0.0
            val remaining = (goal - total).coerceAtLeast(0.0)
            SavingSummary(category, total, goal, remaining)
        }
    }
}

//class MainViewModel : ViewModel() {
//    var transactions by mutableStateOf<List<Transaction>>(emptyList())
//        private set
//
//    fun addTransaction(transaction: Transaction) {
//        transactions = transactions + transaction
//    }
//
//    fun removeTransaction(transaction: Transaction) {
//        transactions = transactions - transaction
//    }
//
//    // Fungsi untuk mengelompokkan dan menghitung data saving per kategori
//    fun getSavingSummary(): List<SavingSummary> {
//        val savings = transactions.filter { it.type == "Saving" }
//
//        return savings.groupBy { it.category }.map { (category, txs) ->
//            val total = txs.sumOf { it.amount }
//            val goal = txs.lastOrNull { it.goalAmount != null }?.goalAmount ?: 0.0
//            val remaining = goal - total
//
//            SavingSummary(
//                category = category,
//                total = total,
//                goal = goal,
//                remaining = remaining
//            )
//        }
//    }
//}

// Model summary untuk tampilan Saving
data class SavingSummary(
    val category: String,
    val total: Double,
    val goal: Double,
    val remaining: Double
)


