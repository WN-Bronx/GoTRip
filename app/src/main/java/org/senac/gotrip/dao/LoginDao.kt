package org.senac.gotrip.dao

import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Insert
import androidx.room.Query
import org.senac.gotrip.bean.LoginBean

@Dao
interface LoginDao {

    /*
        Busca um usuário por usuário e senha
    */
    @Query("SELECT * FROM loginbean WHERE email = :email AND senha = :senha")
    fun fazerLogin(email: String, senha: String): LoginBean

    @Insert
    fun insertAll(vararg logins: LoginBean)

    @Delete
    fun delete(login: LoginBean)
}