package com.example.moneytracker

import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.ViewModel

class MainViewModel : ViewModel() {
    var transactions by mutableStateOf<List<Transaction>>(emptyList())
        private set

    fun addTransaction(transaction: Transaction) {
        transactions = transactions + transaction
    }

    fun removeTransaction(transaction: Transaction) {
        transactions = transactions - transaction
    }

    // Fungsi untuk mengelompokkan dan menghitung data saving per kategori
    fun getSavingSummary(): List<SavingSummary> {
        val savings = transactions.filter { it.type == "Saving" }

        return savings.groupBy { it.category }.map { (category, txs) ->
            val total = txs.sumOf { it.amount }
            val goal = txs.lastOrNull { it.goalAmount != null }?.goalAmount ?: 0.0
            val remaining = goal - total

            SavingSummary(
                category = category,
                total = total,
                goal = goal,
                remaining = remaining
            )
        }
    }
}

// Model summary untuk tampilan Saving
data class SavingSummary(
    val category: String,
    val total: Double,
    val goal: Double,
    val remaining: Double
)


