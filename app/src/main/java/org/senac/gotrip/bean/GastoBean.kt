package org.senac.gotrip.bean

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity
class GastoBean(var motivo: String,
                var valor: Double,
                var data: String,
                var descricao: String,
                var local: String) {
    @PrimaryKey(autoGenerate = true)
    var id:Int = 0
}