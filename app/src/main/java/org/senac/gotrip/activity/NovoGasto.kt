package org.senac.gotrip.activity

import android.app.DatePickerDialog
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Button
import android.widget.DatePicker
import android.widget.Toast
import androidx.appcompat.app.AlertDialog
import androidx.room.Room
import kotlinx.android.synthetic.main.novo_gasto.*
import org.senac.gotrip.R
import org.senac.gotrip.bean.GastoBean
import org.senac.gotrip.base.AppDatabase
import java.util.*

class NovoGasto : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.novo_gasto)

        //Bt
        val btGastei: Button = findViewById(R.id.bttGastei)

        val db = Room.databaseBuilder(this, AppDatabase::class.java, "gotripdb")
            .allowMainThreadQueries() //Permite travar a aplicação principal
            .build()
        //Objeto DAO
        val dao = db?.gastoDao()

        //Calendario
        val c = Calendar.getInstance()
        val year = c.get(Calendar.YEAR)
        val month = c.get(Calendar.MONTH)
        val day = c.get(Calendar.DAY_OF_MONTH)

        bttDataGasto.setOnClickListener{
            val dpd = DatePickerDialog(this, DatePickerDialog.OnDateSetListener{ view: DatePicker, mYear: Int, mMonth: Int, mDay: Int ->
                tViewData.setText(""+ mDay +"/"+ mMonth +"/"+ mYear)
            }, year, month, day)
            dpd.show()
        }

        btGastei.setOnClickListener {
            var valor: Double = 0.0

            try {
                valor = (tViewValor.text.trim().toString()).toDouble()
            }catch (e: Exception){}
            var motivo: String = spTipoGasto.selectedItem.toString()
            var dataa: String = tViewData.text.trim().toString()
            var descricao: String = edTextDescricao.text.trim().toString()
            var local: String = edTextDadoLocal.text.trim().toString()

            var validarCampos: String = this.validarCampos(motivo, valor, dataa, descricao, local)
            if(validarCampos == "") {
                var gasto: GastoBean = GastoBean(motivo, valor, dataa, descricao, local)
                dao.insertAll(gasto)
                Toast.makeText(this@NovoGasto, " Gasto com "+motivo+" salvo com sucesso!", Toast.LENGTH_LONG).show()
            }else{
                this.exibirAlerta("Novo Gasto", validarCampos)
            }
        }
    }

    private fun validarCampos(motivo: String, valor: Double, data: String, descricao: String, local:String): String{
        var result: String = ""

        if(motivo.equals(""))
            result = "-> Favor informar um motivo\n"

        if(valor == 0.0)
            result += "-> Favor informar um valor\n"

        if(data.equals(""))
            result += "-> Favor informar uma data\n"

        if(descricao.equals(""))
            result += "-> Favor informar uma descricao\n"

        if(local.equals(""))
            result += "-> Favor informar um local\n"

        return result
    }

    private fun exibirAlerta(titulo: String, msg: String) {
        AlertDialog.Builder(this@NovoGasto)
            .setTitle(titulo)
            .setMessage(msg)
            .setPositiveButton("OK", {dialog, i -> dialog.dismiss() })
            .show()
    }
}