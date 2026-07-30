package lugares

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.example.ativ_somativ_2.R

class LugarAdapter(
    private val lugares: List<Lugar>,
    private val onClickLugar: (Lugar) -> Unit,
    private val onLongClickLugar: (Lugar) -> Unit
) : RecyclerView.Adapter<LugarAdapter.LugarViewHolder>() {

    class LugarViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {

        val imagem: ImageView =
            itemView.findViewById(R.id.imgLugar)

        val titulo: TextView =
            itemView.findViewById(R.id.txtNome)

        val cidade: TextView =
            itemView.findViewById(R.id.txtCidade)

        val descricao: TextView =
            itemView.findViewById(R.id.txtDescricao)
    }

    override fun onCreateViewHolder(
        parent: ViewGroup,
        viewType: Int
    ): LugarViewHolder {

        val view = LayoutInflater
            .from(parent.context)
            .inflate(
                R.layout.item_lugares,
                parent,
                false
            )

        return LugarViewHolder(view)
    }

    override fun onBindViewHolder(
        holder: LugarViewHolder,
        position: Int
    ) {

        val lugar = lugares[position]

        holder.titulo.text = lugar.nome
        holder.cidade.text = lugar.cidade
        holder.descricao.text = lugar.descricao

        holder.imagem.setImageResource(lugar.imagem)

        holder.itemView.setOnClickListener {
            onClickLugar(lugar)
        }

        holder.itemView.setOnLongClickListener {

            onLongClickLugar(lugar)

            true
        }
    }

    override fun getItemCount(): Int {
        return lugares.size
    }
}