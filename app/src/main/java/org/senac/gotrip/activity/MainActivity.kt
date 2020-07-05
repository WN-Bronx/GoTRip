package org.senac.gotrip.activity

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Button
import android.widget.EditText
import androidx.appcompat.app.AlertDialog
import androidx.room.Room
import kotlinx.android.synthetic.main.login_principal.*
import org.senac.gotrip.R
import org.senac.gotrip.bean.LoginBean
import org.senac.gotrip.dao.AppDatabase

class MainActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.login_principal)

//Faz  a conexão com o banco de dados
        val db = Room.databaseBuilder(this, AppDatabase::class.java, "gotripdb")
            .allowMainThreadQueries() //Permite travar a aplicação principal
            .build()

        //Objeto DAO
        val dao = db?.loginDao()
        /*
        //Salva um usuário no banco de dados

        //Insere um registor no banco de dados
        var l = LoginBean(id = 1, email = "gotrip@gotrip.com.br", senha = "java")
        dao?.insertAll(l)
        */

        val edtEmail = findViewById<EditText>(R.id.edTextEmail)
        val edtSenha = findViewById<EditText>(R.id.edTextSenha)
        val btLogin = findViewById<Button>(R.id.bttLogin)
        btLogin.setOnClickListener {
            //Busca o login do banco de dados
            var l: LoginBean = dao.fazerLogin(edtEmail.text.trim().toString(), edtSenha.text.trim().toString())
            //var l: LoginBean = dao.fazerLogin("gotrip@gotrip.com.br", "java")

            try {
                if (l != null) {
                    AlertDialog.Builder(this@MainActivity)
                        .setTitle("DB")
                        .setMessage(" Sucesso :D ")
                        .setPositiveButton("OK", { dialog, i -> dialog.dismiss() })
                        .show()
                } else {
                    AlertDialog.Builder(this@MainActivity)
                        .setTitle("DB")
                        .setMessage(" [ Falha no cadastro ] " + l.email + "   -   " + l.senha)
                        .setPositiveButton("OK", { dialog, i -> dialog.dismiss() })
                        .show()
                }
            } catch (e: Exception) {
                AlertDialog.Builder(this@MainActivity)
                    .setTitle("DB")
                    .setMessage(" X- Autenticação não autorizada -X ")
                    .setPositiveButton("OK", { dialog, i -> dialog.dismiss() })
                    .show()
            }
        }

        fun openNextActivity() {
            val intent = Intent(this, NewLogin::class.java)
            startActivity(intent)
        }

        bttConta.setOnClickListener {
            openNextActivity()
        }
    }
}