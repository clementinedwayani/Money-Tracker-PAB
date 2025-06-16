package com.example.moneytracker

import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Insert
import androidx.room.Query

@Dao
interface PaymentDao {
    @Query("SELECT * FROM payments")
    suspend fun getAllPayments(): List<PaymentEntity>

    @Insert
    suspend fun insertPayment(payment: PaymentEntity)

    @Delete
    suspend fun deletePayment(payment: PaymentEntity)
}