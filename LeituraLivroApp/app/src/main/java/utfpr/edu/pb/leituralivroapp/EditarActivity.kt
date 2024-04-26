package utfpr.edu.pb.leituralivroapp

import android.app.Activity
import android.content.Intent
import android.net.Uri
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.ImageView
import androidx.activity.result.contract.ActivityResultContracts
import androidx.room.Room
import coil.load
import utfpr.edu.pb.leituralivroapp.dao.DaoLivro
import utfpr.edu.pb.leituralivroapp.database.Database
import utfpr.edu.pb.leituralivroapp.databinding.ActivityEditarBinding
import utfpr.edu.pb.leituralivroapp.model.Livro

class EditarActivity : AppCompatActivity() {

    private lateinit var binding : ActivityEditarBinding
    lateinit var imageView: ImageView
    lateinit var uri : String
    lateinit var db : Database
    lateinit var dao: DaoLivro
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityEditarBinding.inflate(layoutInflater)
        setContentView(binding.root)

        imageView = binding.addImage


        val id : Long = intent.getLongExtra("KEY_ID", 0L)
        db = Room.databaseBuilder(
                this,
                Database::class.java,
                "livros.db"
            ).allowMainThreadQueries().build()
            dao = db.daoLivro()
            val livro : Livro = dao.buscarId(id)
            uri = livro.img
            binding.addImage.load(uri)
            binding.autorEdit.setText(livro.autor)
            binding.nomeEdit.setText(livro.titulo)
            binding.generoEdit.setText(livro.genero)

            imageView.setOnClickListener(){
            val intent = Intent(Intent.ACTION_GET_CONTENT)
            intent.type = "image/*"
            intent.addCategory(Intent.CATEGORY_OPENABLE)
            startActivity(intent)
            launcherArquivo.launch(intent)
        }

        val nome = binding.nomeEdit.text.toString()
        val genero = binding.generoEdit.text.toString()
        val autor = binding.autorEdit.text.toString()

            binding.editar.setOnClickListener{
                dao.editar(
                    Livro(
                        id = id,
                        img = uri,
                        autor = autor,
                        titulo = nome,
                        genero = genero
                    )
                )
                finish()
            }

        binding.excluir.setOnClickListener{
            dao.excluir(livro)
            db.daoLeitura().excluir(db.daoLeitura().buscarId(livro.id))
            finish()
        }

        }

    val launcherArquivo = registerForActivityResult(ActivityResultContracts.StartActivityForResult()){
            result->
        if (result.resultCode == Activity.RESULT_OK) {
            val arquivo : Intent? = result.data
            if (arquivo != null)
                uri = arquivo.data.toString()
            imageView.load(uri)
        }
    }

    }