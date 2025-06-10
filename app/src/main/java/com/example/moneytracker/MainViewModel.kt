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
}