package com.example.moneytracker

import android.app.Application
import android.util.Log
import androidx.compose.runtime.mutableStateListOf
import androidx.compose.runtime.mutableStateMapOf
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.launch
import androidx.compose.ui.graphics.Color

class MainViewModel(application: Application) : AndroidViewModel(application) {
    private val dao: TransactionDao = AppDatabase.getDatabase(application).transactionDao()
    private val paymentDao: PaymentDao = AppDatabase.getDatabase(application).paymentDao()

    val transactions = mutableStateListOf<Transaction>()
    val payments = mutableStateListOf<PaymentEntity>()

    val categoryColors = mutableStateMapOf<String, Color>()

    private val colors = listOf(
        Color(0xFF3A86FF), Color(0xFFF94144), Color(0xFFFFBE0B), Color(0xFF8338EC), Color(0xFFFB5607),
        Color(0xFF06D6A0), Color(0xFF118AB2), Color(0xFFEF476F), Color(0xFFFFD166), Color(0xFF06A77D),
        Color(0xFFFF8C42), Color(0xFF8ECAE6), Color(0xFF219EBC), Color(0xFFFFC8DD), Color(0xFFFFAFCC),
        Color(0xFFCDB4DB), Color(0xFFFFE066), Color(0xFF9BF6FF), Color(0xFFCAFFBF), Color(0xFFFFADAD)
    )

    fun getCategoryColor(category: String): Color {
        return categoryColors.getOrPut(category) {
            colors[categoryColors.size % colors.size]
        }
    }

    init {
        viewModelScope.launch {
            loadTransactions()
            loadPayments()
        }
    }

    suspend fun loadTransactions() {
        transactions.clear()
        val loadedTransactions = dao.getAllTransactions()
        Log.d("MainViewModel", "Loaded transactions: ${loadedTransactions.size}")
        transactions.addAll(loadedTransactions)
    }

    suspend fun loadPayments() {
        payments.clear()
        val loadedPayments = paymentDao.getAllPayments()
        Log.d("MainViewModel", "Loaded payments: ${loadedPayments.size}")
        payments.addAll(loadedPayments)
    }

    fun addTransaction(transaction: Transaction) {
        viewModelScope.launch {
            val validatedTransaction = if (transaction.type == "Saving" && transaction.goalAmount == null) {
                Log.w("MainViewModel", "GoalAmount is null for Saving transaction, setting default to 1.0")
                transaction.copy(goalAmount = 1.0)
            } else {
                transaction
            }
            Log.d("MainViewModel", "Adding transaction: $validatedTransaction")
            dao.insertTransaction(validatedTransaction)
            loadTransactions()
        }
    }

    fun addPayment(payment: PaymentEntity) {
        viewModelScope.launch {
            Log.d("MainViewModel", "Adding payment: $payment")
            paymentDao.insertPayment(payment)
            loadPayments()
        }
    }

    fun deleteTransaction(transaction: Transaction) {
        viewModelScope.launch {
            Log.d("MainViewModel", "Deleting transaction: $transaction")
            dao.deleteTransaction(transaction)
            loadTransactions()
        }
    }

    fun deletePayment(payment: PaymentEntity) {
        viewModelScope.launch {
            Log.d("MainViewModel", "Deleting payment: $payment")
            paymentDao.deletePayment(payment)
            loadPayments()
        }
    }

    fun getSavingSummary(): List<SavingSummary> {
        val savings = transactions.filter { it.type == "Saving" }
        Log.d("MainViewModel", "Savings transactions: ${savings.size}")
        return savings.groupBy { it.category }.map { (category, list) ->
            val total = list.sumOf { it.amount }
            val goal = list.lastOrNull { it.goalAmount != null }?.goalAmount ?: 1.0
            val remaining = (goal - total).coerceAtLeast(0.0)
            Log.d("MainViewModel", "SavingSummary - Category: $category, Total: $total, Goal: $goal, Remaining: $remaining")
            SavingSummary(category, total, goal, remaining)
        }
    }

    fun deleteSavingCategory(category: String) {
        viewModelScope.launch {
            val savingsToDelete = transactions.filter { it.type == "Saving" && it.category == category }
            Log.d("MainViewModel", "Deleting ${savingsToDelete.size} savings for category: $category")
            savingsToDelete.forEach { dao.deleteTransaction(it) }
            loadTransactions()
        }
    }
}

data class SavingSummary(
    val category: String,
    val total: Double,
    val goal: Double,
    val remaining: Double
)