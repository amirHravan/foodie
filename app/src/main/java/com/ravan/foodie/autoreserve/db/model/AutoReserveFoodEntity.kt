package com.ravan.foodie.autoreserve.db.model

import androidx.room.Entity
import androidx.room.Index
import androidx.room.PrimaryKey
import com.ravan.foodie.autoreserve.domain.model.AutoReserveFoodPriority

@Entity(tableName = "food_priority", indices = [Index("accountId")])
data class AutoReserveFoodEntity(
    @PrimaryKey(autoGenerate = true) val id: Int = 0,
    /** Owning account's username, or [TEMPLATE_ACCOUNT_ID] for the bundled defaults. */
    val accountId: String,
    val name: String,
    var priority: Int
)

/** Rows with this account id hold the default food list new accounts start from. */
const val TEMPLATE_ACCOUNT_ID = ""

fun AutoReserveFoodEntity.toAutoReserveFoodPriority() = AutoReserveFoodPriority(
    id = id,
    name = name,
    priority = priority
)
