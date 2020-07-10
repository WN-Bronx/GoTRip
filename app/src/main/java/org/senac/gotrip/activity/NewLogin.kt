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
import org.senac.gotrip.base.AppDatabase


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
            //Variaveil com valores informados na tela
            var nome: String = edTextNewNome.text.trim().toString()
            var email: String = edTextNewEmail.text.trim().toString()
            var senha: String = edTextNewSenha.text.trim().toString()

            var validaCampos: String = this.validarCampos(nome, email, senha)
            if(validaCampos.equals("")){
                //Cria um novo registro no banco
                var l:LoginBean = LoginBean(nome, email, senha) //Objeto que será salvo
                dao.insertAll(l) //Salva o objeto no banco

                //Tenta fazer o login com o usuário que acabou de ser criado - se funcionar... é pq cadastrou com sucesso
                l = dao.fazerLogin(l.email.toString(), l.senha.toString())

                try {
                    if(l != null){
                        this.exibirAlerta("Criar Conta", "[ Login cadastrado com sucesso :D ]")
                    } else{
                        this.exibirAlerta("Criar Conta", " [ Falha no cadastro ] "+l.email+ "   -   "+l.senha)
                    }
                } catch (e: Exception){
                    this.exibirAlerta("Criar Conta", "X- Autenticação não autorizada -X")
                }
            } else {
                this.exibirAlerta("Criar Conta", validaCampos)
            }
        }

        //Deletar login do banco de dados
        btDeluser.setOnClickListener {
            var validaCampos: String = this.validarCamposDeletar(edTextNewEmail.text.trim().toString(), edTextNewSenha.text.trim().toString())
            if(validaCampos.equals("")){
                try {
                    //TEnta fazer login com os dados informados
                    var login = dao.fazerLogin(edTextNewEmail.text.trim().toString(), edTextNewSenha.text.trim().toString())
                    if(login != null){//Só deleta se encontrar o usuário
                        dao.delete(login)
                        this.exibirAlerta("Deletar Conta", " [ Conta Deletada ]")
                    } else {
                        this.exibirAlerta("Deletar Conta", " [ Conta não encontrada ]")
                    }
                } catch (e: Exception) {
                    this.exibirAlerta("Deletar Conta", "X- Falha ao deletar conta -X")
                }
            } else {
                this.exibirAlerta("Deletar Conta", validaCampos)
            }
        }
    }

    private fun exibirAlerta(titulo: String, msg: String) {
        AlertDialog.Builder(this@NewLogin)
            .setTitle(titulo)
            .setMessage(msg)
            .setPositiveButton("OK", {dialog, i -> dialog.dismiss() })
            .show()
    }

    //Valida se os campos foram preenchido e retorna a informação do que ainda falta ser preenchido
    private fun validarCampos(nome:String, email:String, senha:String): String{
        var result: String = ""

        if (nome.equals(""))
            result = "-> Favor informar o nome do usuário\n"

        if(email.equals(""))
            result += "-> Favor informar o email do usuário\n"

        if(senha.equals(""))
            result += "-> Favor informar a senha do usuário"

        return result
    }

    //Valida apenas email e senha
    private fun validarCamposDeletar(email:String, senha:String): String{
        var result: String = ""

        if(email.equals(""))
            result = "-> Favor informar o email do usuário\n"

        if(senha.equals(""))
            result += "-> Favor informar a senha do usuário"

        return result
    }
}