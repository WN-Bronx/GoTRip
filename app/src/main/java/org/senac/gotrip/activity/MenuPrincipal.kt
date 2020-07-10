package org.senac.gotrip.activity

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import kotlinx.android.synthetic.main.menu_principal.*
import org.senac.gotrip.R

class MenuPrincipal : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.menu_principal)
        fun openNextActivityNovoGasto() {
            val intent = Intent(this, NovoGasto::class.java)
            startActivity(intent)
        }

        iViewNovoGasto.setOnClickListener {
            openNextActivityNovoGasto()
        }

        fun openNextActivityNovaViagem() {
            val intent = Intent(this, NovaViagem::class.java)
            startActivity(intent)
        }

        tViewNovaViagem.setOnClickListener {
            openNextActivityNovaViagem()
        }

        fun openNextActivityMinhasviagens() {
            val intent = Intent(this, ListViagem::class.java)
            startActivity(intent)
        }

        iViewMinhasViagens.setOnClickListener {
            openNextActivityMinhasviagens()
        }

    }
}