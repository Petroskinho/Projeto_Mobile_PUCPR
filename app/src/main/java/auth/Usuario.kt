package auth

data class Usuario(
    var id: Long = 0,
    val nome: String,
    val email: String,
    val senha: String
)