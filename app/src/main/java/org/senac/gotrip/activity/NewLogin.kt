package org.senac.gotrip.activity

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Button
import android.widget.EditText
import androidx.appcompat.app.AlertDialog
import androidx.room.Room
import kotlinx.android.synthetic.main.new_login.*
import org.senac.gotrip.R
import org.senac.gotrip.bean.LoginBean
import org.senac.gotrip.dao.AppDatabase


class NewLogin : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.new_login)
        //Faz  a conexão com o banco de dados
        val db = Room.databaseBuilder(this, AppDatabase::class.java, "gotripdb")
            .allowMainThreadQueries() //Permite travar a aplicação principal
            .build()

        //Objeto DAO
        val dao = db?.loginDao()

        //Insere um registor no banco de dados
        val edNemail = findViewById<EditText>(R.id.edTextNewEmail)
        val edNsenha = findViewById<EditText>(R.id.edTextNewSenha)
        val btScriar = findViewById<Button>(R.id.bttNewUser)
        val btDeluser = findViewById<Button>(R.id.bttDelete)

        btScriar.setOnClickListener {
            //Busca o login do banco de dados
            var l: LoginBean = dao.fazerLogin(edNemail.text.trim().toString(), edNsenha.text.trim().toString())

            try {
                if(l != null){
                    AlertDialog.Builder(this@NewLogin)
                        .setTitle("DB")
                        .setMessage(" Login cadastrado com sucesso :D ")
                        .setPositiveButton("OK", {dialog, i -> dialog.dismiss() })
                        .show()
                } else{
                    AlertDialog.Builder(this@NewLogin)
                        .setTitle("DB")
                        .setMessage(" [ Falha no cadastro ] "+l.email+ "   -   "+l.senha)
                        .setPositiveButton("OK", {dialog, i -> dialog.dismiss() })
                        .show()
                }
            } catch (e: Exception){
                AlertDialog.Builder(this@NewLogin)
                    .setTitle("DB")
                    .setMessage(" X- Autenticação não autorizada -X ")
                    .setPositiveButton("OK", {dialog, i -> dialog.dismiss() })
                    .show()
            }
        }
        //Deletar login do banco de dados
        btDeluser.setOnClickListener {

            var login = LoginBean(id = 1, email = "gotrip@gotrip.com.br", senha = "java")
                dao?.insertAll(login)

            try {
                dao.delete(login)
                AlertDialog.Builder(this@NewLogin)
                    .setTitle("DB")
                    .setMessage(" [ Conta Deletada ] ")
                    .setPositiveButton("OK", { dialog, i -> dialog.dismiss() })
                    .show()
            } catch (e: Exception) {
                AlertDialog.Builder(this@NewLogin)
                    .setTitle("DB")
                    .setMessage(" [ ERROR ] ")
                    .setPositiveButton("OK", { dialog, i -> dialog.dismiss() })
                    .show()
            }
        }
    }
}


//var login = LoginBean(id = 1, edNemail.text.trim().toString(), edNsenha.text.trim().toString())