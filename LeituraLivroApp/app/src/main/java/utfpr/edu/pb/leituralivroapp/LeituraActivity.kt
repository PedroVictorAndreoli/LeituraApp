package utfpr.edu.pb.leituralivroapp

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.room.Room
import coil.load
import okhttp3.internal.notifyAll
import utfpr.edu.pb.leituralivroapp.database.Database
import utfpr.edu.pb.leituralivroapp.databinding.ActivityLeituraBinding
import utfpr.edu.pb.leituralivroapp.model.Leitura
import utfpr.edu.pb.leituralivroapp.model.Livro

class LeituraActivity : AppCompatActivity() {
    private lateinit var binding: ActivityLeituraBinding
    val listaImagens = listOf(R.drawable.ic_star_empty_24dp, R.drawable.ic_star)
    var posicaoEstrela = 0
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityLeituraBinding.inflate(layoutInflater)
        setContentView(binding.root)
        val id : Long = intent.getLongExtra("KEY_ID", 0L)

        val db = Room.databaseBuilder(
            this,
            Database::class.java,
            "livros.db"
        ).allowMainThreadQueries().build()
        val dao = db.daoLivro()
        val livro : Livro = dao.buscarId(id)
        binding.addImage.load(livro.img)
        notaestrela()
        binding.botao.setOnClickListener(){
            val dao = db.daoLeitura()
            dao.salvar(Leitura(livroOwnerId = livro.id, review = binding.review.editText!!.text.toString(),nota = posicaoEstrela))
            finish()
        }
    }

    private fun notaestrela() {
        binding.star1.setOnClickListener{
            when(posicaoEstrela){
                0,2,3->{
                    binding.star1.setImageResource(listaImagens[1])
                    binding.star2.setImageResource(listaImagens[0])
                    binding.star3.setImageResource(listaImagens[0])
                    posicaoEstrela = 1
                }
                1->{
                    binding.star1.setImageResource(listaImagens[0])
                    binding.star2.setImageResource(listaImagens[0])
                    binding.star3.setImageResource(listaImagens[0])
                    posicaoEstrela = 0
                }
            }
        }
        binding.star2.setOnClickListener{
            when(posicaoEstrela){
                0,1,3->{
                    binding.star1.setImageResource(listaImagens[1])
                    binding.star2.setImageResource(listaImagens[1])
                    binding.star3.setImageResource(listaImagens[0])
                    posicaoEstrela = 2
                }

                2->{
                    binding.star1.setImageResource(listaImagens[0])
                    binding.star2.setImageResource(listaImagens[0])
                    binding.star3.setImageResource(listaImagens[0])
                    posicaoEstrela = 0
                }
            }
        }
        binding.star3.setOnClickListener{
            when(posicaoEstrela){
                0,2,1->{
                    binding.star1.setImageResource(listaImagens[1])
                    binding.star2.setImageResource(listaImagens[1])
                    binding.star3.setImageResource(listaImagens[1])
                    posicaoEstrela = 3
                }
                3->{
                    binding.star1.setImageResource(listaImagens[0])
                    binding.star2.setImageResource(listaImagens[0])
                    binding.star3.setImageResource(listaImagens[0])
                    posicaoEstrela = 0
                }
            }
        }
    }
}