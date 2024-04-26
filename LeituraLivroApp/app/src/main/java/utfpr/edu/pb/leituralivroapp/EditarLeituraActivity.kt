package utfpr.edu.pb.leituralivroapp

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.ImageView
import androidx.room.Room
import coil.load
import utfpr.edu.pb.leituralivroapp.dao.DaoLeitura
import utfpr.edu.pb.leituralivroapp.dao.DaoLivro
import utfpr.edu.pb.leituralivroapp.database.Database
import utfpr.edu.pb.leituralivroapp.databinding.ActivityEditarBinding
import utfpr.edu.pb.leituralivroapp.databinding.ActivityEditarLeituraBinding
import utfpr.edu.pb.leituralivroapp.model.Leitura
import utfpr.edu.pb.leituralivroapp.model.Livro

class EditarLeituraActivity : AppCompatActivity() {
    private lateinit var binding : ActivityEditarLeituraBinding
    lateinit var uri : String
    lateinit var db : Database
    lateinit var dao: DaoLeitura
    lateinit var leitura: Leitura
    val listaImagens = listOf(R.drawable.ic_star_empty_24dp, R.drawable.ic_star)
    var posicaoEstrela = 0
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityEditarLeituraBinding.inflate(layoutInflater)
        setContentView(binding.root)
        val id : Long = intent.getLongExtra("KEY_ID", 0L)
        db = Room.databaseBuilder(
            this,
            Database::class.java,
            "livros.db"
        ).allowMainThreadQueries().build()
        dao = db.daoLeitura()
        leitura = dao.buscarId(id)
        binding.addImage.load(db.daoLivro().buscarId(dao.buscarId(id).livroOwnerId).img)
        posicaoEstrela = leitura.nota
        binding.nomeEdit.setText(leitura.review)
        notaestrela()
        binding.editar.setOnClickListener{
            dao.editar(
                Leitura(
                    id = id,
                    review = binding.nomeEdit.text.toString(),
                    livroOwnerId = leitura.id,
                    nota = posicaoEstrela
                )
            )
            finish()
        }

        binding.excluir.setOnClickListener{
            dao.excluir(leitura)
            finish()
        }
    }

    private fun notaestrela() {
        if (leitura.nota>=1){
            binding.star1.setImageResource(listaImagens[1])
            if (leitura.nota>=2) {
                binding.star2.setImageResource(listaImagens[1])
                if (leitura.nota==3)
                    binding.star3.setImageResource(listaImagens[1])
            }
        }
        binding.star1.setOnClickListener {
            when (posicaoEstrela) {
                0, 2, 3 -> {
                    binding.star1.setImageResource(listaImagens[1])
                    binding.star2.setImageResource(listaImagens[0])
                    binding.star3.setImageResource(listaImagens[0])
                    posicaoEstrela = 1
                }

                1 -> {
                    binding.star1.setImageResource(listaImagens[0])
                    binding.star2.setImageResource(listaImagens[0])
                    binding.star3.setImageResource(listaImagens[0])
                    posicaoEstrela = 0
                }
            }
        }
        binding.star2.setOnClickListener {
            when (posicaoEstrela) {
                0, 1, 3 -> {
                    binding.star1.setImageResource(listaImagens[1])
                    binding.star2.setImageResource(listaImagens[1])
                    binding.star3.setImageResource(listaImagens[0])
                    posicaoEstrela = 2
                }

                2 -> {
                    binding.star1.setImageResource(listaImagens[0])
                    binding.star2.setImageResource(listaImagens[0])
                    binding.star3.setImageResource(listaImagens[0])
                    posicaoEstrela = 0
                }
            }
        }
        binding.star3.setOnClickListener {
            when (posicaoEstrela) {
                0, 2, 1 -> {
                    binding.star1.setImageResource(listaImagens[1])
                    binding.star2.setImageResource(listaImagens[1])
                    binding.star3.setImageResource(listaImagens[1])
                    posicaoEstrela = 3
                }

                3 -> {
                    binding.star1.setImageResource(listaImagens[0])
                    binding.star2.setImageResource(listaImagens[0])
                    binding.star3.setImageResource(listaImagens[0])
                    posicaoEstrela = 0
                }
            }
        }
    }
}