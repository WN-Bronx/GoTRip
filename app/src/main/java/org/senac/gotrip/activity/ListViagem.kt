package org.senac.gotrip.activity

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Toast
import androidx.appcompat.app.AlertDialog
import androidx.room.Room
import org.senac.gotrip.R
import org.senac.gotrip.base.AppDatabase
import org.senac.gotrip.bean.GastoBean
import org.senac.gotrip.bean.NviagemBean
import org.senac.gotrip.dao.GastoDao

class ListViagem : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.list_viagem)

        val db = Room.databaseBuilder(this, AppDatabase::class.java, "gotripdb")
            .allowMainThreadQueries() //Permite travar a aplicação principal
            .build()
        //Objeto DAO
        val daoViagens = db?.nviagemDao()
        val daoGastos: GastoDao = db?.gastoDao()

        var lstResultados: String = ""

        lstResultados += "\n--------------------- Viagens ----------------------\n"
        daoViagens?.findAll()!!.let {
            val viagens: List<NviagemBean> = it
            viagens.forEach{
                lstResultados += "\n"+it.destino
                lstResultados += "\n       De "+it.datain+" até "+it.dataout
                lstResultados += "\n       Orçamento: "+it.orcamento
            }
        }
        lstResultados += "\n\n---------------------- Gastos -----------------------\n"
        daoGastos?.findAll()!!.let {
            val gastos: List<GastoBean> = it
            gastos.forEach{
                lstResultados += "\nData "+it.data
                lstResultados += "\n            ["+it.motivo + "]"
                lstResultados += "\n     "+it.descricao + " - R$"+ it.valor
            }
        }

        this.exibirAlerta("Resultados", lstResultados)
    }

    private fun exibirAlerta(titulo: String, msg: String) {
        AlertDialog.Builder(this@ListViagem)
            .setTitle(titulo)
            .setMessage(msg)
            .setPositiveButton("OK", {dialog, i -> super.onBackPressed() })
            .show()
    }
}