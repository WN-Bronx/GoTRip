package org.senac.gotrip.activity

import android.app.DatePickerDialog
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Button
import android.widget.DatePicker
import android.widget.EditText
import android.widget.Toast
import androidx.appcompat.app.AlertDialog
import androidx.room.Room
import kotlinx.android.synthetic.main.nova_viagem.*
import org.senac.gotrip.R
import org.senac.gotrip.base.AppDatabase
import org.senac.gotrip.bean.NviagemBean
import java.lang.Exception
import java.util.*

class NovaViagem : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.nova_viagem)

        //Bt
        val btSalvarViagem: Button = findViewById(R.id.bttSalvarViagem)
        /*
        val campoQtdPessoas: EditText = findViewById(R.id.eTextQtd)
        campoQtdPessoas.text = ""
         */
        val db = Room.databaseBuilder(this, AppDatabase::class.java, "gotripdb")
            .allowMainThreadQueries() //Permite travar a aplicação principal
            .build()
        //Objeto DAO
        val dao = db?.nviagemDao()

        //Calendario
        val c = Calendar.getInstance()
        val year = c.get(Calendar.YEAR)
        val month = c.get(Calendar.MONTH)
        val day = c.get(Calendar.DAY_OF_MONTH)

        bttDateIn.setOnClickListener{
            val dpd = DatePickerDialog(this, DatePickerDialog.OnDateSetListener{ view: DatePicker, mYear: Int, mMonth: Int, mDay: Int ->
                tViewDateIn.setText(""+ mDay +"/"+ mMonth +"/"+ mYear)
            }, year, month, day)
            dpd.show()
        }
        bttDateOut.setOnClickListener{
            val dpd = DatePickerDialog(this, DatePickerDialog.OnDateSetListener{view: DatePicker, mYear: Int, mMonth: Int, mDay: Int ->
                tViewDateOut.setText(""+ mDay +"/"+ mMonth +"/"+ mYear)
            }, year, month, day)
            dpd.show()
        }

        btSalvarViagem.setOnClickListener {
            var orcamento: Double = 0.0
            var qtdpessoa: Int = 0
            var destino: String = eTextDestino.text.trim().toString()
            var lazer: String = rbLazer.isChecked.toString()
            var negocio: String = rbNegocios.isChecked.toString()
            var datain: String = tViewDateIn.text.trim().toString()
            var dataout: String = tViewDateOut.text.trim().toString()

            try {
                orcamento = (eTextOrcamento.text.trim().toString().toDouble())
                qtdpessoa = (eTextQtd.text.trim().toString().toInt())
            } catch (e: Exception) {
            }

            var validarCampos: String = this.validarCampos(destino, lazer, negocio, orcamento, qtdpessoa, datain, dataout)
            if(validarCampos.equals("")) {
                var novaViagem: NviagemBean = NviagemBean(destino, lazer, negocio, orcamento, qtdpessoa, datain, dataout)
                dao.insertAll(novaViagem)
                Toast.makeText(
                    this@NovaViagem,
                    "Sua viagem para o " + destino + " salvo com sucesso!",
                    Toast.LENGTH_LONG
                ).show()
            } else {
                this.exibirAlerta("Nova Viagem", validarCampos)
            }
        }
    }


    private fun exibirAlerta(titulo: String, msg: String) {
        AlertDialog.Builder(this@NovaViagem)
            .setTitle(titulo)
            .setMessage(msg)
            .setPositiveButton("OK", { dialog, i -> dialog.dismiss()})
            .show()
    }

    //Valida se os campos foram preenchido e retorna a informação do que ainda falta ser preenchido
    private fun validarCampos(destino:String, lazer:String, negocio:String, orcamento: Double, qtdpessoa:Int, datain:String, dataout:String): String {
        //var dataDataIn:Date = SimpleDateFormat("dd/mm/yyyy").parse(datain)
        //var dataDataOut:Date = SimpleDateFormat("dd/mm/yyyy").parse(dataout)

        //Toast.makeText(this@NovaViagem, "dataDataIn: "+dataDataIn, Toast.LENGTH_LONG).show()
        Toast.makeText(this@NovaViagem, "datassss: "+this.validarDatas(datain, dataout), Toast.LENGTH_LONG).show()


        var result: String = ""

        if (destino.equals(""))
            result += "-> Favor informar o destino\n"

        if (lazer.equals(""))
            result += "-> Favor informar o lazer\n"

        if (negocio.equals(""))
            result += "-> Favor informar o negocio\n"

        if (orcamento == 0.0)
            result += "-> Favor informar um orçamento\n"

        if (qtdpessoa.equals(""))
            result += "-> Favor informar o quantidade de pessoas\n"

        if (qtdpessoa == 0)
            result += "-> A quantidade de pessoas tem que ser maior que 0\n"

        if (datain.equals(""))
            result += "-> Favor informar o data partida\n"
        //else if (dataDataOut > dataDataIn)
        // result += " -> Data de partida não pdoe ser menor que data de saida"

        if (dataout.equals(""))
            result += "-> Favor informar o data chegada"

        if (datain != "" && dataout != "")
            result += this.validarDatas(datain, dataout)

        return result
    }

    private fun montarData(data: String): String{
        var dia: String = data.substring(0, (data.indexOf("/", 0)))
        var mes: String = data.substring((data.indexOf("/", 0)+1),
                                         (data.indexOf("/", (data.indexOf("/", 0)+1))))
        var ano: String = data.substring((data.lastIndexOf("/")+1), data.length)

        if (dia.length == 1)
            dia ="0"+dia
        if (mes.length == 1)
            mes ="0"+mes
        Toast.makeText(this@NovaViagem, "data formatada\n: "+(dia+"/"+mes+"/"+ano), Toast.LENGTH_LONG).show()
        return dia+"/"+mes+"/"+ano
    }

    fun validarDatas(datain_: String, dataout_: String): String{
        var result:String = ""

        var datain: String = this.montarData(datain_)
        var dataout: String  = this.montarData(dataout_)

        if(datain.substring(6, 10).toInt() > dataout.substring(6, 10).toInt()) {
            result = "\nData de partida não pdoe ser menor que data de saida"
        }else if(datain.substring(6, 10).toInt() == dataout.substring(6, 10).toInt()){
            if(datain.substring(3, 5).toInt() > dataout.substring(3, 5).toInt()){
                result = "\nData de partida não pdoe ser menor que data de saida"
            } else if(datain.substring(3, 5).toInt() == dataout.substring(3, 5).toInt()){
                if(datain.substring(0, 2).toInt() > dataout.substring(0, 2).toInt()){
                    result = "\nData de partida não pdoe ser menor que data de saida"
                }
            }
        }
        return  result
    }
}