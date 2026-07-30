package database

import android.content.ContentValues
import android.content.Context
import android.database.sqlite.SQLiteDatabase
import android.database.sqlite.SQLiteOpenHelper
import android.util.Log
import auth.Usuario
import lugares.Lugar


class BancoDB(context: Context)
    : SQLiteOpenHelper(
    context,
    DATABASE_NAME,
    null,
    DATABASE_VERSION
) {

    companion object {

        const val DATABASE_NAME = "travelreview.db"
        const val DATABASE_VERSION = 1

        const val DB_TABLE_LUGARES = "lugares"

        const val DB_FIELD_ID = "id"
        const val DB_FIELD_NOME = "nome"
        const val DB_FIELD_CIDADE = "cidade"
        const val DB_FIELD_IMAGEM = "imagem"
        const val DB_FIELD_DESCRICAO = "descricao"

        const val DB_TABLE_USUARIOS = "usuarios"

        const val DB_FIELD_USUARIO_ID = "id"
        const val DB_FIELD_USUARIO_NOME = "nome"
        const val DB_FIELD_USUARIO_EMAIL = "email"
        const val DB_FIELD_USUARIO_SENHA = "senha"
    }

    override fun onCreate(db: SQLiteDatabase?) {

        val sql = """
        CREATE TABLE IF NOT EXISTS $DB_TABLE_LUGARES (
            $DB_FIELD_ID INTEGER PRIMARY KEY AUTOINCREMENT,
            $DB_FIELD_NOME TEXT,
            $DB_FIELD_CIDADE TEXT,
            $DB_FIELD_IMAGEM INTEGER,
            $DB_FIELD_DESCRICAO TEXT
        );
        """.trimIndent()

        val sqlUsuarios = """
        CREATE TABLE IF NOT EXISTS $DB_TABLE_USUARIOS (
            $DB_FIELD_USUARIO_ID INTEGER PRIMARY KEY AUTOINCREMENT,
            $DB_FIELD_USUARIO_NOME TEXT,
            $DB_FIELD_USUARIO_EMAIL TEXT,
            $DB_FIELD_USUARIO_SENHA TEXT
        );
    """.trimIndent()

        val database = db ?: return

        database.beginTransaction()

        try {
            Log.d("BANCO", "Criando tabelas")
            database.execSQL(sql)
            database.execSQL(sqlUsuarios)

            database.setTransactionSuccessful()

        } catch (e: Exception) {

            Log.e(
                "BancoHelper",
                e.localizedMessage ?: "Erro ao criar tabela"
            )

        } finally {

            database.endTransaction()
        }
    }

    override fun onUpgrade(
        db: SQLiteDatabase?,
        oldVersion: Int,
        newVersion: Int
    ) {
        // Implementar futuramente
    }

    fun getAllLugares(): MutableList<Lugar> {

        val lugares = mutableListOf<Lugar>()

        val db = readableDatabase

        val cursor = db.query(
            DB_TABLE_LUGARES,
            null,
            null,
            null,
            null,
            null,
            DB_FIELD_NOME
        )

        with(cursor) {

            while (moveToNext()) {

                val id =
                    getLong(getColumnIndexOrThrow(DB_FIELD_ID))

                val nome =
                    getString(getColumnIndexOrThrow(DB_FIELD_NOME))

                val cidade =
                    getString(getColumnIndexOrThrow(DB_FIELD_CIDADE))

                val imagem =
                    getInt(getColumnIndexOrThrow(DB_FIELD_IMAGEM))

                val descricao =
                    getString(getColumnIndexOrThrow(DB_FIELD_DESCRICAO))

                lugares.add(
                    Lugar(
                        id,
                        nome,
                        cidade,
                        imagem,
                        descricao
                    )
                )
            }

            close()
        }

        return lugares
    }

    fun addLugar(lugar: Lugar): Long {

        var id: Long = 0

        val db = writableDatabase

        val values = ContentValues().apply {

            put(DB_FIELD_NOME, lugar.nome)
            put(DB_FIELD_CIDADE, lugar.cidade)
            put(DB_FIELD_IMAGEM, lugar.imagem)
            put(DB_FIELD_DESCRICAO, lugar.descricao)
        }

        db.beginTransaction()

        try {

            id = db.insert(
                DB_TABLE_LUGARES,
                null,
                values
            )

            db.setTransactionSuccessful()

        } catch (e: Exception) {

            Log.e(
                "BancoHelper",
                e.localizedMessage ?: "Erro ao inserir"
            )

        } finally {

            db.endTransaction()
        }

        return id
    }

    fun editLugar(lugar: Lugar): Int {

        var count = 0

        val db = writableDatabase

        val values = ContentValues().apply {

            put(DB_FIELD_NOME, lugar.nome)
            put(DB_FIELD_CIDADE, lugar.cidade)
            put(DB_FIELD_IMAGEM, lugar.imagem)
            put(DB_FIELD_DESCRICAO, lugar.descricao)
        }

        val selection = "$DB_FIELD_ID = ?"

        val args = arrayOf(
            lugar.id.toString()
        )

        db.beginTransaction()

        try {

            count = db.update(
                DB_TABLE_LUGARES,
                values,
                selection,
                args
            )

            db.setTransactionSuccessful()

        } catch (e: Exception) {

            Log.e(
                "BancoHelper",
                e.localizedMessage ?: "Erro ao editar"
            )

        } finally {

            db.endTransaction()
        }

        return count
    }

    fun removeLugar(lugar: Lugar): Int {

        var count = 0

        val db = writableDatabase

        val selection = "$DB_FIELD_ID = ?"

        val args = arrayOf(
            lugar.id.toString()
        )

        db.beginTransaction()

        try {

            count = db.delete(
                DB_TABLE_LUGARES,
                selection,
                args
            )

            db.setTransactionSuccessful()

        } catch (e: Exception) {

            Log.e(
                "BancoHelper",
                e.localizedMessage ?: "Erro ao remover"
            )

        } finally {

            db.endTransaction()
        }

        return count
    }

    fun addUsuario(usuario: Usuario): Long {

        var id: Long = 0

        val db = writableDatabase

        val values = ContentValues().apply {

            put(DB_FIELD_USUARIO_NOME, usuario.nome)
            put(DB_FIELD_USUARIO_EMAIL, usuario.email)
            put(DB_FIELD_USUARIO_SENHA, usuario.senha)
        }

        id = db.insert(
            DB_TABLE_USUARIOS,
            null,
            values
        )

        return id
    }

    fun getAllUsuarios(): MutableList<Usuario> {

        val usuarios = mutableListOf<Usuario>()

        val db = readableDatabase

        val cursor = db.query(
            DB_TABLE_USUARIOS,
            null,
            null,
            null,
            null,
            null,
            DB_FIELD_USUARIO_NOME
        )

        with(cursor) {

            while (moveToNext()) {

                val id =
                    getLong(getColumnIndexOrThrow(DB_FIELD_USUARIO_ID))

                val nome =
                    getString(getColumnIndexOrThrow(DB_FIELD_USUARIO_NOME))

                val email =
                    getString(getColumnIndexOrThrow(DB_FIELD_USUARIO_EMAIL))

                val senha =
                    getString(getColumnIndexOrThrow(DB_FIELD_USUARIO_SENHA))

                usuarios.add(
                    Usuario(
                        id,
                        nome,
                        email,
                        senha
                    )
                )
            }

            close()
        }

        return usuarios
    }

    fun validarLogin(
        email: String,
        senha: String
    ): Usuario? {

        val db = readableDatabase

        val selection =
            "$DB_FIELD_USUARIO_EMAIL = ? AND $DB_FIELD_USUARIO_SENHA = ?"

        val args = arrayOf(
            email,
            senha
        )

        val cursor = db.query(
            DB_TABLE_USUARIOS,
            null,
            selection,
            args,
            null,
            null,
            null
        )

        var usuario: Usuario? = null

        with(cursor) {

            if (moveToFirst()) {

                usuario = Usuario(
                    getLong(getColumnIndexOrThrow(DB_FIELD_USUARIO_ID)),
                    getString(getColumnIndexOrThrow(DB_FIELD_USUARIO_NOME)),
                    getString(getColumnIndexOrThrow(DB_FIELD_USUARIO_EMAIL)),
                    getString(getColumnIndexOrThrow(DB_FIELD_USUARIO_SENHA))
                )
            }

            close()
        }

        return usuario
    }

    fun getLugarById(id: Long): Lugar? {

        val db = readableDatabase

        val cursor = db.query(
            DB_TABLE_LUGARES,
            null,
            "$DB_FIELD_ID = ?",
            arrayOf(id.toString()),
            null,
            null,
            null
        )

        var lugar: Lugar? = null

        with(cursor) {

            if (moveToFirst()) {

                lugar = Lugar(
                    getLong(getColumnIndexOrThrow(DB_FIELD_ID)),
                    getString(getColumnIndexOrThrow(DB_FIELD_NOME)),
                    getString(getColumnIndexOrThrow(DB_FIELD_CIDADE)),
                    getInt(getColumnIndexOrThrow(DB_FIELD_IMAGEM)),
                    getString(getColumnIndexOrThrow(DB_FIELD_DESCRICAO))
                )
            }

            close()
        }

        return lugar
    }
}