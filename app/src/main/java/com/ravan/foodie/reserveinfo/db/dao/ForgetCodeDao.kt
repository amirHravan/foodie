package com.ravan.foodie.reserveinfo.db.dao

import androidx.room.Dao
import androidx.room.Query
import androidx.room.Upsert
import com.ravan.foodie.reserveinfo.domain.model.ForgetCode

@Dao
interface ForgetCodeDao {

    @Upsert
    fun upsertForgetCode(forgetCode: ForgetCode)

//    @Query("SELECT * FROM forget_code_table WHERE reserveId = :reserveId")
//    fun getForgetCodeByReserveId(reserveId: Int): ForgetCode?

//    @Delete
//    fun deleteForgetCodeByReserveId(reserveId: Int)

    @Query("DELETE FROM forget_code_table")
    fun deleteAllForgetCodes()

//    fun getForgetCodeCount(): Int

    @Query("SELECT * FROM forget_code_table")
    fun getAllForgetCodes(): List<ForgetCode>
}