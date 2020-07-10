package org.senac.gotrip.dao

import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Insert
import androidx.room.Query
import org.senac.gotrip.bean.GastoBean

@Dao
interface GastoDao {
    @Query("SELECT * FROM gastobean")
    fun findAll(): List<GastoBean>

    @Insert
    fun insertAll(vararg gastos: GastoBean)

    @Delete
    fun delete(gasto: GastoBean)
}