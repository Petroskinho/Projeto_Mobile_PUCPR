package lugares

import android.os.Bundle
import android.view.View
import android.widget.Button
import android.widget.EditText
import android.widget.Toast
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.ativ_somativ_2.R
import database.BancoDB

class CadastroLugaresActivity : AppCompatActivity() {

    private var lugarSelecionado: Lugar? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        enableEdgeToEdge()

        setContentView(R.layout.activity_cadastro_lugares)

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

        val recycler =
            findViewById<RecyclerView>(
                R.id.recyclerLugaresCadastro
            )

        val editDescricao =
            findViewById<EditText>(
                R.id.editDescricao
            )

        val btnSalvar =
            findViewById<Button>(
                R.id.btnSalvarLugar
            )

        val banco = BancoDB(this)

        val repository =
            LugarRepository(banco)

        val idLugar =
            intent.getLongExtra(
                "ID_LUGAR",
                0
            )

        // Modo edição
        if (idLugar != 0L) {

            recycler.visibility = View.GONE

            editDescricao.visibility = View.VISIBLE

            btnSalvar.visibility = View.VISIBLE

            val lugar =
                repository.buscarPorId(idLugar)

            lugarSelecionado = lugar

            editDescricao.setText(
                lugar?.descricao
            )
        }
        else {

            editDescricao.visibility = View.GONE

            btnSalvar.visibility = View.GONE

            val lugares = listOf(

                Lugar(
                    0,
                    "Jardim Botânico",
                    "Curitiba",
                    R.drawable.jardimbotanico,
                    ""
                ),

                Lugar(
                    0,
                    "Shopping São José",
                    "São José dos Pinhais",
                    R.drawable.shopsjp,
                    ""
                ),

                Lugar(
                    0,
                    "Lavandário",
                    "Evelyn",
                    R.drawable.chatgpt_image_7_de_jun__de_2026__23_40_32,
                    ""
                )
            )

            recycler.layoutManager =
                LinearLayoutManager(this)

            val adapter =
                LugarAdapter(
                    lugares,

                    { lugar ->

                        lugarSelecionado = lugar

                        editDescricao.visibility =
                            View.VISIBLE

                        btnSalvar.visibility =
                            View.VISIBLE

                        Toast.makeText(
                            this,
                            "${lugar.nome} selecionado",
                            Toast.LENGTH_SHORT
                        ).show()
                    },

                    { }
                )

            recycler.adapter = adapter
        }

        btnSalvar.setOnClickListener {

            val descricao =
                editDescricao.text
                    .toString()
                    .trim()

            if (descricao.isEmpty()) {

                editDescricao.error =
                    "Digite uma descrição"

                return@setOnClickListener
            }

            val lugar =
                lugarSelecionado

            if (lugar == null) {

                Toast.makeText(
                    this,
                    "Selecione um lugar",
                    Toast.LENGTH_SHORT
                ).show()

                return@setOnClickListener
            }

            if (idLugar == 0L) {

                val novoLugar = Lugar(
                    0,
                    lugar.nome,
                    lugar.cidade,
                    lugar.imagem,
                    descricao
                )

                repository.adicionarLugar(
                    novoLugar
                )

            } else {

                val lugarEditado = Lugar(
                    idLugar,
                    lugar.nome,
                    lugar.cidade,
                    lugar.imagem,
                    descricao
                )

                repository.editarLugar(
                    lugarEditado
                )
            }

            Toast.makeText(
                this,
                "Salvo com sucesso!",
                Toast.LENGTH_SHORT
            ).show()

            finish()
        }
    }
}