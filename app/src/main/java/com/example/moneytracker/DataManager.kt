package com.example.moneytracker

import androidx.compose.runtime.mutableStateListOf

data class PaymentData(
    val title: String,
    val icon: String,
    val totalPayment: String,
    val date: String,
    val reminder: String
)

object DataManager {
    val paymentDataList = mutableStateListOf<PaymentData>()

    fun addPayment(payment: PaymentData) {
        paymentDataList.add(payment)
    }

    fun removePayment(payment: PaymentData) {
        paymentDataList.remove(payment)
    }

    fun clearAllPayments() {
        paymentDataList.clear()
    }
}