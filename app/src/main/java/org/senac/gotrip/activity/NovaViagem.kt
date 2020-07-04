package org.senac.gotrip.activity

import android.app.DatePickerDialog
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.DatePicker
import kotlinx.android.synthetic.main.nova_viagem.*
import org.senac.gotrip.R
import java.util.*

class NovaViagem : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.nova_viagem)
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
    }
}