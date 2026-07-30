package auth

import android.os.Bundle
import android.widget.Button
import android.widget.EditText
import android.widget.Toast
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import com.example.ativ_somativ_2.R
import database.BancoDB

class CadastroUsuarioActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        enableEdgeToEdge()

        setContentView(R.layout.activity_cadastro)

        ViewCompat.setOnApplyWindowInsetsListener(
            findViewById(android.R.id.content)
        ) { view, insets ->

            val systemBars =
                insets.getInsets(
                    WindowInsetsCompat.Type.systemBars()
                )

            view.setPadding(
                systemBars.left,
                systemBars.top,
                systemBars.right,
                systemBars.bottom
            )

            insets
        }

        // Componentes da tela
        val editNome =
            findViewById<EditText>(R.id.editUsername)

        val editEmail =
            findViewById<EditText>(R.id.editEmail)

        val editSenha =
            findViewById<EditText>(R.id.editSenha)

        val editRepetirSenha =
            findViewById<EditText>(R.id.editRepetSenha)

        val btnCadastrar =
            findViewById<Button>(R.id.btnEntrar)

        // Banco e Repository
        val banco = BancoDB(this)

        val repository =
            UsuarioRepository(banco)

        btnCadastrar.setOnClickListener {

            val nome =
                editNome.text.toString().trim()

            val email =
                editEmail.text
                    .toString()
                    .trim()
                    .lowercase()

            val senha =
                editSenha.text.toString()

            val repetirSenha =
                editRepetirSenha.text.toString()

            // Verifica campos vazios
            if (
                nome.isEmpty() ||
                email.isEmpty() ||
                senha.isEmpty() ||
                repetirSenha.isEmpty()
            ) {

                Toast.makeText(
                    this,
                    "Preencha todos os campos",
                    Toast.LENGTH_SHORT
                ).show()

                return@setOnClickListener
            }

            // Verifica se as senhas são iguais
            if (senha != repetirSenha) {

                Toast.makeText(
                    this,
                    "As senhas não coincidem",
                    Toast.LENGTH_SHORT
                ).show()

                return@setOnClickListener
            }

            // Cria o objeto Usuario
            val usuario = Usuario(
                nome = nome,
                email = email,
                senha = senha
            )

            // Salva no banco
            val id =
                repository.cadastrarUsuario(
                    usuario
                )

            if (id > 0) {

                Toast.makeText(
                    this,
                    "Usuário cadastrado com sucesso!",
                    Toast.LENGTH_SHORT
                ).show()

                finish()

            } else {

                Toast.makeText(
                    this,
                    "Erro ao cadastrar usuário",
                    Toast.LENGTH_SHORT
                ).show()
            }
        }
    }
}