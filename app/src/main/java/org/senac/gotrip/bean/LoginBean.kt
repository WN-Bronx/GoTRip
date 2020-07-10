package org.senac.gotrip.bean

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey

/*
    As classes com a anotação @Entity
*/

@Entity
class LoginBean (
    @ColumnInfo(name = "nome") var nome: String?,
    @ColumnInfo(name = "email") var email: String?,
    @ColumnInfo(name = "senha") var senha: String?){
    /* define a variável id como sendo chave primária e autoincrement */
    @PrimaryKey(autoGenerate = true)
    var id: Int = 0
}