package utfpr.edu.pb.leituralivroapp.model


import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity
data class Livro (
    @PrimaryKey(autoGenerate = true) var id : Long = 0L,
    val titulo : String,
    val autor : String,
    val genero : String,
    val img : String
)