package utfpr.edu.pb.leituralivroapp.database

import androidx.room.Database
import androidx.room.RoomDatabase
import utfpr.edu.pb.leituralivroapp.dao.DaoLeitura
import utfpr.edu.pb.leituralivroapp.dao.DaoLivro
import utfpr.edu.pb.leituralivroapp.model.Leitura
import utfpr.edu.pb.leituralivroapp.model.Livro

@Database(entities = [Livro::class,Leitura::class], version = 1)
abstract class Database : RoomDatabase() {
    abstract fun daoLeitura() : DaoLeitura
    abstract fun daoLivro() : DaoLivro
}