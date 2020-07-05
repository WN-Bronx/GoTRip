package org.senac.gotrip.dao

import androidx.room.Database
import androidx.room.RoomDatabase
import org.senac.gotrip.bean.LoginBean

@Database(entities = arrayOf(User::class, LoginBean::class), version = 1)
abstract class AppDatabase : RoomDatabase() {
    abstract fun userDao(): UserDao
    abstract fun loginDao(): LoginDao
}