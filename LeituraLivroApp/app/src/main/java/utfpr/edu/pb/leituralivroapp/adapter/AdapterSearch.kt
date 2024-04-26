package utfpr.edu.pb.leituralivroapp.adapter

import android.content.Context
import android.content.Intent
import android.util.Log
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import coil.load
import utfpr.edu.pb.leituralivroapp.EditarActivity
import utfpr.edu.pb.leituralivroapp.LeituraActivity
import utfpr.edu.pb.leituralivroapp.databinding.BookListBinding
import utfpr.edu.pb.leituralivroapp.databinding.SearchListBinding
import utfpr.edu.pb.leituralivroapp.model.Livro

class AdapterSearch
    (val context: Context,
     livros: List<Livro>) : RecyclerView.Adapter<AdapterSearch.ViewHolderSearch>(){
    private var livros =  livros.toMutableList()

    inner class ViewHolderSearch(val binding: SearchListBinding, val context: Context)
        : RecyclerView.ViewHolder(binding.root){
        private lateinit var livro: Livro

        init {
            Log.i("AdapterPessoa","entrou no init")
            itemView.setOnClickListener {
                Log.i("AdapterPessoa","entrou no clique")
                if(::livro.isInitialized) {
                    Log.i("ItemView", "Pessoa: ${livro.id}")
                    val intent = Intent(context, EditarActivity::class.java)
                        .apply { putExtra("KEY_ID", livro.id) }
                    context.startActivity(intent)
                }
            }
        }
        fun vincula(livro: Livro){//Método de vinculação VIEW -> ELEMENTO DA LISTA
            this.livro = livro
            binding.imagem.load(livro.img)
            binding.autor.setText(livro.autor)
            binding.genero.setText(livro.genero)
            binding.titulo.setText(livro.titulo)
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolderSearch {
        val layout = LayoutInflater.from(context)
        val binding = SearchListBinding.inflate(layout, parent, false)
        return ViewHolderSearch(binding,context)
    }

    override fun getItemCount(): Int {
        return  livros.size
    }

    override fun onBindViewHolder(holder: ViewHolderSearch, position: Int) {
        val livro = livros[position]
        holder.vincula(livro)
    }
}