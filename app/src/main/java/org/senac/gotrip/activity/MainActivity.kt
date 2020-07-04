package org.senac.gotrip.activity

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import org.senac.gotrip.R

class MainActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.login_principal)
    }
}