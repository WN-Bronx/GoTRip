package org.senac.gotrip.activity

import android.os.Bundle
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.room.Room
import org.senac.gotrip.R
import org.senac.gotrip.dao.AppDatabase

class Login : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.login_principal)

        val db = Room.databaseBuilder(
            applicationContext,
            AppDatabase::class.java, "gotripdb"
        ).build()
        Toast.makeText(this,"wn", Toast.LENGTH_LONG).show();
    }

    override fun onStart(){
        super.onStart()
        Toast.makeText(this,"wns", Toast.LENGTH_LONG).show();
    }
}