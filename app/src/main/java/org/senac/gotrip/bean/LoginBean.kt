package org.senac.gotrip.bean

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey

/*
    As classes com a anotação @Entity
*/

@Entity
class LoginBean (
    @PrimaryKey /* define a variável id como sendo chave prim[primária */
    val id: Int,
    @ColumnInfo(name = "email") val email: String?,
    @ColumnInfo(name = "senha") val senha: String?
)