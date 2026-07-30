package auth

import database.BancoDB

class UsuarioRepository(
    private val bancoDB: BancoDB
) {

    fun cadastrarUsuario(usuario: Usuario): Long {
        return bancoDB.addUsuario(usuario)
    }

    fun listarUsuarios(): List<Usuario> {
        return bancoDB.getAllUsuarios()
    }

    fun validarLogin(
        email: String,
        senha: String
    ): Usuario? {

        return bancoDB.validarLogin(
            email,
            senha
        )
    }
}