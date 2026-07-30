package lugares

import database.BancoDB

class LugarRepository(
    private val bancoHelper: BancoDB
) {

    fun listarLugares(): List<Lugar> {
        return bancoHelper.getAllLugares()
    }

    fun adicionarLugar(lugar: Lugar): Long {
        return bancoHelper.addLugar(lugar)
    }

    fun editarLugar(lugar: Lugar): Int {
        return bancoHelper.editLugar(lugar)
    }

    fun removerLugar(lugar: Lugar): Int {
        return bancoHelper.removeLugar(lugar)
    }

    fun buscarPorId(id: Long): Lugar? {
        return bancoHelper.getLugarById(id)
    }
}