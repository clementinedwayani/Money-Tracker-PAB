package com.example.moneytracker

import androidx.room.Entity
import androidx.room.PrimaryKey
import java.util.UUID

@Entity(tableName = "payments")
data class PaymentEntity(
    @PrimaryKey
    val id: String = UUID.randomUUID().toString(),
    val icon: String,
    val title: String,
    val reminder: String,
    val totalPayment: String,
    val date: String
)