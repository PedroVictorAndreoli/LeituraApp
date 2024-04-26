package utfpr.edu.pb.leituralivroapp.ui.book

import android.app.Activity
import android.content.Intent
import android.net.Uri
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import androidx.activity.result.contract.ActivityResultContracts
import androidx.core.net.toFile
import androidx.room.Room
import coil.load
import utfpr.edu.pb.leituralivroapp.R
import utfpr.edu.pb.leituralivroapp.database.Database
import utfpr.edu.pb.leituralivroapp.databinding.FragmentBookBinding
import utfpr.edu.pb.leituralivroapp.model.Livro
import java.io.File

// TODO: Rename parameter arguments, choose names that match
// the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
private const val ARG_PARAM1 = "param1"
private const val ARG_PARAM2 = "param2"

/**
 * A simple [Fragment] subclass.
 * Use the [BookFragment.newInstance] factory method to
 * create an instance of this fragment.
 */
class BookFragment : Fragment() {
    private var _binding: FragmentBookBinding? = null
    lateinit var uri : Uri
    lateinit var imageView: ImageView
    private val binding get() = _binding!!


    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        _binding = FragmentBookBinding.inflate(inflater, container, false)
        val root: View = binding.root
        imageView = binding.addImage
        imageView.setOnClickListener(){
            val intent = Intent(Intent.ACTION_GET_CONTENT)
            intent.type = "image/*"
            intent.addCategory(Intent.CATEGORY_OPENABLE)
            startActivity(intent)
            launcherArquivo.launch(intent)
        }
        val db = this.activity?.let { it1 ->
            Room.databaseBuilder(
                it1,
                Database::class.java,
                "livros.db"
            ).allowMainThreadQueries().build()
        }
        val dao = db?.daoLivro()
        binding.containedButton.setOnClickListener{
            val nome = binding.nomeEdit.text.toString()
            val genero = binding.generoEdit.text.toString()
            val autor = binding.autorEdit.text.toString()
            if (dao != null && uri != null) {
                dao.salvar(Livro(
                    titulo = nome,
                    genero = genero,
                    autor = autor,
                    img = uri.toString()
                ))
                binding.nomeEdit.setText("")
                binding.generoEdit.setText("")
                binding.autorEdit.setText("")
                binding.addImage.load(R.drawable.ic_add_black_24dp)
            }
        }
        return root
    }


    val launcherArquivo = registerForActivityResult(ActivityResultContracts.StartActivityForResult()){
            result->
        if (result.resultCode == Activity.RESULT_OK) {
            val arquivo : Intent? = result.data
            if (arquivo != null)
                uri = arquivo.data!!.normalizeScheme()
            imageView.load(uri)
        }
    }
    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}

