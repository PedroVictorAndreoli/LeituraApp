package utfpr.edu.pb.leituralivroapp.ui.home

import android.Manifest
import android.content.pm.PackageManager
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.core.app.ActivityCompat
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import androidx.room.Room
import utfpr.edu.pb.leituralivroapp.adapter.AdapterLeitura
import utfpr.edu.pb.leituralivroapp.adapter.AdapterLivro
import utfpr.edu.pb.leituralivroapp.dao.DaoLeitura
import utfpr.edu.pb.leituralivroapp.dao.DaoLivro
import utfpr.edu.pb.leituralivroapp.database.Database
import utfpr.edu.pb.leituralivroapp.databinding.FragmentHomeBinding
import utfpr.edu.pb.leituralivroapp.model.Leitura
import utfpr.edu.pb.leituralivroapp.model.Livro

class HomeFragment : Fragment() {

    private var _binding: FragmentHomeBinding? = null

    // This property is only valid between onCreateView and
    // onDestroyView.
    private val binding get() = _binding!!
    lateinit var db : Database
    lateinit var dao: DaoLeitura

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentHomeBinding.inflate(inflater, container, false)
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
        dao = db.daoLeitura()
        binding.recyclerView.adapter = this.activity?.let { AdapterLeitura(it, dao.buscarTodos(),db.daoLivro()) }
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }

}