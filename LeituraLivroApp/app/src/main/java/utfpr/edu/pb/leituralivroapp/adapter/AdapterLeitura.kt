package utfpr.edu.pb.leituralivroapp.adapter

import android.content.Context
import android.content.Intent
import android.util.Log
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import coil.load
import utfpr.edu.pb.leituralivroapp.EditarActivity
import utfpr.edu.pb.leituralivroapp.EditarLeituraActivity
import utfpr.edu.pb.leituralivroapp.LeituraActivity
import utfpr.edu.pb.leituralivroapp.R
import utfpr.edu.pb.leituralivroapp.SearchActivity
import utfpr.edu.pb.leituralivroapp.dao.DaoLivro
import utfpr.edu.pb.leituralivroapp.databinding.BookListBinding
import utfpr.edu.pb.leituralivroapp.databinding.ReviewListBinding
import utfpr.edu.pb.leituralivroapp.model.Leitura

class AdapterLeitura (val context: Context,
leituras: List<Leitura>,daoLivro: DaoLivro) : RecyclerView.Adapter<AdapterLeitura.ViewHolderLeitura>(){
    private var leituras =  leituras.toMutableList()
    private var dao = daoLivro
    val listaImagens = listOf(R.drawable.ic_star_empty_24dp, R.drawable.ic_star)

    inner class ViewHolderLeitura(val binding: ReviewListBinding, val context: Context)
        : RecyclerView.ViewHolder(binding.root){
        private lateinit var leitura: Leitura

        init {
            Log.i("AdapterPessoa","entrou no init")
            itemView.setOnClickListener {
                Log.i("AdapterPessoa","entrou no clique")
                if(::leitura.isInitialized) {
                    Log.i("ItemView", "Pessoa: ${leitura.id}")
                    val intent = Intent(context, EditarLeituraActivity::class.java)
                        .apply { putExtra("KEY_ID", leitura.id) }
                    context.startActivity(intent)
                }
            }
        }
        fun vincula(leitura: Leitura){//Método de vinculação VIEW -> ELEMENTO DA LISTA
            this.leitura = leitura
            binding.imagem.load(dao.buscarId(leitura.livroOwnerId).img)
            binding.Review.setText(leitura.review)
            if (leitura.nota>=1){
                binding.star1.setImageResource(listaImagens[1])
                if (leitura.nota>=2) {
                    binding.star2.setImageResource(listaImagens[1])
                    if (leitura.nota==3)
                        binding.star3.setImageResource(listaImagens[1])
                }
            }
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolderLeitura {
        val layout = LayoutInflater.from(context)
        val binding = ReviewListBinding.inflate(layout, parent, false)
        return ViewHolderLeitura(binding,context)
    }

    override fun getItemCount(): Int {
        return leituras.size
    }

    override fun onBindViewHolder(holder: ViewHolderLeitura, position: Int) {
        val leitura = leituras[position]
        holder.vincula(leitura)
    }
}