package utfpr.edu.pb.leituralivroapp.model

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity
data class Leitura(
    @PrimaryKey(autoGenerate = true) var id : Long = 0L,
    val livroOwnerId: Long,
    val review: String,
    val nota: Int
)