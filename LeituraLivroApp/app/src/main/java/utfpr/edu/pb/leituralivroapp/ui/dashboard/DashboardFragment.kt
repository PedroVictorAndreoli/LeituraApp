package utfpr.edu.pb.leituralivroapp.ui.dashboard

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.SearchView
import android.widget.TextView
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import androidx.room.Room
import utfpr.edu.pb.leituralivroapp.adapter.AdapterLivro
import utfpr.edu.pb.leituralivroapp.dao.DaoLivro
import utfpr.edu.pb.leituralivroapp.database.Database
import utfpr.edu.pb.leituralivroapp.databinding.FragmentDashboardBinding
import utfpr.edu.pb.leituralivroapp.model.Livro

class DashboardFragment : Fragment() {

    private var _binding: FragmentDashboardBinding? = null

    // This property is only valid between onCreateView and
    // onDestroyView.
    private val binding get() = _binding!!
    lateinit var db : Database
    lateinit var dao: DaoLivro
    private var listaDigitada = ArrayList<Livro>()

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentDashboardBinding.inflate(inflater, container, false)
        val root: View = binding.root
        if (this.activity != null) {
            db =
                Room.databaseBuilder(
                    this.requireActivity(),
                    Database::class.java,
                    "livros.db"
                ).allowMainThreadQueries().build()
        }
        return root
    }

    override fun onResume() {
        super.onResume()
        dao = db.daoLivro()
        binding.recyclerView.adapter = this.activity?.let { AdapterLivro(it, dao.buscarTodos()) }
        listaDigitada = dao.buscarTodos() as ArrayList<Livro>
       /*binding.buscaView.setOnQueryTextListener(object : SearchView.OnQueryTextListener {
            override fun onQueryTextSubmit(query: String?): Boolean {
                return false
            }

            override fun onQueryTextChange(newText: String?): Boolean {
                listaDaBusca(newText.toString())
                return true
            })*/
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}