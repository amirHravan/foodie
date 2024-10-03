package com.ravan.foodie.reserveinfo.domain.model

import androidx.room.Entity
import androidx.room.PrimaryKey
import com.ravan.foodie.reserveinfo.db.ForgetCodeDatabase

@Entity(tableName = ForgetCodeDatabase.Companion.DATABASE_TABLE_NAME)
data class ForgetCode(
    @PrimaryKey val reserveId: Int,
    val code: String,
)
