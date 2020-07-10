package org.senac.gotrip.activity

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Button
import android.widget.EditText
import android.widget.Toast
import androidx.appcompat.app.AlertDialog
import androidx.room.Room
import kotlinx.android.synthetic.main.login_principal.*
import org.senac.gotrip.R
import org.senac.gotrip.bean.LoginBean
import org.senac.gotrip.base.AppDatabase

class MainActivity : AppCompatActivity() {

    @Override
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
        val bttLogin = findViewById<Button>(R.id.bttLogin)

        fun openNextActivity() {
            val intent = Intent(this, NewLogin::class.java)
            startActivity(intent)
        }

        bttConta.setOnClickListener {
            openNextActivity()
        }

        fun openNextActivity2() {
            val intent = Intent(this, MenuPrincipal::class.java)
            startActivity(intent)
        }


        bttLogin.setOnClickListener {
            var email: String = edtEmail.text.trim().toString()
            var senha: String = edtSenha.text.trim().toString()

            var validaCampos: String = this.validarCampos(email, senha)
            if(validaCampos.equals("")) {
                //Busca o login do banco de dados
                var l: LoginBean =
                    dao.fazerLogin(edtEmail.text.trim().toString(), edtSenha.text.trim().toString())
                try {
                    if (l != null) {
                        Toast.makeText(
                            this@MainActivity,
                            "Bem-Vindo(a) " + l.nome,
                            Toast.LENGTH_LONG
                        ).show()
                        openNextActivity2()
                    } else {
                        this.exibirAlerta(
                            "Login",
                            " [ Falha ao fazer login ] " + l.email + "   -   " + l.senha
                        )
                    }
                } catch (e: Exception) {
                    this.exibirAlerta("Login", "X- Autenticação não autorizada -X ")
                }
            } else {
                this.exibirAlerta("Login", validaCampos)
            }
        }

    }

    private fun exibirAlerta(titulo: String, msg: String) {
        AlertDialog.Builder(this@MainActivity)
            .setTitle(titulo)
            .setMessage(msg)
            .setPositiveButton("OK", {dialog, i -> dialog.dismiss() })
            .show()
    }

    //Valida se os campos foram preenchido e retorna a informação do que ainda falta ser preenchido
    private fun validarCampos(email:String, senha:String): String{
        var result: String = ""

        if(email.equals(""))
            result += "-> Favor informar o email do usuário\n"

        if(senha.equals(""))
            result += "-> Favor informar a senha do usuário"

        return result
    }
}
