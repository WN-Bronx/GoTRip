package org.senac.gotrip.bean

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity
class NviagemBean(var destino: String,
                  var lazer: String,
                  var negocio: String,
                  var orcamento: Double,
                  var qtdpessoa: Int,
                  var datain: String,
                  var dataout: String) {
    @PrimaryKey(autoGenerate = true)
    var id:Int = 0
}
