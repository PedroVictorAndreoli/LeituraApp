package utfpr.edu.pb.leituralivroapp.dao

import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Insert
import androidx.room.Query
import androidx.room.Update
import utfpr.edu.pb.leituralivroapp.model.Leitura

@Dao
interface DaoLeitura {
    @Query("SELECT * FROM Leitura")
    fun buscarTodos() : List<Leitura>

    @Query("SELECT * FROM Leitura WHERE id = :id")
    fun buscarId(id : Long) : Leitura

    @Insert
    fun salvar(pessoa: Leitura)
    //não pode passar o ID, pois ele é gerado automaticamente

    @Update
    fun editar(pessoa: Leitura)
    //Quando for editar, precisa passar tudo!

    @Delete
    fun excluir(pessoa: Leitura)
}