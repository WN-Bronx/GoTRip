package org.senac.gotrip.base

import androidx.room.Database
import androidx.room.RoomDatabase
import org.senac.gotrip.bean.GastoBean
import org.senac.gotrip.bean.LoginBean
import org.senac.gotrip.bean.NviagemBean
import org.senac.gotrip.dao.*

@Database(entities = arrayOf(User::class, LoginBean::class, GastoBean::class, NviagemBean::class), version = 1)
abstract class AppDatabase : RoomDatabase() {
    abstract fun userDao(): UserDao
    abstract fun loginDao(): LoginDao
    abstract fun gastoDao(): GastoDao
    abstract fun nviagemDao(): NviagemDao
}