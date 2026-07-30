package auth

import android.content.Intent
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

class LoginActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        enableEdgeToEdge()

        setContentView(R.layout.activity_login)

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

        val email =
            findViewById<EditText>(R.id.editEmail)

        val senha =
            findViewById<EditText>(R.id.editSenha)

        val btnEntrar =
            findViewById<Button>(R.id.btnEntrar)

        val banco = BancoDB(this)

        val usuarioRepository =
            UsuarioRepository(banco)

        btnEntrar.setOnClickListener {

            val emailDigitado =
                email.text
                    .toString()
                    .trim()
                    .lowercase()

            val senhaDigitado =
                senha.text.toString()

            val usuario =
                usuarioRepository.validarLogin(
                    emailDigitado,
                    senhaDigitado
                )

            if (usuario != null) {

                Toast.makeText(
                    this,
                    "Login realizado com sucesso!",
                    Toast.LENGTH_SHORT
                ).show()

                val intent = Intent(
                    this,
                    lugares.ListaLugaresActivity::class.java
                )

                startActivity(intent)

                finish()

            } else {

                Toast.makeText(
                    this,
                    "E-mail ou senha inválidos",
                    Toast.LENGTH_SHORT
                ).show()
            }
        }
    }
}