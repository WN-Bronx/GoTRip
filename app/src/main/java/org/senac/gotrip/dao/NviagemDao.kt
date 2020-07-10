package org.senac.gotrip.dao

import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Insert
import androidx.room.Query
import org.senac.gotrip.bean.NviagemBean

@Dao
interface NviagemDao {
    @Query("SELECT * FROM nviagembean")
    fun findAll(): List<NviagemBean>

    @Insert
    fun insertAll(vararg nviagems: NviagemBean)

    @Delete
    fun delete(gasto: NviagemBean)
}