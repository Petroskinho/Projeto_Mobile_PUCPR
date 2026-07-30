package lugares

data class Lugar(
    var id: Long = 0,
    val nome: String,
    val cidade: String,
    val imagem: Int,
    val descricao: String
)