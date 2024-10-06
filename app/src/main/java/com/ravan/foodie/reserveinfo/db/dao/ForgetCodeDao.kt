package com.ravan.foodie.reserveinfo.db.dao

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import com.ravan.foodie.reserveinfo.db.model.ForgetCodeEntity
import com.ravan.foodie.reserveinfo.domain.model.ForgetCode

@Dao
interface ForgetCodeDao {

    @Query("DELETE FROM forget_code_table WHERE timestamp < :twoWeeksAgo")
    suspend fun deleteOldEntries(twoWeeksAgo: Long)

    @Query("DELETE FROM forget_code_table WHERE isValid = 0")
    suspend fun deleteNonValidEntries()

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertForgetCode(reservation: ForgetCodeEntity)

    @Query("SELECT * FROM forget_code_table WHERE reserveId = :reserveId LIMIT 1")
    suspend fun getForgetCodeByReserveId(reserveId: Int): ForgetCode?

    @Query("DELETE FROM forget_code_table")
    suspend fun deleteAllForgetCodes()

    @Query("SELECT * FROM forget_code_table")
    suspend fun getAllForgetCodes(): List<ForgetCodeEntity>
}