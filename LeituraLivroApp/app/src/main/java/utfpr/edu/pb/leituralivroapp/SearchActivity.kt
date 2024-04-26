package utfpr.edu.pb.leituralivroapp

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.SearchView
import android.widget.Toast
import androidx.room.Room
import utfpr.edu.pb.leituralivroapp.adapter.AdapterLivro
import utfpr.edu.pb.leituralivroapp.adapter.AdapterSearch
import utfpr.edu.pb.leituralivroapp.dao.DaoLeitura
import utfpr.edu.pb.leituralivroapp.dao.DaoLivro
import utfpr.edu.pb.leituralivroapp.database.Database
import utfpr.edu.pb.leituralivroapp.databinding.ActivitySearchBinding
import utfpr.edu.pb.leituralivroapp.model.Livro
import java.util.Locale

class SearchActivity : AppCompatActivity() {
    lateinit var binding : ActivitySearchBinding
    lateinit var db : Database
    lateinit var dao: DaoLivro
    private var listaDigitada = ArrayList<Livro>()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivitySearchBinding.inflate(layoutInflater)
        setContentView(binding.root)
        db =
            Room.databaseBuilder(
                this,
                Database::class.java,
                "livros.db"
            ).allowMainThreadQueries().build()
    }


    override fun onResume() {
        super.onResume()
        dao = db.daoLivro()
        binding.recyclerView.adapter = AdapterSearch(this, dao.buscarTodos())
        listaDigitada = dao.buscarTodos() as ArrayList<Livro>
        binding.buscaView.setOnQueryTextListener(object : SearchView.OnQueryTextListener {
             override fun onQueryTextSubmit(query: String?): Boolean {
                 return false
             }

             override fun onQueryTextChange(newText: String?): Boolean {
                 listaDaBusca(newText.toString())
                 return true
             }
        })
    }

    private fun listaDaBusca(query: String) {
        if (query!=null){
            val listaFiltrada = ArrayList<Livro>()
            for(i in listaDigitada) {
                if (i.titulo.lowercase(Locale.ROOT).contains(query) || i.autor.lowercase(Locale.ROOT).contains(query) || i.genero.lowercase(Locale.ROOT).contains(query)){
                    listaFiltrada.add(i)
                }
            }
            if (listaFiltrada.isEmpty()){
                Toast.makeText(this,"Sem Resultado", Toast.LENGTH_SHORT).show()
            }else{
                binding.recyclerView.adapter = AdapterSearch(this,listaFiltrada)
            }
        }
    }
}