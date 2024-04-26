package utfpr.edu.pb.leituralivroapp.dao

import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Insert
import androidx.room.Query
import androidx.room.Update
import utfpr.edu.pb.leituralivroapp.model.Livro

@Dao
interface DaoLivro {

    @Query("SELECT titulo FROM Livro")
    fun buscaTitulo() : String

    @Query("SELECT * FROM Livro")
    fun buscarTodos() : List<Livro>

    @Query("SELECT * FROM Livro WHERE id = :id")
    fun buscarId(id : Long) : Livro

    @Insert
    fun salvar(pessoa: Livro)
    //não pode passar o ID, pois ele é gerado automaticamente

    @Update
    fun editar(pessoa: Livro)
    //Quando for editar, precisa passar tudo!

    @Delete
    fun excluir(pessoa: Livro)

}