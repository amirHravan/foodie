package com.ravan.foodie.reserveinfo.db.model

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey
import com.ravan.foodie.reserveinfo.db.ForgetCodeDatabase
import com.ravan.foodie.reserveinfo.domain.model.ForgetCode

@Entity(tableName = ForgetCodeDatabase.DATABASE_TABLE_NAME)
data class ForgetCodeEntity(
    @PrimaryKey val reserveId: Int,
    val code: String,
    val timestamp: Long = System.currentTimeMillis(),
    val isValid: Boolean = true,
    /** Username of the account the reservation belongs to. */
    @ColumnInfo(defaultValue = "") val accountId: String = "",
)

fun ForgetCodeEntity.toForgetCode() = ForgetCode(
    reserveId = reserveId,
    code = code,
    isValid = isValid,
)
