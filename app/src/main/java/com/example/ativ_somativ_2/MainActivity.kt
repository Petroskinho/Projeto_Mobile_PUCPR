package com.example.ativ_somativ_2

import android.content.Intent
import android.os.Bundle
import android.widget.Button
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat

class MainActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        setContentView(R.layout.activity_main)

        val btnLogin = findViewById<Button>(R.id.btnLogin)
        val btnCadastro = findViewById<Button>(R.id.btnCadastro)

        btnLogin.setOnClickListener {

        val intent = Intent(this, auth.LoginActivity::class.java)
        startActivity(intent)

        }

        btnCadastro.setOnClickListener {

          val intent = Intent(this, auth.CadastroUsuarioActivity::class.java)
          startActivity(intent)

        }
    }
}
