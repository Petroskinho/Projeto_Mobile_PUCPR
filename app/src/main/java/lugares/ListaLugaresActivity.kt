package lugares

import android.content.Intent
import android.os.Bundle
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AlertDialog
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.ativ_somativ_2.R
import com.google.android.material.floatingactionbutton.FloatingActionButton
import database.BancoDB

class ListaLugaresActivity : AppCompatActivity() {

    private lateinit var recycler: RecyclerView

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        enableEdgeToEdge()

        setContentView(R.layout.activity_lista_viagens)

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

        val btnFlutuanteCadastro =
            findViewById<FloatingActionButton>(
                R.id.btnFlutuanteCadastroLugar
            )

        btnFlutuanteCadastro.setOnClickListener {

            val intent = Intent(
                this,
                CadastroLugaresActivity::class.java
            )

            startActivity(intent)
        }

        recycler =
            findViewById(R.id.recyclelugares)

        recycler.layoutManager =
            LinearLayoutManager(this)

        carregarLugares()
    }

    override fun onResume() {
        super.onResume()

        carregarLugares()
    }

    private fun carregarLugares() {

        val banco = BancoDB(this)

        val repository =
            LugarRepository(banco)

        val lista =
            repository.listarLugares()

        val adapter = LugarAdapter(

            lista,

            // Clique normal -> Editar
            { lugar ->

                val intent = Intent(
                    this,
                    CadastroLugaresActivity::class.java
                )

                intent.putExtra(
                    "ID_LUGAR",
                    lugar.id
                )

                startActivity(intent)
            },

            // Clique longo -> Excluir
            { lugar ->

                AlertDialog.Builder(this)
                    .setTitle("Excluir lugar")
                    .setMessage(
                        "Deseja excluir ${lugar.nome}?"
                    )
                    .setPositiveButton("Sim") { _, _ ->

                        repository.removerLugar(lugar)

                        carregarLugares()
                    }
                    .setNegativeButton(
                        "Cancelar",
                        null
                    )
                    .show()
            }
        )

        recycler.adapter = adapter
    }
}